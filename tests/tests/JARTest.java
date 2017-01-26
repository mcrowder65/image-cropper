package tests;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class JARTest {

	@Test
	public void test() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat mat = new Mat();
		mat.setTo(new Scalar(4));
	}

}
