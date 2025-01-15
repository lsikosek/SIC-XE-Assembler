package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.InstructionF3;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;
import sic.asm.utils.Flags;

public class MnemonicF3m extends Mnemonic{
	
	
	public int m;
	public Flags flags;
	
	public MnemonicF3m(String name, int opcode, String hint, String desc) {
		super(name, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		
		flags = new Flags();
		int value;
		String symbol;
		// PODPORA ZA LITERALE
		
		// ---------------
		
		if (parser.lexer.advanceIf('#')) 
			flags.set_ni(Flags.IMMEDIATE);
		else if (parser.lexer.advanceIf('@'))
			flags.set_ni(Flags.INDIRECT);
		else 
			flags.set_ni(Flags.SIMPLE);
		
		if (Character.isDigit(parser.lexer.peek()) || parser.lexer.peek() == '-') {
			value = parser.parseNumber(0, Code.MAX_ADDR);
			symbol = null;
		}
		else if (Character.isLetter(parser.lexer.peek()) || parser.lexer.peek() == '_') {
			value = 0;
			symbol = parser.parseSymbol();
			//System.out.printf("Parsed: %s mnemonic %s\n",symbol,this);
		} else {
			throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
		}
		
		if (parser.parseIndexed()) {
			if (flags.isSimple()) {
				flags.setIndexed();
			}
			else {
				throw new SyntaxError(String.format("Indexed addressing not supported here"), parser.lexer.row, parser.lexer.col);
			}
		}
		
		
		//System.out.printf("mnemonic symbol: %s\n",symbol);
		
		return new InstructionF3(this, value, symbol, flags);
		
		
		
		
	}
	
	@Override
	public String operandToString(Node ins) {
		StringBuilder sb = new StringBuilder();
		if (flags.isImmediate()) sb.append('#');
		if (flags.isIndirect()) sb.append('@');
		
		if (ins.getSymbol()!=null) sb.append(ins.getSymbol());
		else sb.append(ins.value);
		
		return sb.toString();
	}

}