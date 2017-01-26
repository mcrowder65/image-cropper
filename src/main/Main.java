package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import imageprimitives.ColorOperations;
import imageprimitives.NeighborhoodOperations;

public class Main {

	public static void main(String[] args) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			if (args.length < 2) {
				System.out.println("Usage: [input image name] [output image name]");
				return;
			}
			String inputImageName = args[0];
			String outputImageName = args[1];
			if (inputImageName.equals(outputImageName)) {
				System.out.println("Error: Input cannot be the same as output.");
				return;
			}

			Mat source = Highgui.imread("testImages/" + inputImageName, Highgui.CV_LOAD_IMAGE_COLOR);
			Mat destination = source;

			// Maybe TODO? Reduce image size
			destination = ColorOperations.histogramStretch(destination);
			destination = ColorOperations.threshold(destination);
			destination = NeighborhoodOperations.medianBlur(5, destination);
			destination = NeighborhoodOperations.connectedComponents(destination);

			// TODO: Create connected component
			// TODO: Mask, aka Crop
			// TODO:

			Highgui.imwrite("images/" + outputImageName, destination);

			/*
			 * Old code stuff .... Mat destination = new Mat(source.rows(),
			 * source.cols(), source.type()); Imgproc.GaussianBlur(source,
			 * destination, new Size(45, 45), 0);
			 * Highgui.imwrite("Gaussian45.jpg", destination);
			 */

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
