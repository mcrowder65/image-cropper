package generic;

import java.awt.Color;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class MatWrapper {

	public Mat mat;
	private boolean isGrayscale;

	public boolean isGrayscale() {
		return isGrayscale;
	}

	public void setGrayscale(boolean isGrayscale) {
		this.isGrayscale = isGrayscale;
	}

	public MatWrapper() {
		mat = new Mat();
	}

	public MatWrapper(Mat mat) {
		this.mat = mat;
	}

	public MatWrapper(String path) {
		this.mat = Highgui.imread(path);
	}

	public int getToGrayscaleConstant() {
		return Imgproc.COLOR_RGB2GRAY;

	}

	/**
	 * THIS WORKS
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Pixel getPixel(int row, int col) {
		double[] val = mat.get(row, col);
		Color color;
		if (val.length == 3) {
			color = new Color((int) val[0], (int) val[1], (int) val[2]);
		} else {
			int v = (int) val[0];
			color = new Color(v, v, v);
		}

		Pixel pixel = new Pixel(col, row, color);
		return pixel;

	}

	/**
	 * DOES NOT WORK YET
	 * 
	 * @param pixel
	 */
	public void setPixel(Pixel pixel) {
		int x = pixel.getX();
		int y = pixel.getY();
		if (x < 0 || x > mat.width() - 1) {
			System.err.println("ERROR: x was out of bounds.");
			return;
		}
		if (y < 0 || y > mat.height() - 1) {
			System.err.println("ERROR: y was out of bounds.");
			return;
		}
		Color color = pixel.getColor();

	}

	public void Print() {
		Tools.DumpMat(mat, "mat");
	}

	public void Write(String path) {
		Highgui.imwrite(path, mat);
	}

}
