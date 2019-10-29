package visitors.typechecking;



public interface Type {
	default Type checkEqual(Type found) throws TypecheckerException {
		if (!equals(found))
			throw new TypecheckerException(found.toString(), toString());
		return this;
	}
	
	default void checkIsSetOrStringType() throws TypecheckerException {
		if (!(this instanceof SetType))
			throw new TypecheckerException(toString(), PrimtType.STRING + " or " + SetType.TYPE_NAME);
	}
	
	default void checkIsOnlyaSetType() throws TypecheckerException {
		if (!(this instanceof SetType))
			throw new TypecheckerException(toString(), SetType.TYPE_NAME);
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
