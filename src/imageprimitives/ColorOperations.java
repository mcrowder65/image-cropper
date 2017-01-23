package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorOperations {

	public static Mat toGrayscale(Mat input)
	{
		Mat output = new Mat();
		Imgproc.cvtColor(input, output, Imgproc.COLOR_RGB2GRAY);
	    return output;
	}
}
