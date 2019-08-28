package visitors.evaluation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import environments.EnvironmentException;
import environments.GenEnvironment;
import parser.MyParser;
import parser.Parser;
import parser.ParserException;
import parser.StreamTokenizer;
import parser.Tokenizer;
import parser.TokenizerException;
import parser.ast.Block;
import parser.ast.Exp;
import parser.ast.ExpSeq;
import parser.ast.Ident;
import parser.ast.Prog;
import parser.ast.Stmt;
import parser.ast.StmtSeq;
import visitors.Visitor;
import visitors.typechecking.TypeCheck;
import visitors.typechecking.TypecheckerException;

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
	public Value visitWhileStmt(Exp exp, StmtSeq block) {
		do{
			env.enterScope();
			block.accept(this);
			env.exitScope();
        }while(exp.accept(this).asBool());
		return null;
	}
	
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
	public Value visitSingleExp(Exp exp) {
		return new SetValue(exp.accept(this), new SetValue());
	}

	@Override
	public Value visitMoreExp(Exp first, ExpSeq rest) {
		return new SetValue(first.accept(this), rest.accept(this).asSet());
	}
	// dynamic semantics of expressions; a value is returned by the visitor
	
	@Override
	public Value visitCard(Exp exp) {
		String s = exp.accept(this).toString();
		int i = 0;
		if(s.startsWith("{")) {
			SetValue set = exp.accept(this).asSet();
			i = set.number(set);
		}
		else i = s.length();
		return new IntValue(i);
	}
	
	@Override
	public Value visitIn(Exp left, Exp right) {
		return new BoolValue(right.accept(this).asSet().isIn(left.accept(this)));
	}
	
	@Override
	public Value visitUnio(Exp left, Exp right) {
		SetValue prov = new SetValue(left.accept(this).asSet());
		prov = prov.union(left.accept(this).asSet(), right.accept(this).asSet());
		return prov;
	}
	
	@Override
	public Value visitInts(Exp left, Exp right) {
		SetValue prov = new SetValue(left.accept(this).asSet());
		prov = prov.retain(left.accept(this).asSet(), right.accept(this).asSet());
		return prov;
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
	public Value visitSet(ExpSeq exps) {
		return exps.accept(this);
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
		boolean ntc = false;
		String in = "";
		String out = "";
		
		for(int i = 0; i<args.length; ++i) {
			if (args[i].equals("-ntc")) ntc = true;
			else if (args[i].equals("-i")) in = (args[i+1]);
			else if (args[i].equals("-o")) out = (args[i+1]);
		}
			
	
		
		
		try (Tokenizer tokenizer = new StreamTokenizer(!in.isEmpty() ? new FileReader(in) : new InputStreamReader(System.in));) {
			Parser parser = new MyParser(tokenizer);
			Prog prog = parser.parseProg();
				if(!ntc) prog.accept(new TypeCheck());
			prog.accept(new Eval());
		} catch (TokenizerException e) {
			err.println("Tokenizer error: " + e.getMessage());
		} catch (ParserException e) {
			err.println("Syntax error: " + e.getMessage());
		} catch (TypecheckerException e) {
			err.println("Static error: " + e.getMessage());
		} catch (EvaluatorException e) {
			err.println("Dynamic error: " + e.getMessage());
		} catch (IOException e) {
			err.println("IO error: " + e.getMessage());
		} catch (Throwable e) {
			err.println("Unexpected error.");
			e.printStackTrace();
		}
	}
	

	


}
