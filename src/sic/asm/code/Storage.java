package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;

public class Storage extends Node{
	
	public static final int RESB = 0;
	public static final int RESW = 1;
	public static final int BYTE = 2;
	public static final int WORD = 3;

	public int value;
	
	public Storage(Mnemonic mnemonic, int value) {
		super(mnemonic);
		this.value = value;
	}
	
	

}
