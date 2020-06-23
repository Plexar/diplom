package eu.burbach.diplom.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigComputable extends Computable<BigDecimal> {

	public BigComputable() {
		super(BigDecimal.class, BigDecimal.ZERO);
	}
	
	public BigComputable(BigDecimal val) {
		super(BigDecimal.class, val);
	}
	
	@Override
	public Computable<BigDecimal> add(Computable<BigDecimal> a) {
		val= val.add(a.get());
		return this;
	}
	
	@Override
	public Computable<BigDecimal> sub(Computable<BigDecimal> a) {
		val= val.subtract(a.get());
		return this;
	}

	@Override
	public Computable<BigDecimal> mult(Computable<BigDecimal> a) {
		val= val.multiply(a.get());
		return this;
	}

	@Override
	public Computable<BigDecimal> div(Computable<BigDecimal> a) {
		val= val.divide(a.get(), 50, RoundingMode.HALF_DOWN);
		return this;
	}

	@Override
	public BigDecimal get() {
		return val;
	}
	
	@Override
	public Double getDouble() {
		return val.doubleValue();
	}

	@Override
	public BigComputable set(BigDecimal s) {
		val= s;
		return this;
	}
	
	@Override
	public BigComputable setDouble(Double s) {
		val= new BigDecimal(s);
		return this;
	}

	@Override
	public void clear() {
		this.set(BigDecimal.ZERO);
	}

	@Override
	public BigComputable zero() {
		return new BigComputable(BigDecimal.ZERO);
	}

	@Override
	public BigComputable one() {
		return new BigComputable(new BigDecimal(1));
	}
	
	@Override
	public BigComputable minusone() {
		return new BigComputable(new BigDecimal(-1));
	}
}
