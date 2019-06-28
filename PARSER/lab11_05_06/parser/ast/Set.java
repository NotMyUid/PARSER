package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class Set extends NaryOp {
	public Set(Exp left, ExpSeq right) {
		super(left, right);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSet(left, right);
	}
}