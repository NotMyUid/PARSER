package visitors.typechecking;

import static java.util.Objects.requireNonNull;

public class SetType implements Type {

	private final Type fstType;

	public static final String TYPE_NAME = "SET";

	public SetType(Type fstType) {
		this.fstType = requireNonNull(fstType);
	}

	public Type getFstType() {
		return fstType;
	}


	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SetType))
			return false;
		SetType pt = (SetType) obj;
		return fstType.equals(pt.fstType);
	}

	@Override
	public int hashCode() {
		return 31 * fstType.hashCode();
	}

	@Override
	public String toString() {
		return fstType + " " + TYPE_NAME;
	}

}
