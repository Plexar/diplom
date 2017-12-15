package eu.burbach.diplom.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Matrix {
	
	private int rows;
	private int columns;
	private BigDecimal[][] values;

	public Matrix(int rows, int columns) {
		if (rows<1)
			throw new IllegalArgumentException("row count must be at least 1");
		if (columns<1)
			throw new IllegalArgumentException("column count must be at least 1");
		this.rows= rows;
		this.columns= columns;
		values= new BigDecimal[rows][columns];
		this.clear();
	}
	
	public void set(int row, int column, double value) {
		values[row][column]= new BigDecimal(value);
	}
	
	public double get(int row, int column) {
		return values[row][column].doubleValue();
	}
	
	public Matrix clear() {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= BigDecimal.ZERO;
		return this;
	}
	
	public int rows() {
		return rows;
	}
	
	public int columns() {
		return columns;
	}
	
	public Matrix add(Matrix m) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= values[r][c].add(m.values[r][c]);
		return this;
	}
	
	public Matrix add(double v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= values[r][c].add(new BigDecimal(v));
		return this;
	}	
	
	public Matrix sub(Matrix m) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= values[r][c].subtract(m.values[r][c]);
		return this;
	}
	
	public Matrix sub(double v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= values[r][c].subtract(new BigDecimal(v));
		return this;
	}
	
	public Matrix mult(Matrix m) {
		if (columns!=m.rows())
			throw new IllegalArgumentException("this column count must equal argument row count");
		Matrix res= new Matrix(rows,m.columns());
		for (int r=0; r<res.rows(); r++)
			for (int c=0; c<res.columns(); c++) {
				double sum= 0.0;
				for (int i=0; i<columns; i++)
					sum+=this.get(r,i)*m.get(i, c);
				res.set(r, c, sum);
			}
		return res;
	}
	
	public Matrix mult(double v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= values[r][c].multiply(new BigDecimal(v));
		return this;
	}
	public Matrix div(double v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values[r][c]= values[r][c].divide(new BigDecimal(v),100,RoundingMode.HALF_EVEN);
		return this;
	}
	
	public static Matrix e(int n) {
		Matrix m= new Matrix(n,n);
		for (int i=0; i<n; i++)
			m.set(i, i, 1.0);
		return m;
	}
	
	public double trace() {
		if (columns!=rows)
			throw new IllegalArgumentException("trace only if row count equals column count");
		BigDecimal res= BigDecimal.ZERO;
		for (int i=0; i<rows; i++)
			res= res.add(values[i][i]);
		return res.doubleValue();
	}
	
	public Matrix copy() {
		Matrix res= new Matrix(rows,columns);
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				res.values[r][c]= values[r][c];
		return res;		
	}
}
