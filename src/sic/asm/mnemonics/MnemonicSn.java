package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.Node;
import sic.asm.code.Storage;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicSn extends Mnemonic{
	
	public MnemonicSn(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		// number
		if (Character.isDigit(parser.lexer.peek())) {
			int temp = parser.parseNumber(Code.MIN_ADDR, Code.MAX_ADDR);
			//System.out.printf("temp %s %d\n",this.name,temp);
			return new Storage(this, temp);
		}
		// otherwise: error
		else
			throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
	}
	
}
