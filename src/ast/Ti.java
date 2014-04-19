package ast;

import java.io.*;

public class Ti {
	private F f;
	private Ti ti;

	public Ti(F f, Ti ti) {
		this.f = f;
		this.ti = ti;
	}

	public void genC(FileOutputStream outputStream) {
		System.out.print(" * ");
		try {
			outputStream.write(" * ".getBytes());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if (f != null) {
			f.genC(outputStream);
		}

		if (ti != null) {
			ti.genC(outputStream);
		}
	}
}