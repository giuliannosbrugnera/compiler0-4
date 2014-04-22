package ast;

import java.io.*;

// t ::= f ti
public class T {
	private F f;
	private Ti ti;

	public T(F f, Ti ti) {
		this.f = f;
		this.ti = ti;
	}

	public void genC(FileOutputStream outputStream) {
		f.genC(outputStream);

		if (ti != null) {
			ti.genC(outputStream);
		}
	}
}