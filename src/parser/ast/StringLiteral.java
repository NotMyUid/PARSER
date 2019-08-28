package parser.ast;

import visitors.Visitor;

public class StringLiteral extends PrimLiteral<String> {

		public StringLiteral(String n) {
			super(n);
		}

		@Override
		public <T> T accept(Visitor<T> visitor) {
			return visitor.visitStringLiteral(value);
		}
	}