package lab11_05_06.visitors.evaluation;

import static java.util.Objects.requireNonNull;
import java.util.HashSet;



public class SetValue implements Value {

	private HashSet<Value> S = new HashSet<Value>();
	
	public SetValue() {
	}
	
	public SetValue(HashSet<Value> Val) {
		for(Value v: Val)
			this.S.add(requireNonNull(v));
	}

	public Value getVal(int n) {
		int check=0;
		Value val=null;
		for(Value v: S) {
				if(check<n)
					val=v;
					++check;
			}
		return val ;
	}

	@Override
	public SetValue asSet() {
		return this;
	}

	@Override
	public String toString() {
		String str="{";
		for(Value v: S) {
			str=str+" + "+ v;
		}
		str=str+" }";
		return str;
	}

	@Override
	public int hashCode() {
		int hsval=0;
		int first=0;
		for(Value v: S) {
			if(first==0) {
			hsval=hsval+31*v.hashCode();
			++first;
			}
			hsval=hsval+v.hashCode();
		}
		return hsval;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SetValue))
			return false;
		SetValue op = (SetValue) obj;
		return S.equals(op.S);
	}
}
