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

    


}
