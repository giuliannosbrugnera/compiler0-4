package ast;

import java.util.*;

// Program ::= {Line}
public class Program
{	
	private ArrayList<Line> list;
	private Hashtable<Character, Variable> symbolTable;
	
	public Program(ArrayList<Line> list, Hashtable<Character, Variable> symbolTable) {
		this.list = list;
		this.symbolTable = symbolTable;
	}
	
	public void genC() {
		System.out.println("#include <stdio.h>\nint main(){");
		
		for (Map.Entry<Character, Variable> entry : symbolTable.entrySet()) {
 		   System.out.println("\tint " + entry.getKey() + ";");
 		}
		
		for(Line v:list){
			v.genC();
		}
		
		System.out.println("\n\treturn 0;\n}");
	}
}
