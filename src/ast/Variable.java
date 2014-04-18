package ast;

public class Variable {
	
	private char name;
	
	public Variable (char name) {
		this.name = name;
	}
	
	public char getName(){
		return this.name;
	}
	
	public void genC() {
		System.out.println("int " + this.name + ";");
	}
}
