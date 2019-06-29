package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class Ins extends BinaryOp {
	public Ins (Exp left, Exp right) {
		super(left,right);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitIns(left, right);
	}

}
