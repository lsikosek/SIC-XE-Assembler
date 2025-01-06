package sic.asm.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    
	private String name;
	private List<Node> program;
	private Map<String, Integer> symbols;
	public int locctr;
	public int locctr2;
	
	public Code() {
		this.name = "";
		this.program = new ArrayList<>();
		this.symbols = new HashMap<>();

	}
	
	
	public void begin() {
		this.locctr = 0;
		this.locctr2 = 0;
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
    		node.enter(this);
    		node.resolve(this);
    		node.leave(this);
    	}
    	
    	this.end();
    }
	
	

}
