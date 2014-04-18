package ast;

public class Expr extends BaseClass
{
	private T t;
	private Ei ei;

	public Expr(T t, Ei ei)
	{
		this.t = t;
		this.ei = ei;
	}

	public void genC()
	{
        t.genC();

        if (ei != null)
        {
        	ei.genC();
        }
	}
}