package ast;

import java.io.*;

// Atrib ::= var '=' expr
public class Atrib {
	private char variable;
	private Expr expression;

	public Atrib(char variable, Expr expression) {
		this.variable = variable;
		this.expression = expression;
	}

	public void genC(FileOutputStream outputStream) {
		String content;
		System.out.print("\t" + variable + " = ");
		try {
			content = "\t" + variable + " = ";
			outputStream.write(content.getBytes());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
        expression.genC(outputStream);
	}
}