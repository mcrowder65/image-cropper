package generic;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

public class MatWrapperTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void ioTest() {
		MatWrapper mw = new MatWrapper("testImages/testImage1.jpg");
		mw.Write("testImages/outputImage1.jpg");
	}

	@Test
	public void pixelTest() {
		MatWrapper mw = new MatWrapper("testImages/testImage1.jpg");

		for (int row = mw.mat.height() / 2; row < mw.mat.height() / 2 + 100; row++) {
			for (int col = mw.mat.width() / 2; col < mw.mat.width() / 2 + 100; col++) {
				Pixel p = mw.getPixel(row, col);
				p.setColor(new Color(255, 0, 0));
				mw.setPixel(p);
			}
		}

		mw.Write("testImages/outputImage1.jpg");
	}
}
