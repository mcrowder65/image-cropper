package tests;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

import imageprimitives.NeighborhoodOperations;

public class NeighborhoodOperationsTest {

	@Before
	public void setUp() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void testConnectedComponents() {
		NeighborhoodOperations.connectedComponents("images/download.png");
	}

}
