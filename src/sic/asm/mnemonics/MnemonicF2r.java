package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.InstructionF2;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
import sic.asm.code.Registers;

public class MnemonicF2r extends Mnemonic{
	
	public MnemonicF2r(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		// register
		if (Character.isLetter(parser.lexer.peek())) {
			String reg = parser.parseSymbol();
			if (!Registers.symbolToValuesMap.containsKey(reg)) {
				throw new SyntaxError(String.format("Invalid register name '%s'", reg), parser.lexer.row, parser.lexer.col);
			}
			return new InstructionF2(this, Registers.symbolToValuesMap.get(reg), -1, -1);
			
		// otherwise: error
		}
		else
			throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
	}

	@Override
	public String operandToString(Node instruction) {
		Directive i = ((Directive)instruction);
		return i.symbol != null ? i.symbol : Integer.toString(i.value);
	}
	
	
}