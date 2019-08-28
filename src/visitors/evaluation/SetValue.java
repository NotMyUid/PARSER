package visitors.evaluation;

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
	
	public boolean isIn(Value val) {
		return this.S.contains(val);
	}
	
	
	public int number(SetValue set) {
		return set.S.size();
	}

	
	public SetValue union(SetValue fstval, SetValue sndval) {
		SetValue prov = new SetValue(fstval);
		prov.S.addAll(sndval.S);
		return prov;
	}

	public SetValue retain(SetValue fstval, SetValue sndval) {
		SetValue prov = new SetValue(fstval);
		prov.S.retainAll(sndval.S);
		return prov;
	}
	
	@Override
	public SetValue asSet() {
		return this;
	}

	@Override
	public String toString() {
		String str="";
		Iterator<Value> itr = S.iterator();  
        while (itr.hasNext()) {  
             str+=itr.next();
             str+=", ";}
        if (str!="")
        str=str.substring(0, str.length() - 2);
             return "{" + str + "}";             
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
