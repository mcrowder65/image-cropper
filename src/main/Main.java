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
		MatWrapper profiled = NeighborhoodOperations.profile(threshImage);
		MatWrapper blurredImage = NeighborhoodOperations.medianBlur(k, profiled);
		// blurredImage.Write(name + "blur" + extension);
		ImageComponent comp = NeighborhoodOperations.connectedComponents(blurredImage, blurredImage.height() / 2,
				blurredImage.width() / 2);
		MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);
		// maskedImage.Write(name + "mask" + extension);
		MatWrapper grayScaledImage2 = ColorOperations.toGrayscale(maskedImage);
		MatWrapper threshImage2 = ColorOperations.threshold(grayScaledImage2);
		// threshImage2.Write(name + "thresh" + extension);
		// MatWrapper newImage =
		// NeighborhoodOperations.profile_cols(threshImage2, maskedImage);
		MatWrapper newImage = NeighborhoodOperations.secondCrop(threshImage2, maskedImage);
		newImage.Write(name + "Output" + extension);
	}

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			// crop("familySearchImages/crop7", ".jpg", 85);
			runTeamAImages();
			// runTeamBImages();
			// crop("familySearchImages/Crop3", ".jpg", 15);

			// PUT FUNCTION HERE WHICH ONE YOU WANT TO RUN

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	private static void runTeamAImages() {
		crop("familySearchImages/Crop1", ".jpg", 85);
		crop("familySearchImages/Crop2", ".jpg", 85);
		crop("familySearchImages/Crop3", ".jpg", 15);
		crop("familySearchImages/Crop4", ".jpg", 85);
		crop("familySearchImages/Crop5", ".jpg", 85);
		crop("familySearchImages/Crop6", ".jpg", 85);
		crop("familySearchImages/Crop7", ".jpg", 85);
		crop("familySearchImages/Crop8", ".jpg", 85);
		crop("familySearchImages/Crop9", ".jpg", 85);
		crop("familySearchImages/Crop10", ".jpg", 85);
		crop("familySearchImages/Crop11", ".jpg", 85);
	}

	private static void runTeamBImages() {
		crop("team b images/result1", ".jpg", 85);
		crop("team b images/result10", ".jpg", 85);
		crop("team b images/result11", ".jpg", 85);
		crop("team b images/result2", ".jpg", 85);
		crop("team b images/result3", ".jpg", 15);
		crop("team b images/result4", ".jpg", 85);
		crop("team b images/result5", ".jpg", 85);
		crop("team b images/result6", ".jpg", 85);
		crop("team b images/result7", ".jpg", 85);
		crop("team b images/result8", ".jpg", 85);
		crop("team b images/result9", ".jpg", 85);
	}

	private static void runTeamCImages() {
		crop("Team c images/resulting_from_crop1", ".jpg", 85);
		crop("Team c images/resulting_from_crop11", ".jpg", 85);
		crop("Team c images/resulting_from_crop2", ".jpg", 85);
		crop("Team c images/resulting_from_crop6", ".jpg", 85);
		crop("Team c images/resulting_from_crop8", ".jpg", 85);
		crop("Team c images/resulting_from_crop9", ".jpg", 85);
		crop("Team c images/result2 copy", ".jpg", 85);
	}
}
