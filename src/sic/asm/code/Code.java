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

	private String name;
	private List<Node> program;
	private Map<String, Integer> symbols;
	
	public Code() {
		this.name = "";
		this.program = new ArrayList<>();
		this.symbols = new HashMap<>();

	}

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
	
	

}
