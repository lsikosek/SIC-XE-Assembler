package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.utils.Flags;

public class InstructionF4 extends Node {


    public int value;
    public String symbol;
    public Flags flags;


    
    public InstructionF4(Mnemonic mnemonic) {
        super(mnemonic);


    }
    
    public InstructionF4(Mnemonic mnemonic, int value, String symbol, Flags flags) {
        super(mnemonic);
        
        this.value = value;
        this.flags = flags;
    }
    
    @Override
    public void resolve(Code code
    
    

    


}
