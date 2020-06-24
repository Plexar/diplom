package eu.burbach.diplom.algorithms;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import eu.burbach.diplom.common.Computable;
import eu.burbach.diplom.common.DoubleComputable;
import eu.burbach.diplom.common.Matrix;

public class Berkowitz<T extends Computable<S>,S> implements Det<T,S> {
	
	private HashMap<Integer,Matrix<T,S>> s= new HashMap<>();
	private HashMap<Integer,Matrix<T,S>> r= new HashMap<>();
	private HashMap<Integer,Matrix<T,S>> m= new HashMap<>();
		
	private Matrix<T,S> s_i(Matrix<T,S> a, int i) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
		Matrix<T,S> res;
			res = new Matrix<T,S> (a.getClazz(), n-i, 1);
		for (int r=i+1; r<=n; r++)
			res.set(r-i-1, 0, a.get(r-1, i-1));
		return res;
	}
	
	private Matrix<T,S> r_i(Matrix<T,S> a, int i) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
		Matrix<T,S> res= new Matrix<>(a.getClazz(),1, n-i);
		for (int j=i+1; j<=n; j++)
			res.set(0, j-i-1, a.get(i-1, j-1));
		return res;
	}
	
	private Matrix<T,S> m_i(Matrix<T,S> a, int i) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
		Matrix<T,S> res= new Matrix<T,S>(a.getClazz(), n-i, n-i);
		for (int j=i+1; j<=n; j++)
			for (int k=i+1; k<=n; k++)
				res.set(j-i-1, k-i-1, a.get(j-1, k-1));
		return res;
	}
	
	private void fill_r_s_m(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (a.columns()!=a.rows())
			throw new IllegalArgumentException("must be square matrix");
        int n= a.columns();
        
		r= new HashMap<>();
		s= new HashMap<>();
		m= new HashMap<>();
		for (int i=1; i<n; i++) {
			r.put(Integer.valueOf(i), r_i(a,i));
			s.put(Integer.valueOf(i), s_i(a,i));
			m.put(Integer.valueOf(i), m_i(a,i));
		}
	}
	
	@SuppressWarnings("unchecked")
	private T c_t_i_j(Matrix<T,S> a, int t, int i, int j) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		T one= a.buildOne();
		if (j>i) {
			return (T) one.zero();
		}
		if (i==j)
			return (T) one.minusone();
		else if (i-j==1)
			return a.get(t-1, t-1);
		else if (i-j==2) 
			return r.get(t).mult(s.get(t)).get(0, 0);
		else {
			Matrix<T,S> m_res= m.get(t);
			for (int k=1; k<i-j-2; k++)
				m_res= m_res.mult(m.get(t));
			Matrix<T,S> rt= r.get(t);
			Matrix<T,S> st= s.get(t);
			Matrix<T,S> res= rt.mult(m_res).mult(st);
			return res.get(0, 0);
		}
	}
	
	private Matrix<T,S> c_t(Matrix<T,S> a, int t) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		int n= a.columns();
		Matrix<T,S> res= new Matrix<>(a.getClazz(),n-t+2,n-t+1);
		for (int i=1; i<=res.rows(); i++)
			for (int j=1; j<=res.columns(); j++)
				res.set(i-1, j-1, c_t_i_j(a,t,i,j));
		return res;
	}
	
	/* die Korrektheit dieser Methode ist verifiziert */
	@SuppressWarnings("unchecked")
	public T samuelson(Matrix<T,S> a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		T one= a.buildOne();
		if (a.columns()!=a.rows())
			return one;
		if (a.columns()==1)
			return a.get(0, 0);
		if (a.columns()==2)
			return (T) a.get(0, 0).mult(a.get(1, 1)).sub(a.get(1, 0).mult(a.get(0, 1)));
			
		fill_r_s_m(a);
		int n= a.columns();
		
		Queue<Matrix<T,S>> c= new LinkedList<>();
		for (int i=1; i<=n; i++)
			c.add(c_t(a,i));
		
		Matrix<T,S> p= c.poll();
		while (!c.isEmpty())
			p= p.mult(c.poll());
		
		return p.get(p.rows()-1, 0);
	}
	
	@Override
	public T det(Matrix<T,S> mat) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return samuelson(mat);
	}
}
