package imageprimitives;

import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import generic.MatWrapper;

public class ImageSizeOperations {

	/**
	 * Returns a crop of the input image.
	 * 
	 * @param input
	 *            Mat
	 * @param minx
	 *            int
	 * @param miny
	 *            int
	 * @param maxx
	 *            int
	 * @param maxy
	 *            int
	 * @return Returns the new cropped image.
	 */
	public static MatWrapper CropToRect(MatWrapper input, int minx, int miny, int maxx, int maxy) {
		Rect rectCrop = new Rect(minx, miny, maxx - minx, maxy - miny);

		MatWrapper output = new MatWrapper(input, rectCrop);
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
	 */
	public static MatWrapper resizeImage(MatWrapper image, int desiredWidth, int desiredHeight) {
		Size size = image.mat.size();
		Size desiredSize = new Size(desiredWidth, desiredHeight);
		if (size.width != desiredWidth || size.height != desiredHeight) {
			MatWrapper destination = new MatWrapper();
			Imgproc.resize(image.mat, destination.mat, desiredSize);

			return destination;
		} else
			return image;

	}
}
