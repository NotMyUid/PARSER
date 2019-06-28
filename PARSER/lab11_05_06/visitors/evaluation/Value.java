package lab11_05_06.visitors.evaluation;

public interface Value {
	/* default conversion methods */
	
	default String asString() {
		throw new EvaluatorException("Expecting a String");
	}
	
	default int asInt() {
		throw new EvaluatorException("Expecting an integer");
	}

	default boolean asBool() {
		throw new EvaluatorException("Expecting a boolean");
	}

	default PairValue asPair() {
		throw new EvaluatorException("Expecting a pair");
	}
}
