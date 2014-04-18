package ast;

public class T extends BaseClass
{
	private F f;
	private Ti ti;

	public T(F f, Ti ti)
	{
		this.f = f;
		this.ti = ti;
	}

	public void genC()
	{
		f.genC();

		if (ti != null)
		{
			ti.genC();
		}
	}
}