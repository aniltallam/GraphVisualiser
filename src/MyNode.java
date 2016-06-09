import java.awt.geom.Point2D;


public class MyNode extends java.awt.geom.Point2D.Double implements Node {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	double x,y;

	public MyNode(double i, double j, String s) {
		super(i,j);
		this.x=i;
		this.y=j;
		name=s;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public void setLocation(double x, double y) {
		super.x=x;
		super.y=y;
		this.x=x;
		this.y=y;
	}
	public void setLocation(Point2D p2) {
		super.setLocation(p2);
		this.x=p2.getX();
		this.y=p2.getY();
	}
	@Override
	public String toString() {
		return this.getName();
	}
}
