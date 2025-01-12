package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.utils.Utils;

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
	
	@Override
    public int length() {
		
		switch (mnemonic.opcode) {
		case RESB:
			return value;
			//break;
		case RESW:
			return 3*value;
			//break;
		case BYTE:
			return 1;
			//break;
		case WORD:
			return 3;
			//break;
		}
		return 0;
    }
	
	
	@Override
    public byte[] emitCode() {
		
    	
		switch (mnemonic.opcode) {
		case RESB:
			return new byte[value];
			//break;
		case RESW:
			return new byte[3*value];
			//break;
		case BYTE:
			return new byte[]{(byte)value};
			//break;
		case WORD:
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
		case RESB:
			return new String("");
			//break;
		case RESW:
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
