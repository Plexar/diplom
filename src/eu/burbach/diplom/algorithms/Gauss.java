package eu.burbach.diplom.algorithms;

import eu.burbach.diplom.common.Matrix;

public class Gauss implements Det {
	
	/* die Korrektheit diese Methode ist verifiziert */
	public double det(Matrix a) {
		if (a.columns()!=a.rows())
			return 0;
		if (a.columns()==1)
			return a.get(0, 0);
		int n= a.columns();
		Matrix res= a.copy();
		for (int c=0; c<n-1; c++)
			for (int r= c+1; r<n; r++) {
				double factor= res.get(r, c)/res.get(c, c);
				for (int cb= c; cb<n; cb++) {
					double minus= res.get(c, cb);
					res.set(r, cb, res.get(r, cb)-minus*factor);
				}
			}
		
		double factor=res.get(0, 0);
		for (int i= 1; i<n; i++)
			factor*=res.get(i, i);
		return factor;
	}
}
