package lab11_05_06.parser;

import lab11_05_06.parser.ast.Prog;

public interface Parser {

	Prog parseProg() throws ParserException;

}