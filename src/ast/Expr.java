package ast;

import java.io.*;

// expr ::= t ei
public class Expr {
	private T t;
	private Ei ei;

	public Expr(T t, Ei ei) {
		this.t = t;
		this.ei = ei;
	}

	public void genC(FileOutputStream outputStream) {
        t.genC(outputStream);

        if (ei != null) {
        	ei.genC(outputStream);
        }
	}
}