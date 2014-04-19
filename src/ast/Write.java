package ast;

import java.io.*;

public class Write {
	private Expr expression;

	public Write(Expr expression) {
		this.expression = expression;
	}

	public void genC(FileOutputStream outputStream) {
		System.out.print("\tprintf (");
        System.out.print("\"%d\", ");
		try {
			outputStream.write("\tprintf (\"%d\", ".getBytes());
	        expression.genC(outputStream);
	        System.out.print(")");
	        outputStream.write(")".getBytes());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}