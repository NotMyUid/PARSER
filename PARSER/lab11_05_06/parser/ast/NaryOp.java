package lab11_05_06.parser.ast;

import static java.util.Objects.requireNonNull;

public abstract class NaryOp implements Exp {
	protected final Exp left;
	protected final ExpSeq right;

	protected NaryOp(Exp left, ExpSeq right) {
		this.left = requireNonNull(left);
		this.right = requireNonNull(right);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + left + "," + right + ")";
	}

}