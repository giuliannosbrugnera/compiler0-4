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

	public void genC(FileOutputStream outputStream) {
		try {
			if (number != null) {
				for (Character c:number) {
		            System.out.print(c);
		            outputStream.write(Character.toString(c).getBytes());
		        }
			}
			else {
				System.out.print(variable);
				outputStream.write(Character.toString(variable).getBytes());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}