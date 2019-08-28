package parser.ast;

import visitors.Visitor;

public class Card extends UnaryOp {

	public Card(Exp exp) {
		super(exp);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitCard(exp);
	}
}