package imageprimitives;

import org.junit.Before;
import org.opencv.core.Core;

import generic.ImageComponent;
import generic.MatWrapper;

public class NeighborhoodOperationsTest {

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/*
	 * @Test public void testConnectedComponents() { // ImageComponent comp = //
	 * NeighborhoodOperations.connectedComponents("testImages/thresholdTest.jpg"
	 * ); //MatWrapper mw = NeighborhoodOperations.mask(comp,
	 * "testImages/testImage1.jpg"); //mw.Write("testImages/finalImage2.jpg"); }
	 */

	// @Test
	public void testCrop4() {
		// greyScale threshold blur
		String name = "familySearchImages/Crop4";
		String extension = ".jpg";
		MatWrapper input = new MatWrapper(name + extension);
		MatWrapper grayScaledImage = ColorOperations.toGrayscale(input);
		MatWrapper threshImage = ColorOperations.threshold(grayScaledImage);
		MatWrapper blurredImage = NeighborhoodOperations.medianBlur(85, threshImage);
		ImageComponent comp = NeighborhoodOperations.connectedComponents(blurredImage);
		MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);

		maskedImage.Write(name + "Test" + extension);
	}

}
