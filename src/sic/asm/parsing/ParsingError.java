package sic.asm.parsing;

/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
@SuppressWarnings("serial")
public class ParsingError extends Exception {
	int locctr;

	public ParsingError(String msg, int locctr) {
		super(msg);
		this.locctr = locctr;
	}

	@Override
	public String toString() {
        String head = "Parsing error at " + Integer.toString(this.locctr);
        String message = this.getLocalizedMessage();
        return ((message != null) ? (head + ": " + message) : head) + ".";
	}
}