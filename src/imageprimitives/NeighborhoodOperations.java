package imageprimitives;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import org.opencv.imgproc.Imgproc;

import generic.ImageComponent;
import generic.MatWrapper;
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
	public static MatWrapper medianBlur(int k, MatWrapper source) {
		MatWrapper destination = new MatWrapper();
		Imgproc.medianBlur(source.mat, destination.mat, k);
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

		ImageWriter iw = new ImageWriter("testImages/finalImage.jpg", original_image.getWidth(),
				original_image.getHeight());

		for (int i = 0; i < original_image.getHeight(); i++) {
			for (int j = 0; j < original_image.getWidth(); j++) {
				Pixel p = comp.getPixel(i, j);
				if (p != null) {
					iw.setPixel(p, original_image.getRGB(j, i));
				}
			}
		}
		iw.write("jpg");
		MatWrapper mw = doCrop(comp, "testImages/finalImage.jpg");
		mw.Write("testImages/finalImage2.jpg");
	}

	private static Pixel getNeighbor(Pixel p, BufferedImage img, boolean[][] visited, int x, int y) {

		// Bounds check and color check.
		if (y < 0 || y > img.getHeight() - 1 || x < 0 || x > img.getWidth() - 1
				|| p.getColor().getRGB() != img.getRGB(x, y) || visited[y][x] || p.getRGB() != -1) {
			return null;
		}
		return new Pixel(x, y, img.getRGB(x, y));
	}

	private static Pixel getNeighbor(Pixel p, MatWrapper matW, boolean[][] visited, int x, int y) {
		// Bounds check and color check.
		if (y < 0 || y > matW.mat.height() - 1 || x < 0 || x > matW.mat.width() - 1
				|| p.getColor().getRGB() != matW.getPixel(y, x).getRGB() || visited[y][x] || p.getRGB() != -1) {
			return null;
		}
		return matW.getPixel(y, x);
	}

	private static MatWrapper doCrop(ImageComponent comp, String source) {

		int minX = Integer.MAX_VALUE;
		int maxX = 0;
		int minY = Integer.MAX_VALUE;
		int maxY = 0;

		Pixel[][] pixels = comp.getPixels();

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				Pixel p = comp.getPixel(i, j);
				if (p != null) {
					if (i > maxY) {
						maxY = i;
					}
					if (i < minY) {
						minY = i;
					}
					if (j > maxX) {
						maxX = j;
					}
					if (j < minX) {
						minX = j;
					}
				}
			}
		}
		MatWrapper img = new MatWrapper(source);
		return ImageSizeOperations.CropToRect(img, minX, minY, maxX, maxY);
	}
}
