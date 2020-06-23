package eu.burbach.diplom.algorithms;

import java.lang.reflect.InvocationTargetException;

import eu.burbach.diplom.common.Computable;
import eu.burbach.diplom.common.Matrix;

public interface Det<T extends Computable<S>,S> {
	public T det(Matrix<T,S> mat) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
