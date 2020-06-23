package eu.burbach.diplom.algorithms;

import java.lang.reflect.InvocationTargetException;

import eu.burbach.diplom.common.Computable;
import eu.burbach.diplom.common.Matrix;

public class Gauss<T extends Computable<S>,S> implements Det<T,S> {
	
	/* die Korrektheit diese Methode ist verifiziert */
	@SuppressWarnings("unchecked")
	public T det(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows()) {
			T one= a.buildOne();
			return one;
		}
		if (a.columns()==1)
			return a.get(0, 0);
		int n= a.columns();
		Matrix<T,S> res= a.copy();
		for (int c=0; c<n-1; c++)
			for (int r= c+1; r<n; r++) {
				T factor= (T) res.get(r, c).div(res.get(c, c));
				for (int cb= c; cb<n; cb++) {
					T minus= (T) res.get(c, cb);
					res.set(r, cb, (T) res.get(r, cb).sub(minus.mult(factor)));
				}
			}
		
		T factor=res.get(0, 0);
		for (int i= 1; i<n; i++)
			factor= (T) factor.mult(res.get(i, i));
		return factor;
	}
}
