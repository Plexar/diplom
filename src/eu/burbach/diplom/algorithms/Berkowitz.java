package eu.burbach.diplom.algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import eu.burbach.diplom.common.Matrix;

public class Berkowitz {
	
	private HashMap<Integer,Matrix> s= new HashMap<>();
	private HashMap<Integer,Matrix> r= new HashMap<>();
	private HashMap<Integer,Matrix> m= new HashMap<>();
		
	private Matrix s_i(Matrix a, int i) {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
		Matrix res= new Matrix (n-i, 1);
		for (int r=i+1; r<=n; r++)
			res.set(r-i-1, 0, a.get(r-1, i-1));
		return res;
	}
	
	private Matrix r_i(Matrix a, int i) {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
		Matrix res= new Matrix (1, n-i);
		for (int j=i+1; j<=n; j++)
			res.set(0, j-i-1, a.get(i-1, j-1));
		return res;
	}
	
	private Matrix m_i(Matrix a, int i) {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
		Matrix res= new Matrix (n-i, n-i);
		for (int j=i+1; j<=n; j++)
			for (int k=i+1; k<=n; k++)
				res.set(j-i-1, k-i-1, a.get(j-1, k-1));
		return res;
	}
	
	private void fill_r_s_m(Matrix a) {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
        
		r= new HashMap<>();
		s= new HashMap<>();
		m= new HashMap<>();
		for (int i=1; i<n; i++) {
			r.put(new Integer(i), r_i(a,i));
			s.put(new Integer(i), s_i(a,i));
			m.put(new Integer(i), m_i(a,i));
		}
	}
	
	private double c_t_i_j(Matrix a, int t, int i, int j) {
		if (j>i)
			return 0;
		if (i==j)
			return -1;
		else if (i-j==1)
			return a.get(t-1, t-1);
		else if (i-j==2) 
			return r.get(t).mult(s.get(t)).get(0, 0);
		else {
			Matrix m_res= m.get(t);
			for (int k=1; k<i-j-2; k++)
				m_res= m_res.mult(m.get(t));
			Matrix rt= r.get(t);
			Matrix st= s.get(t);
			Matrix res= rt.mult(m_res).mult(st);
			return res.get(0, 0);
		}
	}
	
	private Matrix c_t(Matrix a, int t) {
		int n= a.columns();
		Matrix res= new Matrix(n-t+2,n-t+1);
		for (int i=1; i<=res.rows(); i++)
			for (int j=1; j<=res.columns(); j++)
				res.set(i-1, j-1, c_t_i_j(a,t,i,j));
		return res;
	}
	
	/* die Korrektheit dieser Methode ist verifiziert */
	public double samuelson(Matrix a) {
		if (a.columns()!=a.rows())
			return 0;
		if (a.columns()==1)
			return a.get(0, 0);
		if (a.columns()==2)
			return a.get(0, 0)*a.get(1, 1)-a.get(1, 0)*a.get(0, 1);
			
		fill_r_s_m(a);
		int n= a.columns();
		
		Queue<Matrix> c= new LinkedList<>();
		for (int i=1; i<=n; i++)
			c.add(c_t(a,i));
		
		Matrix p= c.poll();
		while (!c.isEmpty())
			p= p.mult(c.poll());
		
		return p.get(p.rows()-1, 0);
	}
	
	public static double det(Matrix a) {
		return new Berkowitz().samuelson(a);
	}
}
