package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorOperations {

	/**
	 * Returns the image as grayscale.
	 * @param input
	 * @return Returns a grayscale image.
	 */
	public static Mat toGrayscale(Mat input)
	{
		Mat output = new Mat();
		Imgproc.cvtColor(input, output, Imgproc.COLOR_RGB2GRAY);
	    return output;
	}
}
