package ast;
import java.util.ArrayList;

public class F extends BaseClass
{
	private ArrayList<Character> number;
	private char variable;

	public F(ArrayList<Character> number, char variable)
	{
		this.number = number;
		this.variable = variable;
	}

	public void genC()
	{
		if (number != null)
		{
			for (Character c:number)
	        {
	            System.out.print(c);
	        }
		}
		else
		{
			System.out.print(variable);
		}
	}
}