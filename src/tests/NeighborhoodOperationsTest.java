package tests;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import imageprimitives.NeighborhoodOperations;

public class NeighborhoodOperationsTest {

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void testConnectedComponents() {
		Mat source = Highgui.imread("images/download.png", Highgui.CV_LOAD_IMAGE_COLOR);
		NeighborhoodOperations.connectedComponents(source);
	}

}
