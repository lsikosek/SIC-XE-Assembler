package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Directive;
import sic.asm.code.InstructionF1;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;

public class MnemonicF1 extends Mnemonic{

	public MnemonicF1(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) {
		return new InstructionF1(this);
	}
}
