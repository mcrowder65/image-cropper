package imageprimitives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

import generic.MatWrapper;

public class NeighborhoodOperationsTest {

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void testConnectedComponents() throws IOException {
		String path = "testImages/ccsTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper mw = new MatWrapper("testImages/finalImage2.jpg");

		mw.Write(path);
	}

	@Test
	public void testCrop1() {

		String name = "familySearchImages/Crop1";
		String extension = ".jpg";
		MatWrapper input = new MatWrapper(name + extension);
		MatWrapper grayScaledImage = ColorOperations.toGrayscale(input);
		MatWrapper threshImage = ColorOperations.threshold(grayScaledImage);
		MatWrapper blurredImage = NeighborhoodOperations.medianBlur(85, threshImage);
		MatWrapper comp = NeighborhoodOperations.connectedComponents(blurredImage);
		MatWrapper maskedImage = NeighborhoodOperations.mask(comp, input);
		MatWrapper croppedImage = NeighborhoodOperations.doCrop(maskedImage);

		croppedImage.Write(name + "Test" + extension);

	}

}
