package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import imageprimitives.Resize;

public class Main {

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Mat source = Highgui.imread("testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
			Mat destination = new Mat(source.rows(), source.cols(), source.type());
			Imgproc.GaussianBlur(source, destination, new Size(45, 45), 0);
			Highgui.imwrite("Gaussian45.jpg", destination);
			medianBlur(55, source, destination);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
        
        
        Mat resizeMe = Highgui.imread("download.png", Highgui.CV_LOAD_IMAGE_COLOR);
        
        Resize.resizeImage(resizeMe, "resized.jpg", 600, 600);

		/*
		 * try {
		 * 
		 * System.loadLibrary(Core.NATIVE_LIBRARY_NAME); Mat source =
		 * Highgui.imread("Gaussian45.jpg", Highgui.CV_LOAD_IMAGE_COLOR); Mat
		 * destination = new Mat(source.rows(), source.cols(), source.type());
		 * 
		 * destination = source; // Imgproc.threshold(source, destination, 128,
		 * 255, // Imgproc.THRESH_BINARY); // Highgui.imwrite("ThreshZero.jpg",
		 * destination); Imgproc.medianBlur(source, destination, 11);
		 * Highgui.imwrite("MedianBlur.jpg", destination);
		 * 
		 * } catch (Exception e) { System.out.println("error: " +
		 * e.getMessage()); }
		 */
	}

	/**
	 * @param k
	 *            k is kernal size that we want to blur by
	 * @param source
	 * @param destination
	 */
	static void medianBlur(int k, Mat source, Mat destination) {
		try {

			Imgproc.medianBlur(source, destination, k);
			Highgui.imwrite("MedianBlur.jpg", destination);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
        }
        
        


	}

}
