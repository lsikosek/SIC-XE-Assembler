package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.parsing.ParsingError;
import sic.asm.utils.Flags;
import sic.asm.utils.Utils;


public abstract class Node {

	protected String label;
	protected Mnemonic mnemonic;
	protected String comment;
	
	public String symbol;
	public int value;

	public Node(Mnemonic mnemonic) {
		this.mnemonic = mnemonic;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLabel() {
		return label == null ? "" : label;
	}

	public void setLabel(String label) {
		//System.out.printf("label was set for %s\n",this.mnemonic.name);
		this.label = label;
	}

	/**
	 * Return comment as a string.
	 */
	public String getComment() {
		return comment == null ? "" : comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Return string representation of the node.
	 * Label and comment are not included.
	 */
	@Override
	public String toString() {
		return mnemonic.toString() + " " + operandToString();
	}

	public String operandToString() {
		return mnemonic.operandToString(this);
	}
	
	public int length() {
		return 0;
	}
	
	public void enter(Code code) {
		//code.locctr = code.locctr2;
		//code.locctr2 += this.length();
	}
	
	public void leave(Code code) {
		code.locctr += this.length();
	}
	
	public void activate(Code code) {
		
		
		//System.out.printf("%s label %s symbol %s\n", this.mnemonic.name, this.label, this.symbol);
		
		
		if (label!=null) code.put(this.label, code.locctr/*+this.length()*/); //TODO possible cause of narobe addressing
	}
	
	public void resolve(Code code) throws ParsingError {
		if (this.symbol != null) {
			this.value = code.getSymbols().get(this.symbol);
		}
	}
	
	public byte[] emitCode() {
		return null;
	}
	
	public String emitText() {
		byte[] arr = emitCode();
		if (arr==null) return new String("");
		StringBuilder sb = new StringBuilder();
			
		for (byte b_ : arr) {
			

			//System.out.printf("[%s - %s\n",Utils.toBin(b_),Utils.toHex(b_));

			int b = Flags.intToDisp(b_);
			//System.out.printf("%s - %s]\n",Utils.toBin(b),Utils.toHex(b));


			
			sb.append(Utils.toHex(b_));
		}
		
		//System.out.print("\n");
		
		return sb.toString();
	}

}
