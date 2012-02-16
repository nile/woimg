package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.StringUtils;

import com.jhlabs.image.CropFilter;
import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;

public class ImgUtil {
	private static Scaler scaler = new MagicScaler();
	public static boolean write(BufferedImage bi, String fmt, String file) {
		if(bi==null)
			return false;
		try {
			ImageIO.write(bi, fmt, new File(file));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static BufferedImage read(InputStream in) {
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static BufferedImage read(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage s600(String infile, String tofile, BufferedImage bi) {
		return scaler.scale(infile, tofile, bi,600,Integer.MAX_VALUE);
	}

	public static BufferedImage t100(String infile, String tofile, BufferedImage bi) {
		return scaler.square(infile, tofile, bi, 70);
	}
	
	public static BufferedImage s170(String infile, String tofile, BufferedImage bi) {
		return scaler.scale(infile, tofile, bi,170,Integer.MAX_VALUE);
	}

	public static Info detectImageType(File file) {
		if(file == null)
			return null;
		String type = null;
		int width = 0;
		int height = 0;
		ImageInputStream iis = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			iis = ImageIO.createImageInputStream(fis);
			Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
			while (imageReaders.hasNext()) {
				ImageReader reader = imageReaders.next();
				try {
					type = reader.getFormatName();
					reader.setInput(iis);
					height = reader.getHeight(0);
					width = reader.getWidth(0);
					reader.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			iis.close();
			return new Info(type, width, height);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			closeStream(fis);
		}
		return null;
	}

	private static void closeStream(InputStream iis) {
		if (iis != null) {
			try {
				iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class Info {
		public Info(String type2, int width, int height) {
			type = StringUtils.upperCase(type2);
			w = width;
			h = height;
			ext = "PNG".equals(type) ? ".png" : "JPEG".equals(type) ? ".jpg"
					: ".unknow";
		}

		public String type;
		public String ext;
		public int w;
		public int h;
	}
}
