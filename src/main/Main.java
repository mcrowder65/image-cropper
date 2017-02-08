package main;

import org.opencv.core.Core;

import generic.ImageComponent;
import generic.MatWrapper;
import imageprimitives.ColorOperations;
import imageprimitives.NeighborhoodOperations;

public class Main {
	private static void crop(String name, String extension, int k) {

		MatWrapper input = new MatWrapper(name + extension);
		MatWrapper grayScaledImage = ColorOperations.toGrayscale(input);
		MatWrapper threshImage = ColorOperations.threshold(grayScaledImage);
		MatWrapper blurredImage = NeighborhoodOperations.medianBlur(k, threshImage);
		ImageComponent comp = NeighborhoodOperations.connectedComponents(blurredImage, blurredImage.height() / 2,
				blurredImage.width() / 2);
		MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);
		MatWrapper grayScaledImage2 = ColorOperations.toGrayscale(maskedImage);
		MatWrapper threshImage2 = ColorOperations.threshold(grayScaledImage2);
		MatWrapper newImage = NeighborhoodOperations.secondCrop(threshImage2, maskedImage);
		newImage.Write(name + "Output" + extension);
	}

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			// PUT FUNCTION HERE WHICH ONE YOU WANT TO RUN

			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
