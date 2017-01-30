package generic;

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

	}
}
