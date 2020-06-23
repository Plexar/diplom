package eu.burbach.diplom.algorithms;

import java.lang.reflect.InvocationTargetException;

import eu.burbach.diplom.common.Computable;
import eu.burbach.diplom.common.Matrix;

public class Pan<T extends Computable<S>,S> implements Det<T,S> {
	public T det(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return a.buildOne();
	}
}
