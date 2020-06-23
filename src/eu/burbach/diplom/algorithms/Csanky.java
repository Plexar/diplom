package eu.burbach.diplom.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import eu.burbach.diplom.common.Computable;
import eu.burbach.diplom.common.Matrix;

public class Csanky<T extends Computable<S>,S> implements Det<T,S> {

	/* Diese Methode ist für n>3 offensichtlich noch nicht korrekt. */
	@SuppressWarnings("unchecked")
	public T frame_parallel(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows())
			return (T) a.get(0, 0).zero();
		if (a.columns()<2) 
			return a.get(0, 0);
		int n= a.columns();
	
		T tr= (T) a.trace();
		Matrix<T,S> b0= a.e(n);
		for (int i=n-1; i>=1;i--) {
			b0= b0.mult(a.copy().sub(
					               a.e(n).mult(tr).div((T) a.buildOne().setDouble(Double.valueOf(-n)))
					                )
					   );
		}
		
		return (T) a.buildOne().setDouble(Math.pow(-1, n)*a.mult(b0).trace().getDouble() / Double.valueOf(-n));
	}
	
	/* Die Korrektheit dieser Methode ist verifiziert. */
	@SuppressWarnings("unchecked")
	public T frame_iterativ(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows())
			return (T) a.buildOne().zero();
		if (a.columns()<2) 
			return (T) a.get(0, 0);
		int n= a.columns();

		List<Matrix<T,S>> b= new ArrayList<>();
		for(int i=0; i<n+1; i++)
			b.add(new Matrix<T,S>(a.getClazz(),1,1));
		
		Double[] c= new Double[n+1];
		b.set(0, new Matrix<T,S>(a.getClazz(),n,n));
		c[n]= Double.valueOf(1);
		
		for (int k=1; k<=n;k++) {
			b.set(k, a.mult(b.get(k-1)).add(a.e(n).mult((T) a.buildOne().setDouble(c[n-k+1]))));
			c[n-k]= a.buildOne().setDouble(-1/(double)k).mult(a.mult(b.get(k)).trace()).getDouble();
		}
		return (T) a.buildOne().setDouble(Math.pow(-1, n)*c[0]);
	}

	public T det(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		return frame_parallel(a);
		return frame_iterativ(a);
	}
}
