package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;

public class InstructionF2 extends Node {

    public int reg1;
    public int reg2;
    public int value;
    public String symbol;


    
    public InstructionF2(Mnemonic mnemonic, int reg1, int reg2, int num) {
        super(mnemonic);
        
        this.reg1 = reg1;
        this.reg2 = reg2;
        this.value = value;

    }
    
    public InstructionF2(Mnemonic mnemonic, int reg1, int reg2, String symbol) {
        super(mnemonic);
        
        this.reg1 = reg1;
        this.reg2 = reg2;
        this.symbol = symbol;
    }
    
    @Override
    public int length() {
    	return 2;
    }

    @Override
    public byte[] emitCode() {
    	byte[] array = new byte[2];
    	array[0] = (byte)this.mnemonic.opcode;
    	
    	int regs = 0;
    	regs |= reg2; // TODO spremeni nastavitev registrov iz -1 na 0 povsod
    	regs |= (reg1<<4);
    	
    	array[1] = (byte)regs;
    	return array;
    }


}
