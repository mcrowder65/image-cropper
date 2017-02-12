package generic;

public class ImageComponent {

	private Pixel[][] pixels;

	public ImageComponent(int width, int height) {
		pixels = new Pixel[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Pixel p = null;
				pixels[i][j] = p;
			}
		}
	}

	public void setPixel(Pixel value) {
		this.pixels[value.getY()][value.getX()] = new Pixel(value);
	}

	public Pixel getPixel(int i, int j) {
		if (pixels[i][j] == null) {
			return null;
		}
		return this.pixels[i][j];
	}

	public Pixel[][] getPixels() {
		return pixels;
	}

	public int getMaxWidth(boolean inverse) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxWidth = 0;
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				if ((inverse && pixels[y][x] == null) || (!inverse && pixels[y][x] != null)) {
					if (x > maxX)
						maxX = x;
					if (x < minX)
						minX = x;
				}
			}
			if (minX != Integer.MAX_VALUE && maxX != Integer.MIN_VALUE) {
				if (maxX - minX + 1 > maxWidth)
					maxWidth = maxX - minX + 1;
			}
		}
		return maxWidth;
	}

	public int getMaxHeight(boolean inverse) {
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int maxHeight = 0;
		for (int x = 0; x < pixels[0].length; x++) {
			for (int y = 0; y < pixels.length; y++) {
				if ((inverse && pixels[y][x] == null) || (!inverse && pixels[y][x] != null)) {
					if (y > maxY)
						maxY = y;
					if (y < minY)
						minY = y;
				}
			}
			if (minY != Integer.MAX_VALUE && maxY != Integer.MIN_VALUE) {
				if (maxY - minY + 1 > maxHeight)
					maxHeight = maxY - minY + 1;
			}
		}
		return maxHeight;
	}
}
