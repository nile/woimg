package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import play.Logger;

public class MagicScaler extends Scaler {

	@Override
	public BufferedImage square(String infile, String tofile, BufferedImage bi,
			int size) {
		int height = bi.getHeight();
		int width = bi.getWidth();
		int s = Math.min(height, width);
		String command = String
				.format("gm convert %3$s -gravity center -extent %5$sx%5$s -resize %1$sx%1$s -quality 95 %4$s",
						size, size, infile, tofile, s);
		execute(command);
		return null;
	}

	@Override
	public BufferedImage scale(String infile, String tofile, BufferedImage bi,
			int width, int height) {
		
		int imgwidth = bi.getWidth();
		int imgheight = bi.getHeight();
		if(width<imgwidth&&height<imgheight) {
			//do nothing
			return null;
		}
		width = Math.min(width, imgwidth);
		height = Math.min(height, imgheight);
		String command = String
				.format("gm convert -size %1$sx%2$s %3$s -resize %1$sx%2$s -quality 95 %4$s",
						width, height, infile, tofile);
		execute(command);
		return null;
	}
	private void execute(String command) {
		if(Logger.isDebugEnabled())
			Logger.debug(command);
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command, new String[0], new File("."));
			if (process.waitFor() == 0) {

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(process != null)
				process.destroy();
		}
	}


}
