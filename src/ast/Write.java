package ast;

public class Write extends BaseClass
{
	private Expr expression;

	public Write(Expr expression)
	{
		this.expression = expression;
	}

	public void genC()
	{
		System.out.print("\tprintf (");
        System.out.print("\"%d\", ");
        expression.genC();
        System.out.print(")");
	}
}