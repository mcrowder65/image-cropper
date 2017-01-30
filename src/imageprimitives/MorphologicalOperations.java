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

	private static void Union(MatWrapper source, MorphMask mask, int targetRow, int targetCol) {

		for (int row = targetRow - mask.pivotRow; row < targetRow - mask.pivotRow + mask.rows; row++) {
			for (int col = targetCol - mask.pivotCol; col < targetCol - mask.pivotCol + mask.cols; col++) {

				if (row < 0 || row > source.height() - 1 || col < 0 || col > source.width() - 1)
					continue;

				source.setColor(col, row, OnColor);
			}
		}
	}
}
