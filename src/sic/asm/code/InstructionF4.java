package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.parsing.ParsingError;
import sic.asm.utils.Flags;

public class InstructionF4 extends Node {


    public int value;
    //public String symbol;
    public Flags flags;

    public static final int MAX_OPERAND = (1<<20)-1;
    public static final int MIN_OPERAND = 0;

    
    public InstructionF4(Mnemonic mnemonic) {
        super(mnemonic);


    }
    
    public InstructionF4(Mnemonic mnemonic, int value, String symbol, Flags flags) {
        super(mnemonic);
    	this.symbol = symbol;

        
        this.value = value;
        this.flags = flags;
    }
    
    @Override
    public int length() {
    	return 4;
    }
    
    @Override
    public void resolve(Code code) throws ParsingError {
		if (this.symbol != null) {
			this.value = code.getSymbols().get(this.symbol);
		}
		
		flags.setExtended();
		
		
		if (code.baseRelative || flags.isPCRelative()) {
			throw new ParsingError("Invalid addressing.",code.locctr);
		}
		
		if (this.value>MAX_OPERAND || this.value<MIN_OPERAND) {
			throw new ParsingError("Incorrectly sized operand.",code.locctr);
		}		
		
	}   
    
    @Override
    public byte[] emitCode() {
    	byte[] array = new byte[4];
    	
    	if (this.flags==null) {
    		array[0] = (byte)this.mnemonic.opcode;
    		return array;
    	}
    	
    	
    	array[0] = (byte)(this.mnemonic.opcode | this.flags.get_ni());
    	
    	if (!flags.isSic()) array[1] = (byte)(flags.get_xbpe() | (this.value>>16));
    	else array[1] = (byte)(flags.get_x() | this.value>>16);
    	array[2] = (byte)(this.value & 0xFF00);
    	array[3] = (byte)(this.value & 0x00FF);
    	return array;
    }
    

    


}
