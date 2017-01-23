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

}
