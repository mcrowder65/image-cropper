package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Main {

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Mat source = Highgui.imread("download.png", Highgui.CV_LOAD_IMAGE_COLOR);

			Mat destination = new Mat(source.rows(), source.cols(), source.type());
			Imgproc.GaussianBlur(source, destination, new Size(45, 45), 0);
			Highgui.imwrite("Gaussian45.jpg", destination);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		try {

			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat source = Highgui.imread("Gaussian45.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
			Mat destination = new Mat(source.rows(), source.cols(), source.type());

			destination = source;
			Imgproc.threshold(source, destination, 75, 250, Imgproc.THRESH_BINARY);
			Highgui.imwrite("ThreshZero.jpg", destination);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
	}

}
