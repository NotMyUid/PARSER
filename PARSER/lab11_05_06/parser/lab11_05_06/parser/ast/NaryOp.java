package lab11_05_06.parser.ast;

import static java.util.Objects.requireNonNull;

public abstract class NaryOp implements Exp {
	protected final ExpSeq right;

	protected NaryOp(ExpSeq right) {
		this.right = requireNonNull(right);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + right + ")";
	}

}