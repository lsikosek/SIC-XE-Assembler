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
    public int length() {
		
		switch (mnemonic.opcode) {
		case Opcode.RESB:
			return value;
			//break;
		case Opcode.RESW:
			return 3*value;
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
			return new byte[]{(byte)(value&0xFF0000),
							  (byte)(value&0x00FF00),
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
