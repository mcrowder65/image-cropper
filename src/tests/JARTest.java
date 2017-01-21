package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Scalar;

public class JARTest {

	@Test
	public void test() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		org.opencv.core.Mat mat = new org.opencv.core.Mat();
		mat.setTo(new Scalar(4));
	}

}
