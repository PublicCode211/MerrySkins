package me.mrmaurice.merryskin.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.mrmaurice.merryskin.HeadEnum;
import me.mrmaurice.merryskin.MerrySkin;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CacheManager {

	private File cacheFolder;
	
	public CacheManager(){
		cacheFolder = new File(MerrySkin.getInstance().getDataFolder(), "PlayerSkins");
		if(!cacheFolder.exists()) cacheFolder.mkdirs();
	}
	
	public SkinCache getPlayer(ProxiedPlayer player){
		return getSkinCache(player);
	}
	
	private SkinCache getSkinCache(ProxiedPlayer player){
		File file = new File(cacheFolder, player.getName() + ".player");
		String json = "";
		if(!file.exists()) return null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			json = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Gson().fromJson(json, SkinCache.class);
	}
	
	public void applyDefaultSkin(ProxiedPlayer player){
		SkinCache defaultCache = createDefault(player);
		MerrySkin.getFactory().applySkin(player, defaultCache);
		saveSkin(player, defaultCache);
	}
	
	private SkinCache createDefault(ProxiedPlayer player) {
		String signature = "EHhfUnznwgu4jjSlAUKR2c/67TSSHdomLwt/Wl0p9hDiHYofdLVIZHzc7TdM0/0yob5ta7/UuD5OS74InjHmlzbfGbV28z0I9Kqyrwv0nMl7GCEOTyRFhJAkoC1l4pg6SJKKgjHRgEJJAiT+ZwT8XsxntWoQemGB6eYKJKwGSnpi2bCZlJ0A3uEdak/4J2aqS1jvQGjJ0e7d0FDWMxYQIidF1Qkzwx25S+G0vLVDOlAv22hIU+swYN+NyNia9a2K07xxCDTX3qpYQu594Ed+FZZuJbKunwZ1jJwBEjGhHtFQDrLWZqZLn8TlXvHS1nfTelMRzMZ2thTWcagUPCNpF1KyqqCwVx36h6FO8jz9U4O7LVZ0YF6uE3aShOuBpBoXM3ng4OfG822Z3519vqzGjj6bn6QTGf3swCHUmqBYQ0m83Nqa9n7zJVl4b75+uLMi25+/7lA6oB5FDRwkqxaKLlWKbBDSlWMnZhN77xoq05PlOjFyTmEs7h3dMHKce5ltiaPWWyK/kPcS1nxqSLK0SifQduywmN/7nH7FObV0sa+uvMUmFjcFAQQntTmK458Bv+csTA2t22itLb35H441EsQbazqjUytPoXk2TZdqa8XM4ENX+wgohb1KUjH++8kaHPCcU8cTTudpFoFXezhfSA88Sshc4bcjevLt/VwQWHw=";
		String value = "eyJ0aW1lc3RhbXAiOjE0ODE2NjA2NzQxMTQsInByb2ZpbGVJZCI6ImRhNzQ2NWVkMjljYjRkZTA5MzRkOTIwMTc0NDkxMzU1IiwicHJvZmlsZU5hbWUiOiJJc2F5bGEiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFlNGU2NGM1MGY0OTk1MDRiODliN2I1NWRmOWQyYmYzMjBkZjQ2MTkxNGQ4OTIzZmRkMzY4MTY1MDhkOTgifX19";
		String texture = "http://textures.minecraft.net/texture/1e4e64c50f499504b89b7b55df9d2bf320df461914d8923fdd36816508d98";
		return new SkinCache(value, signature, texture, HeadEnum.SANTA_HAT);
	}

	public void saveSkin(ProxiedPlayer player, SkinCache playerCache){
		File file = new File(cacheFolder, player.getName() + ".player");
		try {
			if(!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter(file);
			fw.write(new GsonBuilder().disableHtmlEscaping().create().toJson(playerCache));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
