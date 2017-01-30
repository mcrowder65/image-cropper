package generic;

public class MorphMask {
	public int rows;
	public int cols;
	public int pivotRow;
	public int pivotCol;
	public int onValue;

	public MorphMask(int rows, int cols, int pivotRow, int pivotCol, int onValue) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.pivotRow = pivotRow;
		this.pivotCol = pivotCol;
		this.onValue = onValue;
	}

}
