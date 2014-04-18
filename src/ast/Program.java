package ast;
import java.util.ArrayList;

public class Program extends BaseClass
{	
	private ArrayList<Line> list;
	
	public Program(ArrayList<Line> list)
	{
		this.list = list;	
	}
	
	public void genC()
	{
		System.out.println("#include <stdio.h>\nint main(){");
		
		for(Line v:list)
		{
			v.genC();
		}
		
		System.out.println("\n\treturn 0;\n}");
	}
}
