package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import imageprimitives.ImageSizeOperations;

public class ImageSizeOperationsTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void CropToRectTest() throws IOException {
		String path = "testImages/cropToRectTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		Mat source = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = ImageSizeOperations.CropToRect(source, source.width() / 4, source.height() / 4, source.width() / 2,
				source.height() / 2);
		Highgui.imwrite(path, dest);
	}

}
