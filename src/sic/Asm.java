package sic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sic.asm.code.Code;
import sic.asm.code.SemanticError;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;


/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class Asm {

	public static String readFile(File file) {
	    byte[] buf = new byte[(int) file.length()];
	    try {
	    	InputStream s = new FileInputStream(file);
	    	try {
	    		s.read(buf);
			} finally {
	    		s.close();
	    	}
    	} catch (IOException e) {
    		return "";
	    }
	    return new String(buf);
	}


	public static void main(String[] args) {
		String input;

		// TODO
		//input = readFile(new File(args[0]));
		input = "lol 	START 42\n    "
			  + "	 	LDB #3\n"
			  + "halt	J halt\n"
			  + "	 	END zacetek";

		Parser parser = new Parser();
		Code code;
		try {
			code = parser.parse(input);
			code.activate(); // IZBRISI
			code.resolve(); // IZBRISI
			
			code.print();
		} catch (SyntaxError e) {
			System.err.println(e);
			System.exit(1);
			return;
		} catch (SemanticError e) {
			System.err.println(e);
			System.exit(1);
			return;
		}
	}

}
