package imageprimitives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Rect;

import generic.ImageComponent;
import generic.MatWrapper;
import generic.MorphMask;
import generic.Tools;

public class NeighborhoodOperationsTest {

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public void testConnectedComponents() throws IOException {
		String path = "testImages/ccsTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper mw = new MatWrapper("testImages/finalImage2.jpg");

		mw.Write(path);
	}

	public void crop(String name, String extension, int k) {

		MatWrapper input = new MatWrapper(name + extension);
		MatWrapper grayScaledImage = ColorOperations.toGrayscale(input);

		/*
		 * Eric's Crap MatWrapper blurredImage =
		 * NeighborhoodOperations.medianBlur(15, grayScaledImage);
		 * blurredImage.Write(name + "_Blur" + extension); MatWrapper
		 * threshImage = ColorOperations.threshold(blurredImage);
		 * threshImage.Write(name + "_Thresh" + extension);
		 * 
		 * threshImage = MorphologicalOperations.morphClose(threshImage, new
		 * MorphMask(7, 7, 3, 3, 255)); threshImage.Write(name + "_MorphClose" +
		 * extension); ImageComponent ccOuter =
		 * NeighborhoodOperations.connectedComponents(threshImage, 1, 1);
		 * MatWrapper outerMasked = NeighborhoodOperations.inverseMask(ccOuter,
		 * threshImage); outerMasked.Write(name + "_OuterMask" + extension);
		 */
		// MorphologicalOperations.
		// ImageComponent comp =
		// NeighborhoodOperations.connectedComponents(blurredImage);
		// MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);
		// maskedImage.Write(name + "Test" + extension);
		// blurredImage.Write(name + "Test" + extension);
		MatWrapper threshImage = ColorOperations.threshold(grayScaledImage);
		MatWrapper blurredImage = NeighborhoodOperations.medianBlur(k, threshImage);
		ImageComponent comp = NeighborhoodOperations.connectedComponents(blurredImage, blurredImage.height() / 2,
				blurredImage.width() / 2);
		MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);
		MatWrapper grayScaledImage2 = ColorOperations.toGrayscale(maskedImage);
		MatWrapper threshImage2 = ColorOperations.threshold(grayScaledImage2);
		MatWrapper newImage = NeighborhoodOperations.secondCrop(threshImage2, maskedImage);
		newImage.Write(name + "Second" + extension);
	}

	@Test
	public void ericsCrap() {

		final int TOLERANCE_GROWTH_RATE = 5;
		for (int n = 1; n <= 11; n++) {
			int iteration = 0;
			String extension = ".jpg";
			String inFolder = "familySearchImages/";
			String outFolder = "intermediateOutput/";
			String name = "Crop" + n;

			MatWrapper input = new MatWrapper(inFolder + name + extension);
			input.Write(outFolder + name + "_Final" + extension);
			do {
				System.out.println(name + ", iteration " + ++iteration);

				int shrunkX = 300;
				int shrunkY = 300;

				input = new MatWrapper(outFolder + name + "_Final" + extension);
				MatWrapper shrunk = ImageSizeOperations.resizeImage(input, shrunkX, shrunkY);
				shrunk.Write(outFolder + name + "_Shrunk" + extension);
				MatWrapper grayScaledImage = ColorOperations.toGrayscale(shrunk);

				MatWrapper blurredImage = NeighborhoodOperations.medianBlur(9, grayScaledImage);
				blurredImage.Write(outFolder + name + "_Blur" + extension);
				MatWrapper threshImage = ColorOperations.thresholdSampling(blurredImage, 40);
				threshImage.Write(outFolder + name + "_Thresh" + extension);

				int morphMaskDiameter = 17 - (iteration * 2);
				if (morphMaskDiameter > 1) {
					threshImage = MorphologicalOperations.dialate(threshImage, new MorphMask(morphMaskDiameter,
							morphMaskDiameter, morphMaskDiameter / 2, morphMaskDiameter / 2, 255));
					threshImage.Write(outFolder + name + "_Dialate" + extension);
				}
				ImageComponent ccOuter = NeighborhoodOperations.connectedComponents(threshImage, 1, 1);
				int ccMaxHeight = ccOuter.getMaxHeight(true);
				int matWHeight = threshImage.height();
				if (ccMaxHeight < matWHeight / 2 || ccMaxHeight == matWHeight)
					break;

				Rect croppingRect = NeighborhoodOperations.getInverseMaskCroppingRect(ccOuter, threshImage);
				MatWrapper outerMasked = NeighborhoodOperations.inverseMask(ccOuter, threshImage);
				outerMasked.Write(outFolder + name + "_OuterMask" + extension);

				double xRatio = input.width() / ((double) shrunkX);
				double yRatio = input.height() / ((double) shrunkY);
				Rect scaledCroppingRect = Tools.ScaleRect(croppingRect, xRatio, yRatio);

				MatWrapper finalImage = ImageSizeOperations.CropToRect(input, scaledCroppingRect);
				finalImage.Write(outFolder + name + "_Final" + extension);
			} while (true);
		}

		// ImageComponent comp =
		// NeighborhoodOperations.connectedComponents(blurredImage);
		// MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);
		// maskedImage.Write(name + "Test" + extension);
		// blurredImage.Write(name + "Test" + extension);
	}

	// @Test
	// public void testCrop1() {
	// crop("familySearchImages/Crop1", ".jpg");
	// }
	//
	// @Test
	// public void testCrop2() {
	// crop("familySearchImages/Crop2", ".jpg");
	// }
	//
	// @Test
	// public void testCrop3() {
	// crop("familySearchImages/Crop3", ".jpg");
	// }
	//
	// @Test
	// public void testCrop4() {
	// crop("familySearchImages/Crop4", ".jpg");
	// }
	//
	// @Test
	// public void testCrop5() {
	// crop("familySearchImages/Crop5", ".jpg");
	// }
	//
	// @Test
	// public void testCrop6() {
	// crop("familySearchImages/Crop6", ".jpg");
	// }

	public void testCrop7() {
		crop("familySearchImages/Crop7", ".jpg", 85);

	}
	//
	// @Test
	// public void testCrop8() {
	// crop("familySearchImages/Crop8", ".jpg");
	// }
	//
	// @Test
	// public void testCrop9() {
	// crop("familySearchImages/Crop9", ".jpg");
	// }
	//
	// @Test
	// public void testCrop10() {
	// crop("familySearchImages/Crop10", ".jpg");
	// }
	//
	// @Test
	// public void testCrop11() {
	// crop("familySearchImages/Crop11", ".jpg");
	// crop("familySearchImages/Crop1", ".jpg", 85);
	// }
	//
	// //
	// // //
	// @Test
	// public void testCrop2() {
	// crop("familySearchImages/Crop2", ".jpg", 85);
	// }

	//

	public void testCrop3() {
		crop("familySearchImages/Crop3", ".jpg", 85);
	}
	// //
	// @Test
	// public void testCrop4() {
	// crop("familySearchImages/Crop4", ".jpg", 85);
	// }

	//
	// //
	// // //
	// @Test
	// public void testCrop5() {
	// crop("familySearchImages/Crop5", ".jpg", 85);
	// }
	//
	// //
	// @Test
	// public void testCrop6() {
	//
	// crop("familySearchImages/Crop6", ".jpg", 85);
	// }

	//
	// @Test
	// public void testCrop7() {
	//
	// crop("familySearchImages/Crop7", ".jpg", 85);
	// }

	//
	// //
	// @Test
	// public void testCrop8() {
	// crop("familySearchImages/Crop8", ".jpg", 85);
	// }
	//
	// //
	// // //
	// @Test
	// public void testCrop9() {
	// crop("familySearchImages/Crop9", ".jpg", 85);
	// }

	//
	// @Test
	// public void testCrop10() {
	// crop("familySearchImages/Crop10", ".jpg", 85);
	// }

	//
	// //
	// @Test
	// public void testCrop11() {
	// crop("familySearchImages/Crop11", ".jpg", 85);

	// }

}
