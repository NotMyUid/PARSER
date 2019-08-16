package lab11_05_06.visitors.typechecking;

import static lab11_05_06.visitors.typechecking.PrimtType.BOOL;
import static lab11_05_06.visitors.typechecking.PrimtType.INT;
import static lab11_05_06.visitors.typechecking.PrimtType.STRING;

public interface Type {
	default Type checkEqual(Type found) throws TypecheckerException {
		if (!equals(found))
			throw new TypecheckerException(found.toString(), toString());
		return this;
	}
	default Type keepFst() {
		if(this.toString().contains("INT"))
			return INT;
		if(this.toString().contains("STRING"))
			return STRING;
		if(this.toString().contains("BOOL"))
			return BOOL;
		return (Type) this;
		
	}
	default void checkIsPairType() throws TypecheckerException {
		if (!(this instanceof PairType))
			throw new TypecheckerException(toString(), PairType.TYPE_NAME);
	}

	default Type getFstPairType() throws TypecheckerException {
		checkIsPairType();
		return ((PairType) this).getFstType();
	}

	default Type getSndPairType() throws TypecheckerException {
		checkIsPairType();
		return ((PairType) this).getSndType();
	}
}
