package imageprimitives;

import java.awt.Color;

import generic.MatWrapper;
import generic.MorphMask;

public class MorphologicalOperations {

	/**
	 * The image should be thresholded first for expected results.
	 * 
	 * @param input
	 * @param mask
	 * @param onValue
	 * @return
	 */
	public static MatWrapper dialate(MatWrapper input, MorphMask mask) {
		if (!input.isGrayscale()) {
			System.err.println("ERROR: Image must be grayscale for dialate!");
			return input;
		}

		MatWrapper output = new MatWrapper(input);
		OnColor = new Color(mask.onValue, mask.onValue, mask.onValue);

		for (int row = 0; row < input.mat.height() - 1; row++) {
			for (int col = 0; col < input.mat.width() - 1; col++) {
				if (input.getPixel(row, col).getColor().getRed() != 0)
					Union(output, mask, row, col);
			}
		}

		return output;
	}

	private static Color OnColor;
	private static int OffRGB;

	private static void Union(MatWrapper source, MorphMask mask, int targetRow, int targetCol) {

		for (int row = targetRow - mask.pivotRow; row < targetRow - mask.pivotRow + mask.rows; row++) {
			for (int col = targetCol - mask.pivotCol; col < targetCol - mask.pivotCol + mask.cols; col++) {

				if (row < 0 || row > source.height() - 1 || col < 0 || col > source.width() - 1)
					continue;

				source.setColor(col, row, OnColor);
			}
		}
	}

	/**
	 * The image should be thresholded first for expected results.
	 * 
	 * @param input
	 * @param mask
	 * @param onValue
	 * @return
	 */
	public static MatWrapper erode(MatWrapper input, MorphMask mask) {
		if (!input.isGrayscale()) {
			System.err.println("ERROR: Image must be grayscale for erode!");
			return input;
		}

		MatWrapper output = new MatWrapper(input);
		OffRGB = new Color(0, 0, 0).getRGB();

		for (int row = 0; row < input.mat.height() - 1; row++) {
			for (int col = 0; col < input.mat.width() - 1; col++) {
				if (input.getRGB(row, col) != OffRGB) {
					if (!IsContainedWithin(input, mask, row, col))
						output.setColor(col, row, Color.BLACK);
				}
			}
		}

		return output;

	}

	private static boolean IsContainedWithin(MatWrapper source, MorphMask mask, int targetRow, int targetCol) {
		for (int row = targetRow - mask.pivotRow; row < targetRow - mask.pivotRow + mask.rows; row++) {
			for (int col = targetCol - mask.pivotCol; col < targetCol - mask.pivotCol + mask.cols; col++) {

				if (row < 0 || row > source.height() - 1 || col < 0 || col > source.width() - 1)
					return false;

				if (source.getRGB(row, col) == OffRGB)
					return false;
			}
		}
		return true;

	}

	public static MatWrapper morphOpen(MatWrapper input, MorphMask mask) {
		MatWrapper output = erode(input, mask);
		output = dialate(output, mask);
		return output;
	}

	public static MatWrapper morphClose(MatWrapper input, MorphMask mask) {
		MatWrapper output = dialate(input, mask);
		output = erode(output, mask);
		return output;
	}
}
