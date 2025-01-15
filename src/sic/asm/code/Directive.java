package sic.asm.code;

import sic.asm.mnemonics.Mnemonic;
import sic.asm.mnemonics.MnemonicD;
import sic.asm.mnemonics.MnemonicDn;
import sic.asm.utils.ExpressionEvaluator;
import sic.asm.utils.Opcode;
import sic.asm.utils.Utils;

public class Directive extends Node {
	
	public int value;
	//public String symbol;


//	public static final int NOBASE = 0;
//	public static final int LTORG = 1;
//	public static final int START = 2;
//	public static final int END = 3;
//	public static final int BASE = 4;
//	public static final int EQU = 5;
//	public static final int ORG = 6;
    
    
    
    public Directive(Mnemonic mnemonic, int value) {
    	super(mnemonic);
    	//System.out.println("adsfdsaf" +value);
    	this.value = value;
    }
    
    public Directive(Mnemonic mnemonic, String symbol) {
    	super(mnemonic);
    	this.symbol = symbol;
    }
    
    
    @Override
    public void leave(Code code) {
    	if (this.mnemonic.name.equals("ORG")) {
    		code.locctr = this.value;
    	}
    }
    
    @Override
    public void activate(Code code) {
    	
    	switch (this.mnemonic.opcode) {
    	case Opcode.EQU:
//    		if (this.symbol!=null) {
//    			if (code.getSymbols().containsKey(this.symbol)) {
//    				this.value = code.getSymbols().get(symbol);
//    			} else if (symbol.equals(new String("*"))) {
//    				this.value = code.locctr;
//    			} else {
//    				System.out.printf("EQU: Unknown symbol at locctr %d\n",code.locctr);
//    			}
//    		}
//    		code.put(this.label, this.value);
    		
    		try {
    			if (this.symbol!=null && this.symbol.equals("*")) {
    				code.put(this.label, code.locctr);
    			}
    			else  {
					ExpressionEvaluator ee = new ExpressionEvaluator(code.getSymbols());
					this.value = ee.evaluateExpression(this.symbol);
					//Utils.pr("sdf " + value + "\n");
					code.put(this.label, this.value);
				}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		
    		
    		//System.out.printf("EQU symbol: %s value: %d\n",this.symbol,value);
    		break;
    	default:
			code.put(this.label, code.locctr);
			break;
    	}
    }
    
    @Override
    public void resolve(Code code) {
    	if (this.symbol != null && !this.mnemonic.name.equals("EQU")) {
    		//System.out.printf("%s label: %s symbol: %s value: %d\n",this.mnemonic.name, label, symbol, value);//code.getSymbols().get(this.symbol));

			this.value = code.getSymbols().get(this.symbol);

		}
    	
    	switch (this.mnemonic.opcode) {
    	case Opcode.NOBASE:
    		code.baseRelative = false;
    		break;
    	case Opcode.BASE:
    		code.base = this.value;
    		code.baseRelative = true;
    		break;
    	case Opcode.LTORG:
    		// TODO
    		break;
    	case Opcode.START:
    		code.setName(this.label);
    		code.programStart = this.value;
    		break;
    	case Opcode.END:
    		code.programEnd = code.locctr;//this.value;
    		break;
    	case Opcode.EQU:
    		break;
    	case Opcode.ORG:
    		//code.locctr = this.value;
    		//code.locctr2 = this.value;
    		break;
    	}
    }

    


}
