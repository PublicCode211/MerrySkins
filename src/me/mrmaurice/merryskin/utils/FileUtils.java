package me.mrmaurice.merryskin.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.google.common.io.ByteStreams;

import me.mrmaurice.merryskin.MerrySkin;

public class FileUtils {
	
	public static void copyImageFromJar(String fromPath, File outPut){
		try {
			BufferedImage toCopy = ImageIO.read(MerrySkin.class.getResourceAsStream( fromPath ));
			if(!outPut.exists()) outPut.mkdirs();
			ImageIO.write(toCopy, "png", outPut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFileFromJar(String fromPath, File toPath){

		try {
			if(!toPath.exists()) toPath.createNewFile();
			try ( InputStream is = MerrySkin.class.getResourceAsStream( fromPath );
					OutputStream os = new FileOutputStream( toPath ) ) {
				ByteStreams.copy( is, os );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}