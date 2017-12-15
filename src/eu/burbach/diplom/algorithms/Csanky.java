package eu.burbach.diplom.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import eu.burbach.diplom.common.Matrix;

public class Csanky{

	/* Diese Methode ist für n>3 offensichtlich noch nicht korrekt. */
	public static double frame_parallel(Matrix a) {
		if (a.columns()!=a.rows())
			return 0.0;
		if (a.columns()<2) 
			return a.get(0, 0);
		int n= a.columns();
	
		double tr= a.trace();
		Matrix b0= Matrix.e(n);
		for (int i=n-1; i>=1;i--) {
			b0= b0.mult(a.copy().sub(
					               Matrix.e(n).mult(tr).div(i)
					                )
					   );
		}
		
		return Math.pow(-1, n)*a.mult(b0).trace() / (double) (-n);
	}
	
	/* Die Korrektheit dieser Methode ist verifiziert. */
	public static double frame_iterativ(Matrix a) {
		if (a.columns()!=a.rows())
			return 0.0;
		if (a.columns()<2) 
			return a.get(0, 0);
		int n= a.columns();

		Matrix[] b= new Matrix[n+1];
		Double[] c= new Double[n+1];
		b[0]= new Matrix(n,n);
		c[n]= new Double(1);
		
		for (int k=1; k<=n;k++) {
			b[k]= a.mult(b[k-1]).add(Matrix.e(n).mult(c[n-k+1]));
			c[n-k]= (-1/(double)k)*a.mult(b[k]).trace();
		}
		return Math.pow(-1, n)*c[0];
	}

	public static double det(Matrix a) {
		return frame_iterativ(a);
	}
}
