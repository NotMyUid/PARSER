package lab11_05_06.parser.ast;

import static java.util.Objects.requireNonNull;

public abstract class UnaryOp implements Exp {
	protected final Exp exp;

	protected UnaryOp(Exp exp) {
		this.exp = requireNonNull(exp);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + exp + ")";
	}
}
