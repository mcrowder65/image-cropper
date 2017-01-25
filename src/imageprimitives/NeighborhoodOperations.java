package imageprimitives;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

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

		Stack<Pixel> stack = new Stack();
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
		while (!stack.isEmpty()) {
			Pixel pRoot = stack.pop();
			addToStack(getNorthP(pRoot, img), stack);
			addToStack(getSouthP(pRoot, img), stack);
			addToStack(getEastP(pRoot, img), stack);
			addToStack(getWestP(pRoot, img), stack);

		}

	}

	private static void addToStack(Pixel p, Stack s) {
		if (p != null) {
			s.push(p);
		}
	}

	private static Pixel getNorthP(Pixel p, BufferedImage img) {
		int newY = p.getY() - 1;
		int newX = p.getX();

		if (newY < 0) {
			return null;
		}
		if (p.getColor().getRGB() != img.getRGB(newX, newY)) {
			return null;
		}
		return new Pixel(newX, newY, img.getRGB(newX, newY));
	}

	private static Pixel getSouthP(Pixel p, BufferedImage img) {
		int newY = p.getY() + 1;
		int newX = p.getX();

		if (newY > img.getHeight() - 1) {
			return null;
		}
		if (p.getColor().getRGB() != img.getRGB(newX, newY)) {
			return null;
		}
		return new Pixel(newX, newY, img.getRGB(newX, newY));
	}

	private static Pixel getWestP(Pixel p, BufferedImage img) {
		int newY = p.getY();
		int newX = p.getX() - 1;

		if (newX < 0) {
			return null;
		}
		if (p.getColor().getRGB() != img.getRGB(newX, newY)) {
			return null;
		}
		return new Pixel(newX, newY, img.getRGB(newX, newY));
	}

	private static Pixel getEastP(Pixel p, BufferedImage img) {
		int newY = p.getY();
		int newX = p.getX() + 1;

		if (newX > img.getWidth() - 1) {
			return null;
		}
		if (p.getColor().getRGB() != img.getRGB(newX, newY)) {
			return null;
		}
		return new Pixel(newX, newY, img.getRGB(newX, newY));
	}

}
