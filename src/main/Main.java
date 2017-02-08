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

			crop("familySearchImages/Crop1", ".jpg", 85);
			crop("familySearchImages/Crop2", ".jpg", 85);
			crop("familySearchImages/Crop3", ".jpg", 85);
			crop("familySearchImages/Crop4", ".jpg", 85);
			crop("familySearchImages/Crop5", ".jpg", 85);
			crop("familySearchImages/Crop6", ".jpg", 85);
			crop("familySearchImages/Crop7", ".jpg", 85);
			crop("familySearchImages/Crop8", ".jpg", 85);
			crop("familySearchImages/Crop9", ".jpg", 85);
			crop("familySearchImages/Crop10", ".jpg", 85);
			crop("familySearchImages/Crop11", ".jpg", 85);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
