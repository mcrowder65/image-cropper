package imageprimitives;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

import generic.ImageComponent;

public class NeighborhoodOperationsTest {

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void testConnectedComponents() {
		ImageComponent comp = NeighborhoodOperations.connectedComponents("testImages/thresholdTest.jpg");
		NeighborhoodOperations.mask(comp, "testImages/testImage1.jpg");
	}

}
