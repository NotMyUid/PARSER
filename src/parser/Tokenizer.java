package parser;

public interface Tokenizer extends AutoCloseable {

	TokenType next() throws TokenizerException;

	String isString();
	
	String tokenString();

	int intValue();

	TokenType tokenType();

	boolean hasNext();

	public void close() throws TokenizerException;

	boolean boolValue();

}