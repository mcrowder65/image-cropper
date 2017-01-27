package generic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.opencv.core.Mat;

/*
 * Static generic methods go here
 */
public class Tools {
	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

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
			java.nio.file.Files.write(Paths.get("MatDumps/" + name + ".txt"), strBld.toString().getBytes(UTF8_CHARSET));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
