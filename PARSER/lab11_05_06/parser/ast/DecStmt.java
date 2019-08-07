package lab11_05_06.parser.ast;

import lab11_05_06.visitors.Visitor;

public class DecStmt extends AbstractAssignStmt {

	public DecStmt(Ident ident, Exp exp) {
		super(ident, exp);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitDecStmt(ident, exp);
	}

}
