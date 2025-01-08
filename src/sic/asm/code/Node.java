package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.parsing.ParsingError;


public abstract class Node {

	protected String label;
	protected Mnemonic mnemonic;
	protected String comment;
	
	public String symbol;
	public int value;

	public Node(Mnemonic mnemonic) {
		this.mnemonic = mnemonic;
	}

	public String getLabel() {
		return label == null ? "" : label;
	}

	public void setLabel(String label) {
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
		code.locctr = code.locctr2;
		code.locctr2 += this.length();
	}
	
	public void leave(Code code) {}
	
	public void activate(Code code) {
		code.put(this.label, code.locctr);
	}
	
	public void resolve(Code code) throws ParsingError {
		if (this.symbol != null) {
			this.value = code.getSymbols().get(this.symbol);
		}
	}
	
	public byte[] emitCode() {
		return null;
	}

}
