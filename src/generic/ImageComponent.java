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
}
