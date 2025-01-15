package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.utils.Opcode;
import sic.asm.utils.Utils;

public class Storage extends Node{
	
//	public static final int RESB = 0;
//	public static final int RESW = 1;
//	public static final int BYTE = 2;
//	public static final int WORD = 3;

	public int value;
	
	public Storage(Mnemonic mnemonic, int value) {
		super(mnemonic);
		this.value = value;
	}
	
	@Override
	public void leave(Code code) {
		
		System.out.printf("STORGAE: %s %d\n",this.mnemonic.name, value);
		
		if (this.mnemonic.opcode == Opcode.RESB) {
	    	code.locctr += this.value;
		}
		else if (this.mnemonic.opcode == Opcode.RESW) {
	    	code.locctr += 3*this.value;
		} else {
			code.locctr += this.length();
		}
	}
	
	@Override
    public int length() {
		
		switch (mnemonic.opcode) {
		case Opcode.RESB:
			System.out.printf("value of storage: %d\n",value);

			return 0;//value;
			//break;
		case Opcode.RESW:
			System.out.printf("value of storage: %d\n",value);
			return 0;//3*value;
			//break;
		case Opcode.BYTE:
			return 1;
			//break;
		case Opcode.WORD:
			return 3;
			//break;
		}
		return 0;
    }
	
	
	@Override
    public byte[] emitCode() {
		
    	
		switch (mnemonic.opcode) {
		case Opcode.RESB:
			return new byte[value];
			//break;
		case Opcode.RESW:
			return new byte[3*value];
			//break;
		case Opcode.BYTE:
			return new byte[]{(byte)value};
			//break;
		case Opcode.WORD:
			return new byte[]{(byte)((value&0xFF0000)>>16),
							  (byte)((value&0x00FF00)>>8),
							  (byte)(value&0x0000FF)};
			//break;
		}
		
		return null;
		
    }
	
	@Override
	public String emitText() {
		switch (mnemonic.opcode) {
		case Opcode.RESB:
			return new String("");
			//break;
		case Opcode.RESW:
			return new String("");
			//break;
		default:
			byte[] arr = emitCode();
			
			StringBuilder sb = new StringBuilder();
			
			for (byte b : arr) {
				sb.append(Utils.toHex(b));
			}
			
			return sb.toString();
			
			

			//break;
		}
		
	}
	

}
