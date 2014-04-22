package ast;

import java.io.*;
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
	
	public void genC(FileOutputStream outputStream) {
		String content;
		System.out.println("#include <stdio.h>\nint main(){");
		
		try {
			outputStream.write("#include <stdio.h>\nint main(){\n".getBytes());
			
			for (Map.Entry<Character, Variable> entry : symbolTable.entrySet()) {
	 		   System.out.println("\tint " + entry.getKey() + ";");
	 		   content = "\tint " + entry.getKey() + ";\n";
	 		   outputStream.write(content.getBytes());
	 		}
			
			for(Line v:list){
				v.genC(outputStream);
			}
			
			System.out.println("\n\treturn 0;\n}");
			outputStream.write("\n\treturn 0;\n}".getBytes());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
