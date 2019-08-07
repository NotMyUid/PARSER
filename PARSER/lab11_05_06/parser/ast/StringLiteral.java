package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class StringLiteral extends PrimLiteral<String> {

		public StringLiteral(String n) {
			super(n);
		}

		@Override
		public <T> T accept(Visitor<T> visitor) {
			return visitor.visitStringLiteral(value);
		}
	}