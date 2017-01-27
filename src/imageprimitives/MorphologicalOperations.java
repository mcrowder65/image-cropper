package imageprimitives;

import generic.MatWrapper;
import generic.Pixel;

public class MorphologicalOperations {

	public static MatWrapper dialate(MatWrapper input, int[][] mask, int onValue) {
		MatWrapper output = new MatWrapper();
		input.Print();
		output = ColorOperations.toGrayscale(input);
		output = ColorOperations.threshold(output);

		@SuppressWarnings("unused")
		Pixel p = input.getPixel(output.mat.height() / 2, output.mat.width() / 2);
		return output;
	}
}
