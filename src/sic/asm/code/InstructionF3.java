package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.parsing.ParsingError;
import sic.asm.utils.Flags;
import sic.asm.utils.Utils;

public class InstructionF3 extends Node {


    public int value=0;
    //public String symbol;
    public Flags flags;
    
    public static final int MAX_OPERAND = (1<<12)-1;
    public static final int MIN_OPERAND = 0;


    
    public InstructionF3(Mnemonic mnemonic) {
        super(mnemonic);


    }
    
    public InstructionF3(Mnemonic mnemonic, int value, String symbol, Flags flags) {
        super(mnemonic);
    	this.symbol = symbol;

        this.value = value;
        this.flags = flags;
        
        //if (label!=null &&label.equals("halt")) {
			System.out.printf(" symbol %s\n",symbol);
		//}
    }
    
    @Override
    public int length() {
    	return 3;
    }
    
    @Override
    public void resolve(Code code) throws ParsingError {
    	
    	System.out.printf("BEFORE %s\n", Utils.toHex(this.value,6));
    	
		if (this.symbol != null) {
			this.value = code.getSymbols().get(this.symbol);
		}
		
    	System.out.printf("AFTER %s\n", Utils.toHex(this.value,6));

		
		this.resolveAddressing(code);
		
    	System.out.printf("AFTERAFTER %s\n", Utils.toHex(this.value,6));

		
		
        //System.out.printf("ni: %s xbpe: %s\n",Utils.toBin(flags.get_ni(),2),Utils.toBin(flags.get_xbpe(),4));

		
		if (this.value>MAX_OPERAND || this.value<MIN_OPERAND) {
			throw new ParsingError("Incorrectly sized operand.",code.locctr);
		}
		
		
	} 
    
    public boolean resolveAddressing(Code code) {
    	
    	
    	int pc = code.locctr + this.length(); //TODO if addressing breaks, change back to locctr (brez 2)
    	//int pc = code.locctr2 + this.length(); //TODO if addressing breaks, change back to locctr (brez 2)

    	int base = code.base;
    	
    	// try direct (absolute) addressing, we have relative symbol
        if (flags.isImmediate()) {
            return true;  // flags bp=0
        }
       
        // try PC-relative addressing
        if (!code.baseRelative && Flags.checkPCRelativeFit(this.value, pc)) {
            flags.setPCRelative();
            this.value = Flags.intToDisp(this.value - pc);
            return true;
        }
        // try base-relative addressing
        if (code.baseRelative && Flags.checkBaseRelativeFit(this.value, base)) {
            flags.setBaseRelative();
            this.value = Flags.intToDisp(this.value - base);
            return true;
        }
        
        // if simple addressing also try to fallback to old SIC
        if (flags.isSimple() && Flags.isSicAddr(this.value)) {
            flags.set_ni(Flags.SIC);
            return true;
        }
        
        
        return false;
    }
    
    @Override
    public byte[] emitCode() {
    	byte[] array = new byte[3];
    	
    	// If this is F3 without operands
    	if (this.flags==null) {
    		array[0] = (byte)this.mnemonic.opcode;
    		return array;
    	}
    	
    	
    	array[0] = (byte)(this.mnemonic.opcode | this.flags.get_ni());
    	
    	//hprhrh
    	//System.out.printf("%b\n",flags.isPCRelative());
    	//System.out.printf("debug: %d : %s \ndebug: %d\n", this.value, Utils.toHex(value, 6), Code.MAX_DISP);
    	//tzhtzoi
    	
    	if (!flags.isSic()) array[1] = (byte)(flags.get_xbpe() | (this.value>>8));
    	else array[1] = (byte)(flags.get_x() | this.value>>8);
    	array[2] = (byte)(this.value & 0xFF);
    	return array;
    }
    

    


}
