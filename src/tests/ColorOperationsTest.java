package tests;

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
	public void grayscaleTest() {
		Mat source = Highgui.imread("testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = ColorOperations.toGrayscale(source);
		Highgui.imwrite("grayscaleTest.jpg", dest);
	}

	@Test
	public void thresholdTest() {
		Mat source = Highgui.imread("testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = ColorOperations.threshold(source);
		Highgui.imwrite("thresholdTest.jpg", dest);
	}

}
