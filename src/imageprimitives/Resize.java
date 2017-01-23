package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Resize {
	/**
	 * 
	 * @param image
	 *            Mat
	 * @param newImageName
	 *            String
	 * @param desiredWidth
	 *            int
	 * @param desiredHeight
	 *            int
	 * @return boolean indicating whether or not the image was resized.
	 */
	static public boolean resizeImage(Mat image, String newImageName, int desiredWidth, int desiredHeight) {
		Size size = image.size();
		Size desiredSize = new Size(desiredWidth, desiredHeight);
		if (size.width != desiredWidth || size.height != desiredHeight) {
			Mat destination = new Mat();
			Imgproc.resize(image, destination, desiredSize);
			Highgui.imwrite(newImageName, destination);
			return true;
		}

		return false;
	}
}
