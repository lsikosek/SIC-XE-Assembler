package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.mnemonics.MnemonicD;
import sic.asm.mnemonics.MnemonicDn;

public class Directive extends Node {
	
	public int value;
	public String symbol;


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
    }
    
    @Override
    public void resolve(Code code

    


}
