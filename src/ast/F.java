package ast;

import java.io.*;
import java.util.*;

// f ::= number | var
public class F {
	private ArrayList<Character> number;
	private char variable;

	public F(ArrayList<Character> number, char variable) {
		this.number = number;
		this.variable = variable;
	}

	public void genC() {
		if (number != null) {
			for (Character c:number) {
	            System.out.print(c);
	        }
		}
		else {
			System.out.print(variable);
		}
	}
}