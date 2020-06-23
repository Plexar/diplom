package eu.burbach.diplom.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Matrix<T extends Computable<S>,S> {
	
	private int rows;
	private int columns;
	private List<List<T>> values;
	private Class<T> clazz;

	public Matrix(Class<T> clazz, int rows, int columns) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (rows<1)
			throw new IllegalArgumentException("row count must be at least 1");
		if (columns<1)
			throw new IllegalArgumentException("column count must be at least 1");
		this.rows= rows;
		this.columns= columns;
		values= new ArrayList<>();
		this.clazz= clazz;				
    	this.clear();
	}
	
	public Class<T> getClazz() {
		return clazz;
	}
	
	public T buildOne() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return clazz.getDeclaredConstructor().newInstance();
	}
	
	public void set(int row, int column, T value) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		T res= buildOne();
		res.set(value.get());
		values.get(row).set(column,res);
	}
	
	public T get(int row, int column) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		T res= buildOne();
		res.set(values.get(row).get(column).get());
		return res;
	}
	
	public Matrix<T,S> clear() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		values.clear();
		for (int r=0; r<rows; r++) {
			List<T> row= new ArrayList<>();
			for (int c=0; c<columns; c++)
				row.add(buildOne());
			values.add(row);
		}
		return this;
	}
	
	public int rows() {
		return rows;
	}
	
	public int columns() {
		return columns;
	}
	
	public Matrix<T,S> add(Matrix<T,S> m) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values.get(r).get(c).add(m.get(r,c));
		return this;
	}
	
	public Matrix<T,S> add(T v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values.get(r).get(c).add(v);
		return this;
	}	
	
	public Matrix<T,S> sub(Matrix<T,S> m) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values.get(r).get(c).sub(m.get(r,c));
		return this;
	}
	
	public Matrix<T,S> sub(T v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values.get(r).get(c).sub(v);
		return this;
	}
	
	public Matrix<T,S> mult(Matrix<T,S> m) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (columns!=m.rows())
			throw new IllegalArgumentException("this column count must equal argument row count");
		Matrix<T,S> res= new Matrix<>(clazz, rows,m.columns());
		for (int r=0; r<res.rows(); r++)
			for (int c=0; c<res.columns(); c++) {
				T sum= buildOne();
				for (int i=0; i<columns; i++)
					sum.add(this.get(r,i).mult(m.get(i, c)));
				res.set(r, c, sum);
			}
		return res;
	}
	
	public Matrix<T,S> mult(T v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values.get(r).get(c).mult(v);
		return this;
	}
	public Matrix<T,S> div(T v) {
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++)
				values.get(r).get(c).div(v);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Matrix<T,S> e(int n) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Matrix<T,S> m= new Matrix<>(clazz,n,n);
		T one= buildOne();
		for (int i=0; i<n; i++)
			m.set(i, i, (T) one.one());
		return m;
	}
	
	public Computable<S> trace() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (columns!=rows)
			throw new IllegalArgumentException("trace only if row count equals column count");
		Computable<S> res= buildOne();
		for (int i=0; i<rows; i++)
			res= res.add(this.get(i, i));
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Matrix<T,S> copy() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Matrix<T,S> res= new Matrix<>(clazz,rows,columns);
		for (int r=0; r<rows; r++)
			for (int c=0; c<columns; c++) {
				res.set(r,c, (T) res.buildOne().set(values.get(r).get(c).get()));
			}
		return res;		
	}
}
