package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class NeighborhoodOperations {

	/**
	 * @param k
	 *            k is kernal size that we want to blur by
	 * @param source
	 * @param destination
	 */
	static Mat medianBlur(int k, Mat source) {
		Mat destination = new Mat();
		try {

			Imgproc.medianBlur(source, destination, k);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}

		return destination;
	}

}
