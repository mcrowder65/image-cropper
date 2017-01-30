package imageprimitives;

import java.awt.Color;

import generic.MatWrapper;

public class MorphologicalOperations {

	/**
	 * The image should be thresholded first for expected results.
	 * 
	 * @param input
	 * @param mask
	 * @param onValue
	 * @return
	 */
	public static MatWrapper dialate(MatWrapper input, int[][] mask, int pivotRow, int pivotCol, int onValue) {
		if (!input.isGrayscale()) {
			System.err.println("ERROR: Image must be grayscale for dialate!");
			return input;
		}

		MatWrapper output = new MatWrapper(input);

		Color onColor = new Color(onValue, onValue, onValue);

		for (int row = 0; row < input.mat.height() - 1; row++) {
			for (int col = 0; col < input.mat.width() - 1; col++) {
				if (input.getPixel(row, col).getColor().getRed() != 0)
					Union(input, mask, pivotRow, pivotCol, onColor, row, col);
			}
		}

		return output;
	}

	private static void Union(MatWrapper source, int[][] mask, int pivotRow, int pivotCol, Color onColor, int targetRow,
			int targetCol) {

	}
}
