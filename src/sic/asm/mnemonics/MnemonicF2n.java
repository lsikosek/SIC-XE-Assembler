package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.InstructionF2;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF2n extends Mnemonic{
	
	public MnemonicF2n(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		// number
		if (Character.isDigit(parser.lexer.peek()))
			return new InstructionF2(this, -1, -1, parser.parseNumber(0, Code.MAX_ADDR));
		// symbol
		else if (Character.isLetter(parser.lexer.peek()))
			return new InstructionF2(this, -1, -1, parser.parseSymbol());
		// otherwise: error
		else
			throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
	}

	@Override
	public String operandToString(Node instruction) {
		Directive i = ((Directive)instruction);
		return i.symbol != null ? i.symbol : Integer.toString(i.value);
	}
	
	
}
