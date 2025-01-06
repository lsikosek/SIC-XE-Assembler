package sic.asm.mnemonics;

import sic.asm.code.InstructionF3;
import sic.asm.code.Node;
import sic.asm.parsing.Parser;

public class MnemonicF3 extends Mnemonic{
	
	public MnemonicF3(String name, int opcode, String hint, String desc) {
		super(name, opcode, hint, desc);
	}
	
	@Override
	public Node parse(Parser parser) {
		return new InstructionF3(this);
	}

}
