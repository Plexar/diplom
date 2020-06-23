package eu.burbach.diplom.algorithms;

import java.lang.reflect.InvocationTargetException;

import eu.burbach.diplom.common.Computable;
import eu.burbach.diplom.common.Matrix;

public class Leibniz<T extends Computable<S>,S> implements Det<T,S> {

	/* diese Methode berechnet für n>3 keine Determinante */
	@SuppressWarnings("unchecked")
	public T det(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.rows()!=a.columns())
			return (T) a.buildOne().zero();
		if (a.rows()==1)
			return a.get(0, 0);
		int n= a.rows();
		double sum=0;
		for (int c=1; c<=n; c++) {
			double factor= 1;
			for (int i=1; i<=n; i++) {
				double v= a.get(i-1, (i+c-2)%n).getDouble();
				factor*= v;
			}
			sum+=factor;
		}
		for (int c=1; c<=n; c++) {
			double factor= 1;
			for (int i=1; i<=n; i++) {
				double v= a.get(n-i, (i+c-2)%n).getDouble(); 
				factor*= v;
			}
			sum-=factor;
		}
		return (T) a.buildOne().setDouble(sum);
	}
}
