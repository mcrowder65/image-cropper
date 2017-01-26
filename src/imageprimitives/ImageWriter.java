package imageprimitives;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import generic.Pixel;

public class ImageWriter {
	public BufferedImage buffer;
	public File image;

	public ImageWriter(String filename, int width, int height) {

		image = new File(filename);
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public void write(String filetype) {
		try {
			ImageIO.write(buffer, filetype, image);
		} catch (IOException e) {
			System.out.println("Could not write image.");
			e.printStackTrace();
		}
	}

	public void setPixel(Pixel pixel) {
		buffer.setRGB(pixel.getX(), pixel.getY(), pixel.toInteger());
	}
}
