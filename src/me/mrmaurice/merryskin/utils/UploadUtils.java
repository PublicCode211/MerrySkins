package me.mrmaurice.merryskin.utils;

import java.io.File;

import me.mrmaurice.merryskin.HeadEnum;
import me.mrmaurice.merryskin.MerrySkin;
import me.mrmaurice.merryskin.cache.SkinCache;
import me.mrmaurice.merryskin.mineSkin.MineskinClient;
import me.mrmaurice.merryskin.mineSkin.data.Skin;
import me.mrmaurice.merryskin.mineSkin.data.SkinCallback;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UploadUtils {

	private static MineskinClient skinClient = new MineskinClient();

	public static void uploadSkin(File skinFile, HeadEnum headType, ProxiedPlayer player){
		
		skinClient.generateUpload(skinFile, new SkinCallback() {

			@Override
			public void waiting(long l) {
				
			}

			@Override
			public void uploading() {
				
			}

			@Override
			public void error(String s) {
				skinFile.delete();
			}

			@Override
			public void exception(Exception exception) {
				skinFile.delete();
				exception.printStackTrace();
			}

			@Override
			public void done(Skin skin) {
				SkinCache cache = new SkinCache(skin.data.texture.value,
						skin.data.texture.signature, skin.data.texture.url, headType);
				MerrySkin.getFactory().applySkin(player, cache);
				MerrySkin.getCacheManager().saveSkin(player, cache);
				skinFile.delete();
			}
		});
		
	}
	
}
