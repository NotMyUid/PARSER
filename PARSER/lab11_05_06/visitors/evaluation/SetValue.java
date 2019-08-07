package lab11_05_06.visitors.evaluation;

import static java.util.Objects.requireNonNull;
import java.util.HashSet;
import java.util.Iterator;



public class SetValue implements Value, Iterable<Value> {

	private HashSet<Value> S = new HashSet<Value>();
	
	public SetValue() {
	}
	
	public SetValue(SetValue Val) {
		for(Value v : Val)
		S.add(requireNonNull(v));
	}
	
	public SetValue(Value val, SetValue rest) {
		this(rest);
		S.add(requireNonNull(val));
	}

	
	@Override
	public SetValue asSet() {
		return this;
	}

	@Override
	public String toString() {
		return S.toString();
	}

	@Override
	public int hashCode() {
		return S.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SetValue))
			return false;
		return S.equals(((SetValue) obj).S);
	}

	@Override
	public Iterator<Value> iterator() {
		return S.iterator();
	}
}
