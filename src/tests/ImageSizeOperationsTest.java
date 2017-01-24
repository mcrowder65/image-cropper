package tests;

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
	public void CropToRectTest() {
		Mat source = Highgui.imread("testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = ImageSizeOperations.CropToRect(source, 10, 10, 30, 30);
		Highgui.imwrite("cropToRectTest.jpg", dest);
	}

}
