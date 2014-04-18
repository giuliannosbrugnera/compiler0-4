package ast;

public class Ti extends BaseClass
{
	private F f;
	private Ti ti;

	public Ti(F f, Ti ti)
	{
		this.f = f;
		this.ti = ti;
	}

	public void genC()
	{
		System.out.print(" * ");
		
		if (f != null)
		{
			f.genC();
		}

		if (ti != null)
		{
			ti.genC();
		}
	}
}