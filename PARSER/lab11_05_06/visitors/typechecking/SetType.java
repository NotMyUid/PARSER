package lab11_05_06.visitors.typechecking;

import static java.util.Objects.requireNonNull;
import java.util.HashSet;


public class SetType implements Type {

	private final HashSet<Type> setTypes = new HashSet<Type>();

	public static final String TYPE_NAME = "SET";

	public SetType(HashSet<Type> Types) {
		for(Type t: Types)
			this.setTypes.add(requireNonNull(t));
	}

	public Type getType(int n) {
		int check=0;
		Type type=null;
		for(Type t: setTypes) {
				if(check<n)
					type=t;
					++check;
			}
		return type ;
	}


	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PairType))
			return false;
		SetType pt = (SetType) obj;
		return setTypes.equals(pt.setTypes);
	}

	@Override
	public int hashCode() {
		int hsval=0;
		int first=0;
		int last=setTypes.size()-1;
		for(Type t: setTypes) {
			if(first==last) {
			hsval=hsval+31*t.hashCode();
			}
			hsval=hsval+t.hashCode();
			++first;
		}
		return hsval ;
	}

	@Override
	public String toString() {
		String str="{";
		for(Type t: setTypes) {
			str=str+" + "+ t;
		}
		str=str+" }";
		return str;
	}

}
