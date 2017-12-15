package eu.burbach.diplom.algorithms;

import eu.burbach.diplom.common.Matrix;

public class Leibniz {

	/* diese Methode berechnet für n>3 keine Determinante */
	public static double det(Matrix a) {
		if (a.rows()!=a.columns())
			return 0;
		if (a.rows()==1)
			return a.get(0, 0);
		int n= a.rows();
		double sum=0;
		for (int c=1; c<=n; c++) {
			double factor= 1;
			for (int i=1; i<=n; i++) {
				double v= a.get(i-1, (i+c-2)%n);
				factor*= v;
			}
			sum+=factor;
		}
		for (int c=1; c<=n; c++) {
			double factor= 1;
			for (int i=1; i<=n; i++) {
				double v= a.get(n-i, (i+c-2)%n); 
				factor*= v;
			}
			sum-=factor;
		}
		return sum;
	}
}
