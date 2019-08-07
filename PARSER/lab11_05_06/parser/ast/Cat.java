package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class Cat extends BinaryOp {
	public Cat(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitCat(left, right);
	}

}
