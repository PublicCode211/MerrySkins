package me.mrmaurice.merryskin;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;

import javax.imageio.ImageIO;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.google.gson.JsonParser;

import me.mrmaurice.merryskin.cache.SkinCache;
import me.mrmaurice.merryskin.utils.UploadUtils;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.connection.LoginResult;
import net.md_5.bungee.connection.LoginResult.Property;

public class SkinFactory {
	
	private File temporalFolder;
	private File rootFolder;
	
	public SkinFactory(File file){
		temporalFolder = file;
		rootFolder = MerrySkin.getInstance().getDataFolder();
	}
	
	public BufferedImage getSkin(ProxiedPlayer player){
		String id = getId(player);
		if(id == null) return null;
		
		String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + id;
		String jsonString = Utils.readUrl(url);
		if(jsonString == null){
			try {
				return ImageIO.read(new URL("http://mcapi.ca/rawskin/" + player.getName()));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		String value = new JsonParser().parse(jsonString).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
		String profile = Base64Coder.decodeString(value);
		String textureUrl = new JsonParser().parse(profile).getAsJsonObject().get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
		
		try {
			URL skinUrl = new URL(textureUrl);
			return ImageIO.read(skinUrl);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getId(ProxiedPlayer player){
		String sUrl = "https://api.mojang.com/users/profiles/minecraft/" + player.getName();
		String readed = Utils.readUrl(sUrl);
		if(readed.isEmpty()) return null;
		return new JsonParser().parse(readed).getAsJsonObject().get("id").getAsString();
	}
	
	public boolean createSkin(ProxiedPlayer player, BufferedImage originalSkin, HeadEnum hatType){
		if(originalSkin.getHeight() < 64) originalSkin = Utils.convertSkin(originalSkin);
		File path = new File(temporalFolder, player.getName() + ".png");
		BufferedImage newSkin;
		BufferedImage layout = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		try {			
			layout = ImageIO.read(new File(rootFolder, hatType.getPath()));
			newSkin = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
			Graphics g = newSkin.getGraphics();
			g.drawImage(originalSkin, 0, 0, null);
			g.drawImage(layout, 0, 0, null);
			
			ImageIO.write(newSkin, "png", path);
			UploadUtils.uploadSkin(path, hatType, player);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void applySkin(ProxiedPlayer player, SkinCache cache){
		
		InitialHandler initialHandler = (InitialHandler) player.getPendingConnection();
		LoginResult loginProfile = initialHandler.getLoginProfile();
		
		if (loginProfile == null) {
			try {
				Field profileField = InitialHandler.class.getDeclaredField("loginProfile");
				profileField.setAccessible(true);
				String id = player.getUniqueId().toString().replace("-", "");

                if (cache == null) {
                	LoginResult loginResult = new LoginResult(id, new Property[]{});
                	profileField.set(initialHandler, loginResult);
                } else {
                	Property textures = new Property("textures", cache.getValue(), cache.getSignature());
                	Property[] properties = new Property[]{textures};
                	
                	LoginResult loginResult = new LoginResult(id, properties);
                	profileField.set(initialHandler, loginResult);
                }
			} catch (NoSuchFieldException | IllegalAccessException ex) {
				ex.printStackTrace();
			}
		} else if (cache == null) {
			loginProfile.setProperties(new Property[]{});
        } else {
            Property textures = new Property("textures", cache.getValue(), cache.getSignature());
            loginProfile.setProperties(new Property[]{textures});
}
		
	}
	
}
