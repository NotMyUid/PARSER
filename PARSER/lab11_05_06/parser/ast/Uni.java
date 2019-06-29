package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class Uni extends BinaryOp {
	public Uni (Exp left, Exp right) {
		super(left,right);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitUni(left, right);
	}

}
