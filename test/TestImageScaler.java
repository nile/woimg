import java.io.File;

import utils.ImgUtil;

public class TestImageScaler {
	public static void main(String[] args) {
		ImgUtil.write(ImgUtil.s170(null, null, ImgUtil.read(new File(
				"D:\\home\\Pictures\\BfYLwInU.jpg"))), "PNG",
				"D:\\home\\Pictures\\BfYLwInUs.jpg");
		ImgUtil.write(ImgUtil.s600(null, null, ImgUtil.read(new File(
		"D:\\home\\Pictures\\BfYLwInU.jpg"))), "PNG",
		"D:\\home\\Pictures\\BfYLwInUl.jpg");
	}
}
