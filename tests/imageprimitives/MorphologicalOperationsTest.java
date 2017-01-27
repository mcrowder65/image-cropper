package imageprimitives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.highgui.Highgui;

import generic.MatWrapper;

public class MorphologicalOperationsTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void dialateTest() throws IOException {
		String path = "testImages/dialateTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper();
		MatWrapper dest = new MatWrapper();
		source.mat = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		dest = ImageSizeOperations.resizeImage(source, 200, 200);
		dest = MorphologicalOperations.dialate(dest, null, 255);
		Highgui.imwrite(path, dest.mat);

	}
}
