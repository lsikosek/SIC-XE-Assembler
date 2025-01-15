package sic.asm.code;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import sic.asm.parsing.ParsingError;
import sic.asm.utils.Opcode;
import sic.asm.utils.Utils;


/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class Code {

	public static final int MAX_ADDR = 0xb800;
    public static final int MIN_ADDR = 0;
	public static final int MAX_WORD = (1 << 23) - 1;
	public static final int MIN_WORD = -(1 << 23);
	
	public static final int MASK_DISP = 0xFFF;
    public static final int MIN_DISP = 0;
    public static final int MAX_DISP = (1 << 12) - 1;
    
    public static final int MASK_SDISP = 0x7FF;
    public static final int MIN_SDISP = -(1 << 11);
    public static final int MAX_SDISP = (1 << 11) - 1;
	public static final int MIN_SICADDR = 0;
	public static final int MAX_SICADDR = (1 << 15)-1;
    
	private String name;
	private List<Node> program;
	private Map<String, Integer> symbols;
	public int locctr;
	public int locctr2;
	public boolean baseRelative;
	public int base;
	
	private ListIterator<Node> iteratorHelper;
	
	public StringBuilder lst;
	
	public int programStart;
	public int programEnd;
	
	public Code() {
		this.name = "";
		this.program = new ArrayList<>();
		this.symbols = new HashMap<>();
		this.lst = new StringBuilder();
;


	}
	
	
	public void begin() {
		this.base=0;
		this.locctr = 0;
		this.locctr2 = 0;
		this.baseRelative = false;
	}
	
	public void end() {}
	
	
	
	
	

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getProgram() {
        return program;
    }

    public void setProgram(List<Node> program) {
        this.program = program;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }

    public void append(Node instruction) {
        this.program.add(instruction);
    }
    
    public void put(String label, int addr) {
    	this.symbols.put(label, addr);
    }
    
    public void activate() {
    	this.begin();
    	
    	for (Node node : this.program) {
    		node.enter(this);
    		node.activate(this);
    		node.leave(this);
    	}
    	
    	this.end();
    }
    
    public void resolve() {
    	this.begin();
    	
    	for (Node node : this.program) {
    		
    		
    		
    		System.out.println(node);
    		System.out.println(locctr);
    		
    		int oldLocctr = locctr;

    		
    		node.enter(this);
    		try {
				node.resolve(this);
			} catch (ParsingError e) {
				e.printStackTrace();
			}
    		node.leave(this);
    		
    		lst.append(Utils.stringFixedLength(Utils.toHex(oldLocctr, 5), 7));
    		lst.append(Utils.stringFixedLength(node.emitText(), 10));
    		lst.append(Utils.stringFixedLength(node.label, 6));
    		lst.append(node.toString());
    		lst.append('\n');
    		
    	}
    	
    	this.end();
    }
    
    public byte[] emitCode() {
    	byte[] c = new byte[programEnd];
    	
    	int i = programStart;
    	
    	for (Node node : program) {
    		byte[] arr = node.emitCode();
    		
    		if (arr==null) continue;
    		
    		for (byte b : arr) {
    			c[i] = b;
    			i++;
    		}
    		
    		// TODO napiši length za vse node
    		
    	}
    	
    	return c;
    }
    
    public String emitText() {
    	
    	this.begin();
    	
    	StringBuilder sbH = new StringBuilder();
    	StringBuilder sbT = new StringBuilder();

    	sbH.append("H");
    	sbH.append(String.format("%-6s",this.name));
    	sbH.append(Utils.toHex(programStart, 6));
    	
    	
    	
    	// Tzapisi
    	iteratorHelper = program.listIterator();
    	while (locctr < programEnd) {
    		sbT.append(TZapis());
    		sbT.append("\n");
    	}
    	// ...
    	
    	
    	sbH.append(Utils.toHex(locctr, 6));
    	sbH.append("\n");
    	
    	sbH.append(sbT.toString());
    	
    	sbH.append("E");
    	sbH.append(Utils.toHex(programStart, 6));
    	
    	return sbH.toString();
    	
    }
    
    public String TZapis() {
    	int length = 0;
    	int addr = this.locctr;
    	
    	StringBuilder sb = new StringBuilder();
    	StringBuilder sb2 = new StringBuilder();

    	
    	sb.append("T");
    	sb.append(Utils.toHex(addr, 6));
    	
    	while (iteratorHelper.hasNext()) {
    		
    		Node node = iteratorHelper.next();
    		
    		if (node.mnemonic==null) {
    			continue;
    			//System.out.printf("null node at %d and size of program is %d\n",iteratorHelper.previousIndex(),program.size());
    		}
    		
    		int temp = 0;
    		if (node.mnemonic.opcode!=Opcode.RESW && node.mnemonic.opcode!=Opcode.RESB && node.mnemonic.opcode!=Opcode.ORG) {
    			temp = node.length();
    		}
    		else {
    			//Utils.pr("storage prekinil t zapis\n");
    			//System.out.printf("%s\n",node.mnemonic.opcode);
        		node.enter(this);
        		node.leave(this);
        		break;
    		}
    		
    		if (length+temp>30) {
    			iteratorHelper.previous();
    			break;
    		}
    		
    		node.enter(this);
    		
    		sb2.append(node.emitText());
    		length+=temp;
    		
    		node.leave(this);
    		
    	}
    	
    	System.out.printf("After TZAPIS at %d\n",addr);
    	
    	sb.append(Utils.toHex(length, 2));
    	sb.append(sb2.toString());
    	
    	return sb.toString();
    	
    }


	public void print() {
		System.out.printf("start: %d end: %d\n",programStart,programEnd);
		byte[] a = this.emitCode();
		
		for (byte b : a) {
			System.out.printf("%s",Utils.toBin(b));
		}
		
		
		String objFile = this.emitText();
		String lstFile = this.lst.toString();
		
		//String content = "This is the content to save in the file.";
        
        try (FileWriter writer = new FileWriter(this.name+".obj")) {
            writer.write(objFile);
            System.out.println("OBJ file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (FileWriter writer = new FileWriter(this.name+".lst")) {
            writer.write(lstFile);
            System.out.println("LST file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		System.out.println(this.emitCode());
		
		System.out.print(objFile);
		
		
//		for (String key : symbols.keySet()) {
//        	System.out.printf("KEY %s : %d\n",key,symbols.get(key));
//        	//if (key==null) continue;
//            //expression = expression.replace(key, variables.get(key).toString());
//        }
		
	}
	
	

}
