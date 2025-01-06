package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.Node;
import sic.asm.code.Storage;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicSd extends Mnemonic {

	public MnemonicSd(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}

	@Override
	public Node parse(Parser parser) throws SyntaxError {
		// TODO Literals
		
		
		// int
		if (Character.isDigit(parser.lexer.peek()) || parser.lexer.peek()=='-')
			return new Storage(this, parser.parseNumber(Code.MIN_WORD, Code.MAX_WORD));
		// otherwise: error
		else
			throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);

		
		
	}
	
	
}
