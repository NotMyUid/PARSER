package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class In extends BinaryOp {
	public In(Exp left, Exp right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitIn(left, right);
	}

}
