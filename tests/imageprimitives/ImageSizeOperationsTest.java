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

public class ImageSizeOperationsTest {

	@Before
	public void setup() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@Test
	public void ResizeImageTest() throws IOException {
		String path = "testImages/resizeImage.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper();
		MatWrapper dest = new MatWrapper();
		source.mat = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		dest = ImageSizeOperations.resizeImage(source, 200, 200);
		Highgui.imwrite(path, dest.mat);
	}

	@Test
	public void CropToRectTest() throws IOException {
		String path = "testImages/cropToRectTest.jpg";
		Path fileToDeletePath = Paths.get(path);
		Files.deleteIfExists(fileToDeletePath);

		MatWrapper source = new MatWrapper();
		MatWrapper dest = new MatWrapper();
		source.mat = Highgui.imread("testImages/testImage1.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		dest = ImageSizeOperations.CropToRect(source, source.mat.width() / 4, source.mat.height() / 4,
				source.mat.width() / 2, source.mat.height() / 2);
		Highgui.imwrite(path, dest.mat);
	}

}
