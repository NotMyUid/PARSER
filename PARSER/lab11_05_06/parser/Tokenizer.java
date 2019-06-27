package lab11_05_06.parser;

public interface Tokenizer extends AutoCloseable {

	TokenType next() throws TokenizerException;

	String tokenString();

	int intValue();

	TokenType tokenType();

	boolean hasNext();

	public void close() throws TokenizerException;

	boolean boolValue();

	String stringValue();


}