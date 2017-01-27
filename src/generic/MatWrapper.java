package generic;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MatWrapper {

	public Mat mat;
	private MatCode code;

	public int getToGrayscaleConstant() {
		switch (code) {
		case RGB:
			return Imgproc.COLOR_RGB2GRAY;
		case YUV:
			return Imgproc.COLOR_YUV2GRAY_420;
		default:
			return -1;
		}
	}
}
