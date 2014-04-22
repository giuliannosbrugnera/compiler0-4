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

	public void genC() {
		System.out.print("\t" + variable + " = ");
        expression.genC();
	}
}