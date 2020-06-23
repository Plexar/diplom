package eu.burbach.diplom.common;

public class DoubleComputable extends Computable<Double> {

	public DoubleComputable() {
		super(Double.class, Double.valueOf(0));
	}
	
	public DoubleComputable(Double val) {
		super(Double.class, val);
	}
	
	@Override
	public Computable<Double> add(Computable<Double> a) {
		val= Double.valueOf(val + a.get());
		return this;
	}
	
	@Override
	public Computable<Double> sub(Computable<Double> a) {
		val= Double.valueOf(val - a.get());
		return this;
	}

	@Override
	public Computable<Double> mult(Computable<Double> a) {
		val= Double.valueOf(val * a.get());
		return this;
	}

	@Override
	public Computable<Double> div(Computable<Double> a) {
		val= Double.valueOf(val / a.get());
		return this;
	}

	@Override
	public Double get() {
		return Double.valueOf(val);
	}
	
	@Override
	public Double getDouble() {
		return Double.valueOf(val);
	}

	@Override
	public DoubleComputable set(Double s) {
		val= s;
		return this;
	}
	
	@Override
	public DoubleComputable setDouble(Double s) {
		val= s;
		return this;
	}

	@Override
	public void clear() {
		this.set(Double.valueOf(0));
	}

	@Override
	public DoubleComputable zero() {
		return new DoubleComputable(Double.valueOf(0));
	}

	@Override
	public DoubleComputable one() {
		return new DoubleComputable(Double.valueOf(1));
	}
	
	@Override
	public DoubleComputable minusone() {
		return new DoubleComputable(Double.valueOf(-1));
	}
}
