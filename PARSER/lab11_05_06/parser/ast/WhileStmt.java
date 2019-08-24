package lab11_05_06.parser.ast;

import static java.util.Objects.requireNonNull;

import lab11_05_06.visitors.Visitor;

public class WhileStmt implements Stmt {
	 	private final Exp exp; 
	    private final StmtSeq block;

	    public WhileStmt(Exp exp, StmtSeq block) {
	        this.exp = requireNonNull(exp);
	        this.block = requireNonNull(block);
	    }

	    @Override
	    public String toString() {
	        return getClass().getSimpleName() + "(" + block +  "," + exp + ")";
	    }

	    @Override
	    public <T> T accept(Visitor<T> visitor) {
	        return visitor.visitWhileStmt(exp, block);
	    }
}
