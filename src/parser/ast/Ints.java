package parser.ast;

import visitors.Visitor;

public class Ints extends BinaryOp {
	public Ints(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitInts(left, right);
	}

}