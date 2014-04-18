package AST;

public class Line extends BaseClass
{
	private Write write;
	private Atrib atrib;

	public Line(Write write, Atrib atrib)
	{
		this.write = write;
		this.atrib = atrib;
	}

	public void genC()
	{
		// System.out.println("Class Line.");
		if (write != null)
		{
			write.genC();
		}
		else
		{
			atrib.genC();
		}
        System.out.println(";");
	}
}