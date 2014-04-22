package ast;

import java.io.*;

// Line ::= (Write | Atrib) ';'
public class Line {
	
	private Write write;
	private Atrib atrib;

	public Line(Write write, Atrib atrib) {
		this.write = write;
		this.atrib = atrib;
	}

	public void genC(FileOutputStream outputStream) {
		if (write != null) {
			write.genC(outputStream);
		}
		else {
			atrib.genC(outputStream);
		}
		
        System.out.println(";");
        try {
        	outputStream.write(";\n".getBytes());
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
	}
}