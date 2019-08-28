package visitors.typechecking;

import static visitors.typechecking.PrimtType.*;

import environments.EnvironmentException;
import environments.GenEnvironment;
import parser.ast.*;
import visitors.Visitor;

public class TypeCheck implements Visitor<Type> {

	private final GenEnvironment<Type> env = new GenEnvironment<>();

	private void checkBinOp(Exp left, Exp right, Type type) {
		type.checkEqual(left.accept(this));
		type.checkEqual(right.accept(this));
	}
	
	private void checkBinOp(Exp left, Type right, Type type) {
		type.checkEqual(left.accept(this));
		type.checkEqual(right);
	}
	
	

	// static semantics for programs; no value returned by the visitor

	@Override
	public Type visitProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
		} catch (EnvironmentException e) { // undefined variable
			throw new TypecheckerException(e);
		}
		return null;
	}

	// static semantics for statements; no value returned by the visitor

	@Override
	public Type visitWhileStmt(Exp exp, StmtSeq block) {
		BOOL.checkEqual(exp.accept(this));
		env.enterScope();
		block.accept(this);
		env.exitScope();
		return null;
	}

	@Override
	public Type visitAssignStmt(Ident ident, Exp exp) {
		Type found = env.lookup(ident);
		found.checkEqual(exp.accept(this));
		return null;
	}

	@Override
	public Type visitPrintStmt(Exp exp) {
		exp.accept(this);
		return null;
	}

	@Override
	public Type visitDecStmt(Ident ident, Exp exp) {
		env.dec(ident, exp.accept(this));
		return null;
	}

	@Override
	public Type visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
		BOOL.checkEqual(exp.accept(this));
		thenBlock.accept(this);
		if (elseBlock == null)
			return null;
		elseBlock.accept(this);
		return null;
	}

	@Override
	public Type visitBlock(StmtSeq stmtSeq) {
		env.enterScope();
		stmtSeq.accept(this);
		env.exitScope();
		return null;
	}

	// static semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Type visitSingleStmt(Stmt stmt) {
		stmt.accept(this);
		return null;
	}

	@Override
	public Type visitMoreStmt(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}

	@Override
	public Type visitSingleExp(Exp exp) {
		return exp.accept(this);
	}

	@Override
	public Type visitMoreExp(Exp first, ExpSeq rest) {
		Type found = first.accept(this);
		return found.checkEqual(rest.accept(this));
	}
	// static semantics of expressions; a type is returned by the visitor

	@Override
	public Type visitStringLiteral(String value) {
		return STRING;
	}
	
	@Override
	public Type visitCard(Exp exp) {
		return INT;
	}
	
	@Override
	public Type visitIn(Exp left, Exp right) {
		
		Type t = right.accept(this).getSetType();
		checkBinOp(left,t,t);
		/*
			let i = {1} in {1,2,3};
			print i
			
			let p = {1,2} in {1,2};
			print p
			
			let o = {{{1,2}}} in {{{{1,2}}},{{{4,5}}}};
			print o
			
			
			*/
		
		return BOOL;
}
	
	@Override
	public Type visitUnio(Exp left, Exp right) {
		Type t = left.accept(this);
		checkBinOp(left, right, t);
		return t;
}
	
	@Override
	public Type visitInts(Exp left, Exp right) {
		Type t = left.accept(this);
		checkBinOp(left, right, t);
		return t;
}
	
	@Override
	public Type visitCat(Exp left, Exp right) {
		checkBinOp(left, right, STRING);
		return STRING;
	}
	
	@Override
	public Type visitAdd(Exp left, Exp right) {
		checkBinOp(left, right, INT);
		return INT;
	}

	@Override
	public Type visitIntLiteral(int value) {
		return INT;
	}

	@Override
	public Type visitMul(Exp left, Exp right) {
		checkBinOp(left, right, INT);
		return INT;
	}

	@Override
	public Type visitSign(Exp exp) {
		return INT.checkEqual(exp.accept(this));
	}

	@Override
	public Type visitIdent(Ident id) {
		return env.lookup(id);
	}

	@Override
	public Type visitNot(Exp exp) {
		return BOOL.checkEqual(exp.accept(this));
	}

	@Override
	public Type visitAnd(Exp left, Exp right) {
		checkBinOp(left, right, BOOL);
		return BOOL;
	}

	@Override
	public Type visitBoolLiteral(boolean value) {
		return BOOL;
	}

	@Override
	public Type visitEq(Exp left, Exp right) {
		left.accept(this).checkEqual(right.accept(this));
		return BOOL;
	}

	@Override
	public Type visitSet(ExpSeq exps) {
		return new SetType(exps.accept(this));
	}
	
	
	@Override
	public Type visitPairLit(Exp left, Exp right) {
		return new PairType(left.accept(this), right.accept(this));
	}

	@Override
	public Type visitFst(Exp exp) {
		return exp.accept(this).getFstPairType();
	}

	@Override
	public Type visitSnd(Exp exp) {
		return exp.accept(this).getSndPairType();
	}

	
}
