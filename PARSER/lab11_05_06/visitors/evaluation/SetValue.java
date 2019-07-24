package lab11_05_06.visitors.evaluation;

import static java.util.Objects.requireNonNull;

public class SetValue implements Value {

	private final Value fstVal;

	public SetValue(Value fstVal) {
		this.fstVal = requireNonNull(fstVal);
	}

	public Value getFstVal() {
		return fstVal;
	}

	@Override
	public SetValue asSet() {
		return this;
	}

	@Override
	public String toString() {
		return "{" + fstVal +  "}";
	}

	@Override
	public int hashCode() {
		return 31 * fstVal.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SetValue))
			return false;
		SetValue op = (SetValue) obj;
		return fstVal.equals(op.fstVal);
	}
}
