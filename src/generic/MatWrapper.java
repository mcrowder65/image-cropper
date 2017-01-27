package generic;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MatWrapper {

	public Mat mat;
	private MatCode code;
	private boolean isGrayscale;

	public boolean isGrayscale() {
		return isGrayscale;
	}

	public void setGrayscale(boolean isGrayscale) {
		this.isGrayscale = isGrayscale;
	}

	public MatWrapper() {
		mat = new Mat();
		code = MatCode.RGB;
	}

	public MatWrapper(Mat mat) {
		this.mat = mat;
		code = MatCode.RGB;
	}

	public MatCode getCode() {
		return code;
	}

	public void setCode(MatCode code) {
		this.code = code;
	}

	public int getToGrayscaleConstant() {
		switch (code) {
		case RGB:
			return Imgproc.COLOR_RGB2GRAY;
		case YUV:
			return Imgproc.COLOR_YUV2GRAY_I420;
		default:
			return -1;
		}
	}

	public Pixel getPixel(int row, int col) {
		Pixel pixel = new Pixel(col, row, 0);
		double[] val = mat.get(row, col);
		return pixel;

	}

	public void Print() {
		Tools.DumpMat(mat, "mat");
	}

}
