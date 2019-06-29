package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class Card extends UnaryOp {

	public Card(Exp exp) {
		super(exp);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitCard(exp);
	}
}
