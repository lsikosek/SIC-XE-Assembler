package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.InstructionF2;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
import sic.asm.utils.Registers;

public class MnemonicF2rn extends Mnemonic{
	
	
	public MnemonicF2rn(String mnemonic, int opcode, String hint, String desc) {
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
			
			parser.lexer.skipWhitespace(); // ČE BO NAPAKA IZBRIŠI TO VRSTICO
			if (Character.isDigit(parser.lexer.peek())) {
				int num = parser.parseNumber(0, Code.MAX_ADDR);
				
				return new InstructionF2(this, Registers.symbolToValuesMap.get(reg), -1, num);
				
			}
			else {
				throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
			}
		// otherwise: error
		}
		else
			throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
	}


	
}
