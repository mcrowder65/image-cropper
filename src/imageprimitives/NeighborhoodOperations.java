package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class NeighborhoodOperations {

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

	/*
	 * try {
	 * 
	 * System.loadLibrary(Core.NATIVE_LIBRARY_NAME); Mat source =
	 * Highgui.imread("Gaussian45.jpg", Highgui.CV_LOAD_IMAGE_COLOR); Mat
	 * destination = new Mat(source.rows(), source.cols(), source.type());
	 * 
	 * destination = source; // Imgproc.threshold(source, destination, 128, 255,
	 * // Imgproc.THRESH_BINARY); // Highgui.imwrite("ThreshZero.jpg",
	 * destination); Imgproc.medianBlur(source, destination, 11);
	 * Highgui.imwrite("MedianBlur.jpg", destination);
	 * 
	 * } catch (Exception e) { System.out.println("error: " + e.getMessage()); }
	 */

}
