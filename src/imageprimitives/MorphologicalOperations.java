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

		for (int row = 0; row < output.mat.height() - 1; row++) {
			for (int col = 0; col < output.mat.width() - 1; col++) {
				if (output.getPixel(row, col).getColor().getRed() != 0)
					Union(output, mask, row, col);
			}
		}

		return output;
	}

	private static void Union(MatWrapper source, MorphMask mask, int targetRow, int targetCol) {
		Color onColor = new Color(mask.onValue, mask.onValue, mask.onValue);

	}
}
