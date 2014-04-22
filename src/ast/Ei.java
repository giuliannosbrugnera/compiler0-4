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

	public void genC() {
		System.out.print(" + ");
		
		if (t != null) {
			t.genC();
		}
		
		if (ei != null) {
			ei.genC();
		}
	}
}