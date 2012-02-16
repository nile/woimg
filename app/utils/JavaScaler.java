package utils;

import java.awt.image.BufferedImage;

import com.jhlabs.image.CropFilter;
import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleOp;

public class JavaScaler extends Scaler {

	@Override
	public BufferedImage square(String infile, String tofile, BufferedImage bi, int s) {
		int size = Math.min(bi.getHeight()-2, bi.getWidth()-2);
		CropFilter cropFilter = new CropFilter(Math.max(1,
				(bi.getWidth() - size) / 2), Math.max(1,
				(bi.getHeight() - size) / 2), size, size);
		BufferedImage croped = cropFilter.filter(bi, null);
		ResampleOp  thumpnailRescaleOp = new ResampleOp (
				DimensionConstrain.createAbsolutionDimension(s, s));
		return thumpnailRescaleOp.filter(croped, null);
	}

	@Override
	public BufferedImage scale(String infile, String tofile, BufferedImage bi, int width, int height) {
		ResampleOp  thumpnailRescaleOp = new ResampleOp (
				DimensionConstrain.createMaxDimension(width,height));
		return thumpnailRescaleOp.filter(bi, null);
	}

}
