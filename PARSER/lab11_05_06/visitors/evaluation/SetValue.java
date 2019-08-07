package lab11_05_06.visitors.evaluation;

import static java.util.Objects.requireNonNull;
import java.util.HashSet;


public class SetValue implements Value {

	private HashSet<Value> S = new HashSet<Value>();
	
	public SetValue() {
	}
	
	public SetValue(HashSet<Value> Val) {
		for(Value v: Val)
		S.add(requireNonNull(v));
	}

	public Value getVal() {
		return null ;
	}

	@Override
	public SetValue asSet() {
		return this;
	}

	@Override
	public String toString() {
		return "{" +   "}";
	}

	@Override
	public int hashCode() {
		return 31 ;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SetValue))
			return false;
		return S.equals(((SetValue) obj).S);
	}
}
