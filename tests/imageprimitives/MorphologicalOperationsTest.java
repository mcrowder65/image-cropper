package imageprimitives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;

import generic.MatWrapper;
import generic.MorphMask;

public class MorphologicalOperationsTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void dialateTest() throws IOException {
		String path = "testImages/dialateTest2.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper("testImages/testImage1.jpg");
		source = ColorOperations.toGrayscale(source);
		source = ColorOperations.threshold(source);

		source = MorphologicalOperations.dialate(source, new MorphMask(3, 3, 1, 1, 255));
		source.Write(path);

	}
}
