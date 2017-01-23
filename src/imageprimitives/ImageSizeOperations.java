package imageprimitives;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class ImageSizeOperations {

	/**
	 * Returns a crop of the input image.
	 * @param input
	 * @param minx
	 * @param miny
	 * @param maxx
	 * @param maxy
	 * @return Returns the new cropped image.
	 */
	public static Mat CropToRect(Mat input, int minx, int miny, int maxx, int maxy)
	{
		Rect rectCrop = new Rect(minx, miny, maxx, maxy );
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
	 * @return boolean indicating whether or not the image was resized.
	 */
	public static boolean resizeImage(Mat image, String newImageName, int desiredWidth, int desiredHeight) {
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
