package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.InstructionF2;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
import sic.asm.utils.Registers;

public class MnemonicF2rr extends Mnemonic {
	
	public MnemonicF2rr(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		// register
		if (Character.isLetter(parser.lexer.peek())) {
			
			String reg1 = parser.parseSymbol();
			
			// REGISTER NOT FOUND
			if (!Registers.symbolToValuesMap.containsKey(reg1)) {
				throw new SyntaxError(String.format("Invalid register name '%s'", reg1), parser.lexer.row, parser.lexer.col);
			}
			
			parser.lexer.skipWhitespace(); // ČE BO NAPAKA IZBRIŠI TO VRSTICO
			if (Character.isLetter(parser.lexer.peek())) {
				String reg2 = parser.parseSymbol();
				
				// REGISTER NOT FOUND
				if (!Registers.symbolToValuesMap.containsKey(reg2)) {
					throw new SyntaxError(String.format("Invalid register name '%s'", reg2), parser.lexer.row, parser.lexer.col);
				}
				
				// SUCCESSFULLY RETURN RESULT
				return new InstructionF2(this, Registers.symbolToValuesMap.get(reg1), Registers.symbolToValuesMap.get(reg2), 0);
				
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
