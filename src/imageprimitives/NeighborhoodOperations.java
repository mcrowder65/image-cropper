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
		Imgproc.medianBlur(source, destination, k);
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
		System.out.println(img.getHeight());
		System.out.println(img.getWidth());
		boolean[][] visited = new boolean[img.getHeight()][img.getWidth()];
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
				visited[north.getY()][north.getX()] = true;
			}

			Pixel south = getNeighbor(pRoot, img, visited, pRoot.getX(), pRoot.getY() + 1);
			if (south != null) {
				stack.push(south);
				visited[south.getY()][south.getX()] = true;
			}

			Pixel west = getNeighbor(pRoot, img, visited, pRoot.getX() - 1, pRoot.getY());
			if (west != null) {
				stack.push(west);
				visited[west.getY()][west.getX()] = true;
			}

			Pixel east = getNeighbor(pRoot, img, visited, pRoot.getX() + 1, pRoot.getY());
			if (east != null) {
				stack.push(east);
				visited[east.getY()][east.getX()] = true;
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
		System.out.println(comp.getPixels().length);
		System.out.println(comp.getPixels()[0].length);

		ImageWriter iw = new ImageWriter("testImages/finalImage.jpg", original_image.getWidth(),
				original_image.getHeight());

		System.out.println(original_image.getHeight());
		System.out.println(original_image.getWidth());
		for (int i = 0; i < original_image.getHeight() - 1; i++) {
			for (int j = 0; j < original_image.getWidth() - 1; j++) {
				Pixel p = comp.getPixel(i, j);
				if (p != null) {
					iw.setPixel(p, original_image.getRGB(j, i));
				}
			}
		}
		iw.write("jpg");
	}

	private static Pixel getNeighbor(Pixel p, BufferedImage img, boolean[][] visited, int x, int y) {

		// Bounds check and color check.
		if (y < 0 || y > img.getHeight() - 1 || x < 0 || x > img.getWidth() - 1
				|| p.getColor().getRGB() != img.getRGB(x, y) || visited[y][x] || p.getRGB() != -1) {
			return null;
		}
		return new Pixel(x, y, img.getRGB(x, y));
	}

	private void doCrop(ImageComponent comp, String source) {
		BufferedImage original_image = null;
		try {
			original_image = ImageIO.read(new File(source));
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		int minX = Integer.MAX_VALUE;
		int maxX = 0;
		int minY = Integer.MAX_VALUE;
		int maxY = 0;

		for (int i = 0; i < original_image.getHeight() - 1; i++) {
			for (int j = 0; j < original_image.getWidth() - 1; j++) {
				if (original_image.getRGB(j, i) == -1) {
					if (i > maxY) {
						maxY = i;
					}
					if (i < minY) {
						minY = i;
					}
					if (j > maxX) {
						j = maxX;
					}
					if (j < minX) {
						j = minX;
					}
				}
			}
		}

	}
}
