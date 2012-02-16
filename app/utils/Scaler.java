package utils;

import java.awt.image.BufferedImage;

public abstract class Scaler {

	public abstract BufferedImage square(String infile, String tofile, BufferedImage bi, int size) ;

	public abstract BufferedImage scale(String infile, String tofile, BufferedImage bi, int width, int height);

}
