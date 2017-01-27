package generic;

public class ImageComponent {

	private Pixel[][] pixels;

	public ImageComponent(int width, int height) {
		pixels = new Pixel[width][height];
	}

	public void setPixel(Pixel value) {
		this.pixels[value.getX()][value.getY()] = new Pixel(value);
	}

	public Pixel getPixel(int x, int y) {
		if (pixels[x][y] == null) {
			return null;
		}
		return this.pixels[x][y];
	}

	public Pixel[][] getPixels() {
		return pixels;
	}
}
