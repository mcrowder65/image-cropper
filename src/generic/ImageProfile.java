package generic;

public class ImageProfile {

	public int[] vals;

	public ImageProfile(int dims) {
		vals = new int[dims];
	}

	public boolean hasDip() {
		// Find local minima starting from the center
		// int start = vals.length * 0.2;
		// int end = vals.length * 0.8;
		return false;
	}
}
