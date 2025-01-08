package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;

public class InstructionF1 extends Node {
    
    public InstructionF1(Mnemonic mnemonic) {
        super(mnemonic);
    }
    
    @Override
    public int length() {
    	return 1;
    }
    
    @Override
    public byte[] emitCode() {
    	byte[] array = new byte[1];
    	array[0] = (byte)this.mnemonic.opcode;
    	return array;
    }

    


}
