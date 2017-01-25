package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorOperations {

	/**
	 * Returns the image as grayscale.
	 * 
	 * @param input
	 *            Mat
	 * @return Returns a Mat grayscale image
	 */
	public static Mat toGrayscale(Mat input) {
		Mat output = new Mat();
		Imgproc.cvtColor(input, output, Imgproc.COLOR_RGB2GRAY);
		return output;

	}

	/**
	 * Histogram streches
	 * 
	 * @param input
	 *            Mat
	 * @return Mat
	 */
	public static Mat histogramStretch(Mat input) {
		Mat output = new Mat();
		try {

			Imgproc.equalizeHist(input, output);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}

		return output;

	}

	/**
	 * Creates an image that is thresholded to black or white. First, the image
	 * is converted to grayscale, then an adaptive threshold is applied.
	 * 
	 * @param input
	 *            Mat
	 * @return Mat
	 */
	public static Mat threshold(Mat input) {
		Mat output = new Mat();
		output = ColorOperations.toGrayscale(input);

		Imgproc.adaptiveThreshold(output, output, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 3, 0);
		return output;
	}

}
