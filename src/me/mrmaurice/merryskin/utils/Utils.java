package me.mrmaurice.merryskin.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Utils {

	private static String prefix = "&cMerry&rSkins &8» ";

	public static void setPrefix(String newPrefix){
		if(newPrefix.isEmpty()) return;
		prefix = newPrefix;
	}
	
	public static void sendMessage(CommandSender sender, String msg, boolean usePrefix) {
		if (usePrefix)
			msg = prefix + msg;
		sender.sendMessage(TextComponent.fromLegacyText(color(msg)));
	}

	public static void sendMessage(ProxiedPlayer player, String msg, boolean usePrefix) {
		if (usePrefix)
			msg = prefix + msg;
		player.sendMessage(TextComponent.fromLegacyText(color(msg)));
	}

	public static String color(String msgToColor) {
		return ChatColor.translateAlternateColorCodes('&', msgToColor);
	}

	public static String nocolor(String msgToUncolor) {
		return ChatColor.stripColor(msgToUncolor);
	}
	
	public static String replacePlace(String toReplace, String placeHolder, String value){
		return toReplace.replaceAll(placeHolder, value);
	}

	public static String readUrl(String url) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "MerrySkins");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);

			String line;
			StringBuilder output = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			while ((line = in.readLine()) != null)
				output.append(line);

			in.close();
			
			if(output.toString().contains("\"TooManyRequestsException\"")) return null;
			
			return output.toString();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static BufferedImage convertSkin(BufferedImage skin){
	    BufferedImage upper = new BufferedImage(64, 32, 2);
	    upper.getGraphics().drawImage(skin, 0, 0, 64, 32, 0, 0, 64, 32, null);
	    
	    BufferedImage armF = new BufferedImage(12, 12, 2);
	    armF.getGraphics().drawImage(skin, 0, 0, 12, 12, 40, 20, 52, 32, null);
	    AffineTransform tx = AffineTransform.getScaleInstance(-1.0D, 1.0D);
	    tx.translate(-armF.getWidth(null), 0.0D);
	    AffineTransformOp op = new AffineTransformOp(tx, 1);
	    armF = op.filter(armF, null);
	    
	    BufferedImage armB = new BufferedImage(4, 12, 2);
	    armB.getGraphics().drawImage(skin, 0, 0, 4, 12, 52, 20, 56, 32, null);
	    AffineTransform txab = AffineTransform.getScaleInstance(-1.0D, 1.0D);
	    txab.translate(-armB.getWidth(null), 0.0D);
	    AffineTransformOp opab = new AffineTransformOp(txab, 1);
	    armB = opab.filter(armB, null);
	    
	    BufferedImage armT = new BufferedImage(4, 4, 2);
	    armT.getGraphics().drawImage(skin, 0, 0, 4, 4, 44, 16, 48, 20, null);
	    AffineTransform txat = AffineTransform.getScaleInstance(-1.0D, 1.0D);
	    txat.translate(-armT.getWidth(null), 0.0D);
	    AffineTransformOp opat = new AffineTransformOp(txat, 1);
	    armT = opat.filter(armT, null);
	    
	    BufferedImage armBo = new BufferedImage(4, 4, 2);
	    armBo.getGraphics().drawImage(skin, 0, 0, 4, 4, 48, 16, 52, 20, null);
	    armBo = opat.filter(armBo, null);
	    
	    BufferedImage legF = new BufferedImage(12, 12, 2);
	    legF.getGraphics().drawImage(skin, 0, 0, 12, 12, 0, 20, 12, 32, null);
	    legF = op.filter(legF, null);
	    
	    BufferedImage legB = new BufferedImage(4, 12, 2);
	    legB.getGraphics().drawImage(skin, 0, 0, 4, 12, 12, 20, 16, 32, null);
	    legB = opab.filter(legB, null);
	    
	    BufferedImage legT = new BufferedImage(4, 4, 2);
	    legT.getGraphics().drawImage(skin, 0, 0, 4, 4, 4, 16, 8, 20, null);
	    legT = opat.filter(legT, null);
	    
	    BufferedImage legBo = new BufferedImage(4, 4, 2);
	    legBo.getGraphics().drawImage(skin, 0, 0, 4, 4, 8, 16, 12, 20, null);
	    legBo = opab.filter(legBo, null);
	    
	    BufferedImage newSkin = new BufferedImage(64, 64, 2);
	    newSkin.getGraphics().drawImage(upper, 0, 0, 64, 64, 0, 0, 64, 64, null);
	    
	    newSkin.getGraphics().drawImage(armF, 0, 0, 64, 64, -32, -52, 32, 12, null);
	    newSkin.getGraphics().drawImage(armB, 0, 0, 64, 64, -44, -52, 20, 12, null);
	    newSkin.getGraphics().drawImage(armT, 0, 0, 64, 64, -36, -48, 28, 16, null);
	    newSkin.getGraphics().drawImage(armBo, 0, 0, 64, 64, -40, -48, 24, 16, null);
	    
	    newSkin.getGraphics().drawImage(legF, 0, 0, 64, 64, -16, -52, 48, 12, null);
	    newSkin.getGraphics().drawImage(legB, 0, 0, 64, 64, -28, -52, 36, 12, null);
	    newSkin.getGraphics().drawImage(legT, 0, 0, 64, 64, -20, -48, 44, 16, null);
	    newSkin.getGraphics().drawImage(legBo, 0, 0, 64, 64, -24, -48, 40, 16, null);
	    
	    return newSkin;
	}

}
