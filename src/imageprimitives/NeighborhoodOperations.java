package imageprimitives;

import java.util.Stack;

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
		MatWrapper destination = new MatWrapper(source);
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
	 * @param input
	 *            MatWrapper
	 */
	public static ImageComponent connectedComponents(MatWrapper input, int seedRow, int seedCol) {

		Stack<Pixel> stack = new Stack<Pixel>();

		boolean[][] visited = new boolean[input.height()][input.width()];
		int col = seedCol;
		int row = seedRow;
		Pixel p = new Pixel(col, row, input.getRGB(col, row));

		// TODO:make sure p is "on" - this should be done via a sampling
		// function
		stack.push(p);
		// MatWrapper component = new MatWrapper(input);
		ImageComponent component = new ImageComponent(input.width(), input.height());
		while (!stack.isEmpty()) {
			Pixel pRoot = stack.pop();
			component.setPixel(pRoot);

			Pixel north = getNeighbor(pRoot, input, visited, pRoot.getX(), pRoot.getY() - 1);
			if (north != null) {
				stack.push(north);
				visited[north.getY()][north.getX()] = true;
			}

			Pixel south = getNeighbor(pRoot, input, visited, pRoot.getX(), pRoot.getY() + 1);
			if (south != null) {
				stack.push(south);
				visited[south.getY()][south.getX()] = true;
			}

			Pixel west = getNeighbor(pRoot, input, visited, pRoot.getX() - 1, pRoot.getY());
			if (west != null) {
				stack.push(west);
				visited[west.getY()][west.getX()] = true;
			}

			Pixel east = getNeighbor(pRoot, input, visited, pRoot.getX() + 1, pRoot.getY());
			if (east != null) {
				stack.push(east);
				visited[east.getY()][east.getX()] = true;
			}

		}
		return component;

	}

	public static MatWrapper mask(ImageComponent component, MatWrapper original) {

		// MatWrapper output = new MatWrapper(orig);
		int minX = Integer.MAX_VALUE;
		int maxX = 0;
		int minY = Integer.MAX_VALUE;
		int maxY = 0;

		for (int i = 0; i < original.height(); i++) {
			for (int j = 0; j < original.width(); j++) {
				Pixel p = component.getPixel(i, j);
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
		return ImageSizeOperations.CropToRect(original, minX, minY, maxX, maxY);

	}

	public static MatWrapper inverseMask(ImageComponent component, MatWrapper original) {

		int minX = 0;
		int minY = inversemask_findMinY(component, original);
		int maxX = original.width() - 1;
		int maxY = inversemask_findMaxY(component, original);

		return ImageSizeOperations.CropToRect(original, minX, minY, maxX, maxY);

	}

	static final int INVERSEMASK_TOLERANCE = 50;

	private static int inversemask_findMinY(ImageComponent component, MatWrapper original) {

		for (int row = 0; row < original.height(); row++) {
			for (int col = 0; col < original.width(); col++) {
				Pixel p = component.getPixel(row, col);
				if (p == null) { // Found a non-cc, check pixels below to see if
									// it was just noise
					boolean justNoise = false;
					for (int subRow = row; subRow < Math.min(original.height() - 1,
							row + INVERSEMASK_TOLERANCE); subRow++) {
						if (component.getPixel(subRow, col) != null) {
							justNoise = true;
							break;
						}
					}
					if (!justNoise)
						return row;

				}
			}
		}

		return 0;

	}

	private static int inversemask_findMaxY(ImageComponent component, MatWrapper original) {

		for (int row = original.height() - 1; row > -1; row--) {
			for (int col = 0; col < original.width(); col++) {
				Pixel p = component.getPixel(row, col);
				if (p == null) { // Found a non-cc, check pixels below to see if
									// it was just noise
					boolean justNoise = false;
					for (int subRow = row; subRow > Math.max(0, row - INVERSEMASK_TOLERANCE); subRow--) {
						if (component.getPixel(subRow, col) != null) {
							justNoise = true;
							break;
						}
					}
					if (!justNoise)
						return row;

				}
			}
		}

		return original.height() - 1;

	}

	private static Pixel getNeighbor(Pixel p, MatWrapper matW, boolean[][] visited, int x, int y) {
		// Bounds check and color check.
		if (y < 0 || y > matW.mat.height() - 1 || x < 0 || x > matW.mat.width() - 1)
			return null;
		int pRGB = p.getRGB();
		int neighRGB = matW.getPixel(y, x).getRGB();
		if (pRGB != neighRGB || visited[y][x]) {
			return null;
		}
		return matW.getPixel(y, x);
	}
}
