package tests;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import imageprimitives.ColorOperations;

public class ColorOperationsTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void grayscaleTest() throws IOException {
		String path = "testImages/grayscaleTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		java.nio.file.Files.deleteIfExists(fileToDeletePath);

		Mat source = Highgui.imread("testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = ColorOperations.toGrayscale(source);
		Highgui.imwrite(path, dest);
	}

	@Test
	public void thresholdTest() throws IOException {
		String path = "testImages/thresholdTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		java.nio.file.Files.deleteIfExists(fileToDeletePath);

		Mat source = Highgui.imread("testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = ColorOperations.threshold(source);
		Highgui.imwrite(path, dest);
	}

}
