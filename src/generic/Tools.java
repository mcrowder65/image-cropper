package generic;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

/*
 * Static generic methods go here
 */
public class Tools {

	public static Rect ScaleRect(Rect rect, double xRatio, double yRatio) {
		return new Rect((int) (rect.x * xRatio), (int) (rect.y * yRatio), (int) (rect.width * xRatio),
				(int) (rect.height * yRatio));
	}

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	/**
	 * TESTING
	 * 
	 * @param mat
	 * @param name
	 */
	public static void DumpMat(Mat mat, String name) {
		double[] d;
		StringBuilder strBld = new StringBuilder();
		int rows = mat.rows();
		int cols = mat.cols();
		for (int r = 0; r < rows; r++) {
			strBld.append("{  ");
			for (int c = 0; c < cols; c++) {
				d = mat.get(r, c);
				for (int n = 0; n < d.length; n++) {
					if (n == 0)
						strBld.append((int) d[n]);
					else
						strBld.append("," + ((int) d[n]));
				}
				strBld.append("|");
			}
			strBld.append("  }");
		}
		String ss = strBld.toString();

		try {
			WriteStringToFile("MatDumps//" + name + ".txt", strBld.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void WriteStringToFile(String path, String str) throws Exception {
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
		wr.write(str);
		wr.close();

		// Files.write(Paths.get("./" + path), str.getBytes(UTF8_CHARSET));
	}
}
