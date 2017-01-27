package imageprimitives;

import org.opencv.imgproc.Imgproc;

import generic.MatWrapper;

public class ColorOperations {

	/**
	 * Returns the image as grayscale.
	 * 
	 * @param input
	 *            Mat
	 * @return Returns a Mat grayscale image
	 */
	public static MatWrapper toGrayscale(MatWrapper input) {
		if (input.isGrayscale())
			return input;

		MatWrapper output = new MatWrapper();
		Imgproc.cvtColor(input.mat, output.mat, input.getToGrayscaleConstant());
		output.setGrayscale(true);
		return output;

	}

	/**
	 * Histogram streches
	 * 
	 * @param input
	 *            Mat
	 * @return Mat
	 */
	public static MatWrapper histogramStretch(MatWrapper input) {
		MatWrapper output = new MatWrapper();
		output = ColorOperations.toGrayscale(input);
		Imgproc.equalizeHist(output.mat, output.mat);
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
	public static MatWrapper threshold(MatWrapper input) {
		MatWrapper output = new MatWrapper();
		output = ColorOperations.toGrayscale(input);
		// output = NeighborhoodOperations.medianBlur(87, output);
		Imgproc.threshold(output.mat, output.mat, 128, 255, Imgproc.THRESH_BINARY);
		return output;
	}

}
