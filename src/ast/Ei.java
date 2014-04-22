package ast;

import java.io.*;

// ei ::= '+' t ei | vazio
public class Ei {
	private T t;
	private Ei ei;

	public Ei(T t, Ei ei) {
		this.t = t;
		this.ei = ei;
	}

	public void genC(FileOutputStream outputStream) {
		System.out.print(" + ");
		try {
			outputStream.write(" + ".getBytes());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if (t != null) {
			t.genC(outputStream);
		}
		
		if (ei != null) {
			ei.genC(outputStream);
		}
	}
}