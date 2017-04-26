package me.mrmaurice.merryskin;

import java.awt.image.BufferedImage;
import java.io.File;

import me.mrmaurice.merryskin.cache.CacheManager;
import me.mrmaurice.merryskin.cache.SkinCache;
import me.mrmaurice.merryskin.commands.MerryCmd;
import me.mrmaurice.merryskin.utils.FileUtils;
import me.mrmaurice.merryskin.utils.Utils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class MerrySkin extends Plugin implements Listener {

	private static MerrySkin plugin;
	private static CacheManager caches;
	private static SkinFactory factory;
	
	private File temporalFolder;
	private File configFile;
	private Configuration config;
	
	public void onEnable(){
		plugin = this;
		
		createFiles();
		loadConfig();
		
		Utils.setPrefix(config.getString("prefix"));
		
		caches = new CacheManager();
		factory = new SkinFactory(temporalFolder);
		
		getProxy().getPluginManager().registerListener(this, this);
		
		String[] aliases = config.getStringList("").isEmpty() ? new String[] {"merrySkins"}: config.getStringList("").toArray(new String[0]);
		
		getProxy().getPluginManager().registerCommand(this, new MerryCmd(aliases, null, "merrySkins"));
		
	}
	
	@EventHandler
	public void onPostLogin(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();

		setSkin(player);
		
	}
	
	private void setSkin(ProxiedPlayer player){
		SkinCache cache = caches.getPlayer(player);
		if(cache == null){
			BufferedImage skin = factory.getSkin(player);
			if(skin == null){
				caches.applyDefaultSkin(player);
				return;
			}
			factory.createSkin(player, skin, HeadEnum.SANTA_HAT);
			return;
		}
		if(cache.isEnabled())
			factory.applySkin(player, cache);
	}
	
	private void createFiles(){
		if(!getDataFolder().exists()) getDataFolder().mkdir();
		
		configFile = new File(getDataFolder(), "config.yml");
		if(!configFile.exists()) FileUtils.copyFileFromJar("/config.yml", configFile);
		
		temporalFolder = new File(getDataFolder(), "Temporal");
		if(!temporalFolder.exists()) temporalFolder.mkdirs();
		
		for(HeadEnum head : HeadEnum.values()){
			File headFile = new File(getDataFolder(), head.getPath());
			if(headFile.exists()) continue;
			FileUtils.copyImageFromJar(head.getInnerPath(), headFile);
		}
	}
	
	public void loadConfig(){
		try {
			config = ConfigurationProvider.getProvider( YamlConfiguration.class ).load( configFile );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public Configuration getConfig(){
		return config;
	}
	
	public static MerrySkin getInstance(){
		return plugin;
	}
	
	public static CacheManager getCacheManager(){
		return caches;
	}
	
	public static SkinFactory getFactory(){
		return factory;
	}
	
}
