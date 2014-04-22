package ast;

import java.io.*;

// ti ::= '*' f ti | vazio
public class Ti {
	private F f;
	private Ti ti;

	public Ti(F f, Ti ti) {
		this.f = f;
		this.ti = ti;
	}

	public void genC() {
		System.out.print(" * ");
		
		if (f != null) {
			f.genC();
		}

		if (ti != null) {
			ti.genC();
		}
	}
}