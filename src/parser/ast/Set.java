package parser.ast;

import static java.util.Objects.requireNonNull;

import visitors.Visitor;

public class Set implements Exp {
	private final ExpSeq exps;

	public Set(ExpSeq exps) {
		this.exps = requireNonNull(exps);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + exps + ")";
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSet(exps);
	}

}