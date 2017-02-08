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

			crop("team b images/result1", ".jpg", 85);
			crop("team b images/result2", ".jpg", 85);
			// crop("team b images/result3", ".jpg", 85);
			crop("team b images/result4", ".jpg", 85);
			crop("team b images/result5", ".jpg", 85);
			crop("team b images/result6", ".jpg", 85);
			crop("team b images/result7", ".jpg", 85);
			crop("team b images/result8", ".jpg", 85);
			crop("team b images/result9", ".jpg", 85);
			crop("team b images/result10", ".jpg", 85);
			crop("team b images/result11", ".jpg", 85);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
