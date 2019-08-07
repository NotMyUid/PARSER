package lab11_05_06.visitors.evaluation;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import lab11_05_06.environments.EnvironmentException;
import lab11_05_06.environments.GenEnvironment;
import lab11_05_06.parser.MyParser;
import lab11_05_06.parser.Parser;
import lab11_05_06.parser.ParserException;
import lab11_05_06.parser.StreamTokenizer;
import lab11_05_06.parser.Tokenizer;
import lab11_05_06.parser.TokenizerException;
import lab11_05_06.parser.ast.Block;
import lab11_05_06.parser.ast.Exp;
import lab11_05_06.parser.ast.ExpSeq;
import lab11_05_06.parser.ast.Ident;
import lab11_05_06.parser.ast.Prog;
import lab11_05_06.parser.ast.Stmt;
import lab11_05_06.parser.ast.StmtSeq;
import lab11_05_06.visitors.Visitor;
import lab11_05_06.visitors.typechecking.TypeCheck;
import lab11_05_06.visitors.typechecking.TypecheckerException;

import static java.lang.System.err;
import static java.util.Objects.requireNonNull;

public class Eval implements Visitor<Value> {

	private final GenEnvironment<Value> env = new GenEnvironment<>();
	private final PrintWriter printWriter;

	public Eval() {
		printWriter = new PrintWriter(System.out, true);
	}

	public Eval(PrintWriter printWriter) {
		this.printWriter = requireNonNull(printWriter);
	}

	// dynamic semantics for programs; no value returned by the visitor
	@Override
	public Value visitProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
			// possible runtime errors
			// EnvironmentException: undefined variable
		} catch (EnvironmentException e) {
			throw new EvaluatorException(e);
		}
		return null;
	}

	// dynamic semantics for statements; no value returned by the visitor

	@Override
	public Value visitAssignStmt(Ident ident, Exp exp) {
		env.update(ident, exp.accept(this));
		return null;
	}

	@Override
	public Value visitPrintStmt(Exp exp) {
		printWriter.println(exp.accept(this));
		return null;
	}

	@Override
	public Value visitDecStmt(Ident ident, Exp exp) {
		env.dec(ident, exp.accept(this));
		return null;
	}

	@Override
	public Value visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
		if (exp.accept(this).asBool())
			thenBlock.accept(this);
		else if (elseBlock != null)
			elseBlock.accept(this);
		return null;
	}

	@Override
	public Value visitBlock(StmtSeq stmtSeq) {
		env.enterScope();
		stmtSeq.accept(this);
		env.exitScope();
		return null;
	}

	// dynamic semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Value visitSingleStmt(Stmt stmt) {
		stmt.accept(this);
		return null;
	}
	
	
	@Override
	public Value visitMoreStmt(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}
	
	@Override
	public Value visitExpSeq(Exp exp, ExpSeq expseq) {
		exp.accept(this);
		expseq.accept(this);
		return null;
	}

	@Override
	public Value visitSingleExp(Exp exp) {
		exp.accept(this);
		return null;
	}
	
	@Override
	public Value visitMoreExp(Exp first, ExpSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}


	// dynamic semantics of expressions; a value is returned by the visitor
	
	@Override
	public Value visitInts(Exp left, Exp right) {
		return new SetValue();//right.accept(this));
	}
	
	@Override
	public Value visitCat(Exp left,Exp right) {
		return new StringValue(left.accept(this).asString() + right.accept(this).asString());
	}

	@Override
	public Value visitAdd(Exp left, Exp right) {
		return new IntValue(left.accept(this).asInt() + right.accept(this).asInt());
	}

	@Override
	public Value visitIntLiteral(int value) {
		return new IntValue(value);
	}

	@Override
	public Value visitMul(Exp left, Exp right) {
		return new IntValue(left.accept(this).asInt() * right.accept(this).asInt());
	}

	@Override
	public Value visitSign(Exp exp) {
		return new IntValue(-exp.accept(this).asInt());
	}

	@Override
	public Value visitIdent(Ident id) {
		return env.lookup(id);
	}

	@Override
	public Value visitNot(Exp exp) {
		return new BoolValue(!exp.accept(this).asBool());
	}

	@Override
	public Value visitAnd(Exp left, Exp right) {
		return new BoolValue(left.accept(this).asBool() && right.accept(this).asBool());
	}

	@Override
	public Value visitStringLiteral(String value) {
		return new StringValue(value);
	}
	
	@Override
	public Value visitBoolLiteral(boolean value) {
		return new BoolValue(value);
	}

	@Override
	public Value visitEq(Exp left, Exp right) {
		return new BoolValue(left.accept(this).equals(right.accept(this)));
	}
	
	@Override
	public Value visitSet(ExpSeq right) {
		return new SetValue();
	}
	
	@Override
	public Value visitPairLit(Exp left, Exp right) {
		return new PairValue(left.accept(this), right.accept(this));
	}

	@Override
	public Value visitFst(Exp exp) {
		return exp.accept(this).asPair().getFstVal();
	}

	@Override
	public Value visitSnd(Exp exp) {
		return exp.accept(this).asPair().getSndVal();
	}

	public static void main(String[] args) {
		try (Tokenizer tokenizer = new StreamTokenizer(new InputStreamReader(System.in))) {
			Parser parser = new MyParser(tokenizer);
			Prog prog = parser.parseProg();
			prog.accept(new TypeCheck());
			prog.accept(new Eval());
		} catch (TokenizerException e) {
			err.println("Tokenizer error: " + e.getMessage());
		} catch (ParserException e) {
			err.println("Syntax error: " + e.getMessage());
		} catch (TypecheckerException e) {
			err.println("Static error: " + e.getMessage());
		} catch (EvaluatorException e) {
			err.println("Dynamic error: " + e.getMessage());
		} catch (Throwable e) {
			err.println("Unexpected error.");
			e.printStackTrace();
		}
	}


}