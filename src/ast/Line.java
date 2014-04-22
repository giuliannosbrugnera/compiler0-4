package ast;

// Line ::= (Write | Atrib) ';'
public class Line {
	
	private Write write;
	private Atrib atrib;

	public Line(Write write, Atrib atrib) {
		this.write = write;
		this.atrib = atrib;
	}

	public void genC() {
		if (write != null) {
			write.genC();
		}
		else {
			atrib.genC();
		}
		
        System.out.println(";");
	}
}