package parser.ast;

import visitors.Visitor;

public class Unio extends BinaryOp {
	public Unio(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitUnio(left, right);
	}

}
