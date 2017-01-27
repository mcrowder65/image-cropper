package imageprimitives;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import generic.ImageComponent;
import generic.Pixel;

public class NeighborhoodOperations {

	/**
	 * @param k
	 *            int - k is kernel size that we want to blur by
	 * @param source
	 *            Mat
	 * @param destination
	 *            Mat
	 */
	public static Mat medianBlur(int k, Mat source) {
		Mat destination = new Mat();
		try {

			Imgproc.medianBlur(source, destination, k);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}

		return destination;
	}

	/**
	 * Connected component <br>
	 * 
	 * Push s onto stack, S <br>
	 * While S is not empty <br>
	 * --- Pop S to get pixel p<br>
	 * --- if p not in L (not visited/marked) <br>
	 * ------ add p to Cs <br>
	 * ------ add p to L (add to marked list L) <br>
	 * ------ For each of p's 4-connected neighbors, <br>
	 * ---------- if q has brightness value < t <br>
	 * -------------- push q onto S<br>
	 * Output Cs
	 * 
	 * @param source
	 *            String
	 */
	public static ImageComponent connectedComponents(String source) {

		Stack<Pixel> stack = new Stack<Pixel>();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(source));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		boolean[][] visited = new boolean[img.getWidth()][img.getHeight()];
		Pixel p = new Pixel(img.getWidth() / 2, img.getHeight() / 2,
				img.getRGB(img.getWidth() / 2, img.getHeight() / 2));

		// TODO:make sure p is "on" - this should be done via a sampling
		// function
		stack.push(p);
		ImageComponent component = new ImageComponent(img.getWidth(), img.getHeight());

		while (!stack.isEmpty()) {
			Pixel pRoot = stack.pop();
			component.setPixel(pRoot);

			Pixel north = getNeighbor(pRoot, img, visited, pRoot.getX(), pRoot.getY() - 1);
			if (north != null) {
				stack.push(north);
				visited[north.getX()][north.getY()] = true;
			}

			Pixel south = getNeighbor(pRoot, img, visited, pRoot.getX(), pRoot.getY() + 1);
			if (south != null) {
				stack.push(south);
				visited[south.getX()][south.getY()] = true;
			}

			Pixel west = getNeighbor(pRoot, img, visited, pRoot.getX() - 1, pRoot.getY());
			if (west != null) {
				stack.push(west);
				visited[west.getX()][west.getY()] = true;
			}

			Pixel east = getNeighbor(pRoot, img, visited, pRoot.getX() + 1, pRoot.getY());
			if (east != null) {
				stack.push(east);
				visited[east.getX()][east.getY()] = true;
			}

		}
		return component;

	}

	public static void mask(ImageComponent comp, String source) {
		BufferedImage original_image = null;
		try {
			original_image = ImageIO.read(new File(source));
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		ImageWriter iw = new ImageWriter("testImages/finalImage.jpg", original_image.getWidth(),
				original_image.getHeight());

		for (int i = 0; i < original_image.getHeight(); i++) {
			for (int j = 0; j < original_image.getWidth(); j++) {
				Pixel p = comp.getPixel(i, j);
				if (p != null) {
					iw.setPixel(p, original_image.getRGB(i, j));
				}
			}
		}
		iw.write("jpg");
	}

	private static Pixel getNeighbor(Pixel p, BufferedImage img, boolean[][] visited, int x, int y) {

		// Bounds check and color check.
		if (y < 0 || y > img.getHeight() - 1 || x < 0 || x > img.getWidth() - 1
				|| p.getColor().getRGB() != img.getRGB(x, y) || visited[x][y]) {
			return null;
		}
		return new Pixel(x, y, img.getRGB(x, y));
	}
}
