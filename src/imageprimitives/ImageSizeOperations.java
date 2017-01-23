package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ImageSizeOperations {

	/**
	 * Returns a crop of the input image.
	 * 
	 * @param input
	 * @param minx
	 * @param miny
	 * @param maxx
	 * @param maxy
	 * @return Returns the new cropped image.
	 */
	public static Mat CropToRect(Mat input, int minx, int miny, int maxx, int maxy) {
		Rect rectCrop = new Rect(minx, miny, maxx, maxy);
		Mat output = new Mat(input, rectCrop);
		return output;
	}

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
	 * @return Mat Returns a Mat object if operation succeeded
	 * @throws Exception
	 *             Throws a generic exception if you try to resize the image to
	 *             the same dimensions
	 */
	public static Mat resizeImage(Mat image, String newImageName, int desiredWidth, int desiredHeight)
			throws Exception {
		Size size = image.size();
		Size desiredSize = new Size(desiredWidth, desiredHeight);
		if (size.width != desiredWidth || size.height != desiredHeight) {
			Mat destination = new Mat();
			Imgproc.resize(image, destination, desiredSize);
			Highgui.imwrite(newImageName, destination);
			return destination;
		}

		throw new Exception("You can't resize the image to the same dimensions!");
	}
}
