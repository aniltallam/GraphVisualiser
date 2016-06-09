import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class MyEdge extends Line2D.Double implements Edge {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyNode p1,p2;
MyEdge(MyNode p1,MyNode p2){
	super(p1,p2);
	super.x1=p1.getX();  super.y1=p1.getY();
	super.x2=p2.getX();  super.y2=p2.getY();
	this.p1=p1;
	this.p2=p2;
}
public int getDist(){
	return (int) p1.distance(p2);
}
@Override
public Rectangle2D getBounds2D() {
	return new Rectangle2D.Double(p1.getX(),p1.getY(),p2.getX()-p1.getX(),p2.getY()-p1.getY());
}
@Override
public MyNode getP1() {
	return p1;
}
@Override
public MyNode getP2() {
	return p2;
}
@Override
public double getX1() {
	return p1.getX();
}
@Override
public double getX2() {
	return p2.getX();
}
@Override
public double getY1() {
	return p1.getY();
}
@Override
public double getY2() {
	return p2.getY();
}
@Override
public void setLine(double x1, double y1, double x2, double y2) {
	p1.x=x1;
	p1.y=y1;
	p2.x=x2;
	p2.y=y2;
}
public void setLine(Point2D pt1, Point2D pt2){
	p1=(MyNode)pt1;
	p2=(MyNode)pt2;
}
@Override
public Node getFromNode() {
	return p1;
}
@Override
public Node getToNode() {
	return p2;
}
}
