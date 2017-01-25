package generic;

import java.util.ArrayList;
import java.util.List;

public class ImageComponent {

	private List<Pixel> pixels;

	public ImageComponent() {
		pixels = new ArrayList<Pixel>();
	}

	public void addPixel(Pixel p) {
		pixels.add(p);
	}

	public List<Pixel> getPixels() {
		return pixels;
	}

	public void setPixels(List<Pixel> pixels) {
		this.pixels = pixels;
	}

}
