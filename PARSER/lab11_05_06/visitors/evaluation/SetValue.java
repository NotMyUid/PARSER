package lab11_05_06.visitors.evaluation;

import static java.util.Objects.requireNonNull;
import java.util.HashSet

import sun.awt.SunHints.Value;<E>;



public class SetValue implements Value {

	private HashSet<Value> S = new HashSet<Value>;

	public SetValue(Value Val) {
		S.add(requireNonNull(Val);
	}
	public SetValue() {
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
