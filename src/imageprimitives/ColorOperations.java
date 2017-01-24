package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorOperations {

	/**
	 * Returns the image as grayscale.
	 * 
	 * @param input
	 * @return Returns a grayscale image.
	 */
	public static Mat toGrayscale(Mat input) {
		Mat output = new Mat();
		Imgproc.cvtColor(input, output, Imgproc.COLOR_RGB2GRAY);
		return output;

	}

	public static Mat histogramStretch(Mat input) {
		Mat output = new Mat();
		try {

			Imgproc.equalizeHist(input, output);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}

		return output;

	}

	public static Mat threshold(Mat input) {
		Mat output = new Mat();
		Imgproc.adaptiveThreshold(input, output, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 3, 0);
		return output;
	}

}
