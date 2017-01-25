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
	public static void connectedComponents(String source) {

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
		stack.push(p);
		ImageComponent component = new ImageComponent();
		while (!stack.isEmpty()) {
			Pixel pRoot = stack.pop();

			// north
			addToStack(getNeighbor(pRoot, img, visited, pRoot.getX(), pRoot.getY() - 1), stack);

			// south
			addToStack(getNeighbor(pRoot, img, visited, pRoot.getX(), pRoot.getY() + 1), stack);

			// west
			addToStack(getNeighbor(pRoot, img, visited, pRoot.getX() - 1, pRoot.getY()), stack);

			// east
			addToStack(getNeighbor(pRoot, img, visited, pRoot.getX() + 1, pRoot.getY()), stack);

		}

	}

	private static void addToStack(Pixel p, Stack<Pixel> s) {
		if (p != null) {
			s.push(p);
		}
	}

	private static Pixel getNeighbor(Pixel p, BufferedImage img, boolean[][] visited, int x, int y) {
		if (y < 0 || y > img.getHeight() - 1 || x < 0 || x > img.getWidth() - 1
				|| p.getColor().getRGB() != img.getRGB(x, y) || visited[x][y]) {
			return null;
		}
		return new Pixel(x, y, img.getRGB(x, y));
	}
}
