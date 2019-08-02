package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class MoreExp extends More<Exp, ExpSeq> implements ExpSeq {
	public MoreExp(Exp first, ExpSeq rest) {
		super(first, rest);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitMoreExp(first, rest);
	}
}