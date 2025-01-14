package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.mnemonics.MnemonicD;
import sic.asm.mnemonics.MnemonicDn;

public class Directive extends Node {
	
	public int value;
	//public String symbol;


	public static final int NOBASE = 0;
	public static final int LTORG = 1;
	public static final int START = 2;
	public static final int END = 3;
	public static final int BASE = 4;
	public static final int EQU = 5;
	public static final int ORG = 6;
    
    
    
    public Directive(Mnemonic mnemonic, int value) {
    	super(mnemonic);
    }
    
    public Directive(Mnemonic mnemonic, String symbol) {
    	super(mnemonic);
    	this.symbol = symbol;
    }
    
    @Override
    public void activate(Code code) {
    	
    	switch (this.mnemonic.opcode) {
    	case EQU:
    		if (this.symbol!=null) {
    			if (code.getSymbols().containsKey(this.symbol)) {
    				this.value = code.getSymbols().get(symbol);
    			} else if (symbol.equals(new String("*"))) {
    				this.value = code.locctr;
    			} else {
    				System.out.printf("Unknown symbol at locctr %d\n",code.locctr);
    			}
    		}
    		code.put(this.label, this.value);
    		break;
    	default:
			code.put(this.label, code.locctr);
			break;
    	}
    }
    
    @Override
    public void resolve(Code code) {
    	if (this.symbol != null) {
			this.value = code.getSymbols().get(this.symbol);
		}
    	
    	switch (this.mnemonic.opcode) {
    	case NOBASE:
    		code.baseRelative = false;
    		break;
    	case BASE:
    		code.base = this.value;
    		code.baseRelative = true;
    		break;
    	case LTORG:
    		// TODO
    		break;
    	case START:
    		code.setName(this.label);
    		code.programStart = this.value;
    		break;
    	case END:
    		code.programEnd = code.locctr;//this.value;
    		break;
    	case EQU:
    		break;
    	case ORG:
    		code.locctr = this.value;
    		code.locctr2 = this.value;
    		break;
    	}
    }

    


}
