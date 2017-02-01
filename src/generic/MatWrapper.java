package generic;

import java.awt.Color;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
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

	public int width() {
		return mat.width();
	}

	public int height() {
		return mat.height();
	}

	public MatWrapper(Mat mat) {
		this.mat = mat;
	}

	public MatWrapper(MatWrapper matW, Rect rectCrop) {
		this.mat = new Mat(matW.mat, rectCrop);
		this.isGrayscale = matW.isGrayscale;

	}

	public MatWrapper(String path) {
		this.mat = Highgui.imread(path, Highgui.CV_LOAD_IMAGE_COLOR);
	}

	/**
	 * Creates a deep copy.
	 * 
	 * @param clone
	 */
	public MatWrapper(MatWrapper clone) {
		this.mat = clone.mat.clone();
		this.isGrayscale = clone.isGrayscale;
	}

	public int getToGrayscaleConstant() {
		return Imgproc.COLOR_RGB2GRAY;

	}

	public int getRGB(int row, int col) {
		return getPixel(row, col).getRGB();
	}

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

	public Pixel[][] getPixels() {
		Pixel[][] pixels = new Pixel[mat.rows()][];
		for (int row = 0; row < mat.height(); row++) {
			pixels[row] = new Pixel[mat.cols()];
			for (int col = 0; col < mat.width(); col++) {
				pixels[row][col] = getPixel(row, col);
			}
		}
		return pixels;
	}

	public void setPixel(Pixel pixel) {
		setColor(pixel.getX(), pixel.getY(), pixel.getColor());
	}

	public void setColor(int x, int y, Color color) {

		if (x < 0 || x > mat.width() - 1) {
			System.err.println("ERROR: x was out of bounds.");
			return;
		}
		if (y < 0 || y > mat.height() - 1) {
			System.err.println("ERROR: y was out of bounds.");
			return;
		}

		if (isGrayscale)
			mat.put(y, x, color.getRed());
		else
			mat.put(y, x, color.getBlue(), color.getGreen(), color.getRed());
	}

	public void Print() {
		Tools.DumpMat(mat, "mat");
	}

	public void Write(String path) {
		Highgui.imwrite(path, mat);
	}

}
