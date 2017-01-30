package generic;

import java.awt.Color;

public class Pixel {

	private int x;
	private int y;
	private Color color;

	public Pixel(Pixel temp) {
		this.x = temp.x;
		this.y = temp.y;
		this.color = new Color(temp.getColor().getRGB());
	}

	public Pixel(int x, int y, int rgb) {
		this.x = x;
		this.y = y;
		this.color = new Color(rgb);
	}

	public Pixel(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getRGB() {
		return color.getRGB();
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int toInteger() {
		return (int) (color.getRed() * 255) << 16 | (int) (color.getGreen() * 255) << 8 | (int) (color.getBlue() * 255);
	}
}
