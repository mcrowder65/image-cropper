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

public class ColorOperationsTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void grayscaleTest() throws IOException {
		String path = "testImages/grayscaleTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper();
		MatWrapper dest = new MatWrapper();
		source.mat = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		dest = ColorOperations.toGrayscale(source);
		Highgui.imwrite(path, dest.mat);
	}

	@Test
	public void thresholdTest() throws IOException {
		String path = "testImages/thresholdTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper();
		MatWrapper dest = new MatWrapper();
		source.mat = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		dest = ColorOperations.threshold(source);
		Highgui.imwrite(path, dest.mat);
	}

	@Test
	public void histogramTest() throws IOException {
		String path = "testImages/histogramTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper();
		MatWrapper dest = new MatWrapper();
		source.mat = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		dest = ColorOperations.histogramStretch(source);
		Highgui.imwrite(path, dest.mat);
	}
}
