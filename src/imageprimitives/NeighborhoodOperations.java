package imageprimitives;

import java.util.Stack;

import org.opencv.core.Rect;
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

		stack.push(p);
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

		int minX = inversemask_findMinX(component, original);
		int minY = inversemask_findMinY(component, original);
		int maxX = inversemask_findMaxX(component, original);
		int maxY = inversemask_findMaxY(component, original);
		return ImageSizeOperations.CropToRect(original, minX, minY, maxX, maxY);
	}

	public static Rect getInverseMaskCroppingRect(ImageComponent component, MatWrapper original) {
		int minX = inversemask_findMinX(component, original);
		int minY = inversemask_findMinY(component, original);
		int maxX = inversemask_findMaxX(component, original);
		int maxY = inversemask_findMaxY(component, original);
		return new Rect(minX, minY, maxX - minX, maxY - minY);
	}

	public static MatWrapper mask2(ImageComponent component, MatWrapper original) {

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

	private static int inversemask_findMinX(ImageComponent component, MatWrapper original) {

		for (int col = 0; col < original.width(); col++) {
			for (int row = 0; row < original.height(); row++) {
				Pixel p = component.getPixel(row, col);
				if (p == null) { // Found a non-cc, check pixels below to see if
									// it was just noise
					boolean justNoise = false;
					for (int subCol = col; subCol < Math.min(original.width() - 1,
							col + INVERSEMASK_TOLERANCE); subCol++) {
						if (component.getPixel(row, subCol) != null) {
							justNoise = true;
							break;
						}
					}
					if (!justNoise)
						return col;

				}
			}
		}

		return 0;

	}

	private static int inversemask_findMaxX(ImageComponent component, MatWrapper original) {

		for (int col = original.width() - 1; col > -1; col--) {
			for (int row = 0; row < original.height(); row++) {
				Pixel p = component.getPixel(row, col);
				if (p == null) { // Found a non-cc, check pixels below to see if
									// it was just noise
					boolean justNoise = false;
					for (int subCol = col; subCol > Math.max(0, col - INVERSEMASK_TOLERANCE); subCol--) {
						if (component.getPixel(row, subCol) != null) {
							justNoise = true;
							break;
						}
					}
					if (!justNoise)
						return col;

				}
			}
		}

		return original.width() - 1;
	}

	public static MatWrapper secondCrop(MatWrapper matW, MatWrapper original) {

		int height = matW.height();
		int width = matW.width();
		int startHeight = (height / 2) - 100;
		int endHeight = (height / 2) + 100;
		int startWidth = (width / 2) - (int) (0.15 * width);
		int endWidth = (width / 2) + (int) (0.15 * width);

		int minX = 0;
		int maxX = width;
		int minY = 0;
		int maxY = height;

		for (int j = 10; j < (height / 2); j++) {
			for (int i = startWidth; i < endWidth; i++) {
				Pixel p = matW.getPixel(j, i);
				if (p.getRGB() != -1) {
					minY = j;
					break;
				}
			}
			if (minY != 0) {
				int sum = 0;
				for (int z = j; z < (j + 50); z++) {

					sum += matW.getPixel(z, (width / 2) - 35).getRGB();
				}
				float avg = ((float) sum / (float) 50);
				if (sum > -2000000) {
					break;
				}
			}
		}

		for (int j = height - 10; j > 0; j--) {
			for (int i = startWidth; i < endWidth; i++) {
				Pixel p = matW.getPixel(j, i);
				if (p.getRGB() != -1) {
					maxY = j;
					break;
				}
			}
			if (maxY != height) {
				int sum = 0;
				for (int z = j; z > (j - 50); z--) {
					int top_val = 0;

					sum += matW.getPixel(z, (width / 2) - 35).getRGB();
				}

				float avg = ((float) sum / (float) 50);
				if (avg > -2000000) {
					break;
				}

			}
		}

		for (int j = 10; j < (width / 2); j++) {
			for (int i = startHeight; i < endHeight; i++) {
				Pixel p = matW.getPixel(i, j);
				if (p.getRGB() != -1) {
					minX = j;
					break;

				}
			}
			if (minX != 0) {
				int sum = 0;
				for (int z = j; z < (j + 50); z++) {
					sum += matW.getPixel(startHeight, z).getRGB();
				}
				float avg = ((float) sum / (float) 50);
				if (sum > -100000000) {
					break;
				}
			}
		}

		for (int j = width - 10; j > 0; j--) {
			for (int i = startHeight; i < endHeight; i++) {
				Pixel p = matW.getPixel(i, j);
				if (p.getRGB() != -1) {
					maxX = j;
					break;
				}
			}
			if (maxX != width) {
				int sum = 0;
				for (int z = j; z > (j - 50); z--) {
					sum += matW.getPixel(startHeight, z).getRGB();
				}

				float avg = ((float) sum / (float) 50);
				if (avg > -2000000) {
					break;
				}

			}
		}

		if (minX > 25) {
			minX = minX - 25;
		} else {
			minX = 0;
		}
		if (minY > 25) {
			minY = minY - 25;
		} else {
			minY = 0;
		}

		if (maxX < width - 50) {
			maxX = maxX + 50;
		} else {
			maxX = width - 1;
		}
		if (maxY < height - 10) {
			maxY = maxY + 10;
		} else {
			maxY = height - 1;
		}

		return ImageSizeOperations.CropToRect(original, minX, minY, maxX, maxY);

	}

	public static MatWrapper myCrap2(MatWrapper matW, MatWrapper original) {

		int height = matW.height();
		int width = matW.width();
		int startHeight = (height / 2) - 25;
		int endHeight = (height / 2) + 25;
		int startWidth = (width / 2) - (int) (0.15 * width);
		int endWidth = (width / 2) + (int) (0.15 * width);

		int minX = 0;
		int maxX = width;
		int minY = 0;
		int maxY = height;

		for (int j = 10; j < (height / 2); j++) {
			for (int i = startWidth; i < endWidth; i++) {
				Pixel p = matW.getPixel(j, i);
				if (p.getRGB() != -1) {
					minY = j;
					break;
				}
			}
			if (minY != 0) {
				break;
			}
		}

		for (int j = height - 10; j > 0; j--) {
			for (int i = startWidth; i < endWidth; i++) {
				Pixel p = matW.getPixel(j, i);
				if (p.getRGB() != -1) {
					maxY = j;
					break;
				}
			}
			if (maxY != height) {
				break;
			}
		}

		for (int j = 10; j < (width / 2); j++) {
			for (int i = startHeight; i < endHeight; i++) {
				Pixel p = matW.getPixel(i, j);
				if (p.getRGB() != -1) {
					minX = j;
					break;

				}
			}
			if (minX != 0) {
				break;
			}
		}

		for (int j = width - 10; j > 0; j--) {
			for (int i = startHeight; i < endHeight; i++) {
				Pixel p = matW.getPixel(i, j);
				if (p.getRGB() != -1) {
					maxX = j;
					break;
				}
			}
			if (maxX != width) {
				break;
			}
		}

		if (minX > 25) {
			minX = minX - 25;
		} else {
			minX = 0;
		}
		if (minY > 25) {
			minY = minY - 25;
		} else {
			minY = 0;
		}

		if (maxX < width - 20) {
			maxX = maxX + 20;
		} else {
			maxX = width - 1;
		}
		if (maxY < height - 10) {
			maxY = maxY + 10;
		} else {
			maxY = height - 1;
		}

		return ImageSizeOperations.CropToRect(original, minX, minY, maxX, maxY);

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
