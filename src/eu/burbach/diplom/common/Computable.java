package eu.burbach.diplom.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public class Computable<T> {

	protected Class<T> clazz;
	protected T val;
			
	public Computable(Class<T> clazz, T val) {
		this.clazz= clazz;
		this.val= val;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}
	
	public T buildOne() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return (T) clazz.getDeclaredConstructor(clazz).newInstance();
	}
	
	public Computable<T> add(Computable<T> a) {
		return this;
	}
	
	public Computable<T> sub(Computable<T> a) {
		return this;
	}
	
	public Computable<T> mult(Computable<T> a) {
		return this;
	}
	
	public Computable<T> div(Computable<T> a) {
		return this;
	}
	
	public T get() {
		return val;
	}
	
	public Double getDouble() {
		return Double.valueOf(0);
	}
	
	public Computable<T> set(T s) {
		val= s;
		return this;
	}
	
	public Computable<T> setDouble(Double s) {
		return this;
	}
	
	public void clear() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		val= buildOne();
	}
	
	public Computable<T> zero() {
		return null;
	}
	
	public Computable<T> one() {
		return null;
	}
	
	public Computable<T> minusone() {
		return null;
	}
	
	public String toString() {
		return val.toString();
	}
}
