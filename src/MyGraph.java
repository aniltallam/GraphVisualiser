import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Anil Tallam
 */
class MyGraph {
	ArrayList<MyNode> nodes=new ArrayList<MyNode>();
    ArrayList<MyEdge> edges=new ArrayList<MyEdge>();
    
    ArrayList<MyNode> nodecolor=new ArrayList<MyNode>();
    ArrayList<MyEdge> edgecolor=new ArrayList<MyEdge>();
	
//Functions Used by UI classes

	public void removeAdjacentEdges(MyNode mp) {
		ArrayList<MyEdge> els=new ArrayList<MyEdge>();
		for(MyEdge me:edges){
			if(me.p1==mp || me.p2==mp){
				els.add(me);
			}
		}
		for(MyEdge me:els){
			edges.remove(me);
		}
	}
	public void addEdge(MyNode p1, MyNode p2) {
		MyEdge me=new MyEdge(p1,p2);
		boolean edgealreadyexists = false;
		for(MyEdge e:edges){
			if(e.p1==p1 && e.p2==p2){ edgealreadyexists=true; break;}
			if(e.p1==p2 && e.p2==p1){ edgealreadyexists=true; break;}
		}
		if(!edgealreadyexists) this.edges.add(me);
	}
	public void addNode(int x, int y, String name) {
		MyNode mp=new MyNode(x,y,name);
		nodes.add(mp);
	}
	public MyNode getMyNodeAt(Point2D cp){
		for(MyNode mp : nodes){
	    	if(mp.distance(cp)<5){
	    		return mp;
	    	}
		}
		return null;
	}
	public String getNodeName(MyNode mp) {
		return mp.getName();
	}
	public Color getNodeColor(MyNode mp) {
		if(nodecolor.contains(mp))return Color.GREEN;
		else return Color.BLUE;
	}
	public Color getEdgeColor(MyEdge me) {
		if(edgecolor.contains(me)) return Color.GREEN;
		else return Color.MAGENTA;
	}
	
    public void findBestPath(Node start, Node end){
    	edgecolor.clear();
    	nodecolor.clear();
        ArrayList<Node> ns=new ArrayList<>(nodes);
        ArrayList<Edge> es=new ArrayList<>(edges);

//    	DikstraImpl d=new DikstraImpl(ns,es);
//    	d.myalgo(start, end);
//		ArrayList<Edge> pathEdges = d.path;

        PathFinder pf = PathFinder.getInstance(ns, es, start, end);
        ArrayList<Edge> pathEdges = pf.getShortestPathEdges();
        if(pathEdges.size()==0){
            System.out.println("There are 0 edges");
        }
        for(Edge e:pathEdges){
            edgecolor.add((MyEdge) e);
            nodecolor.add(((MyEdge)e).getP1());
            nodecolor.add(((MyEdge)e).getP2());
        }
    }

	public void findAllPaths(Node start, Node end){
		edgecolor.clear();
		nodecolor.clear();
		ArrayList<Node> ns=new ArrayList<>(nodes);
		ArrayList<Edge> es=new ArrayList<>(edges);

		PathFinder pf = PathFinder.getInstance(ns, es, start, end);

		ArrayList<Edge> pathEdges = pf.getAllPathEdges();
		String message = pf.getMessage();
		System.out.println(message);
		if(pathEdges.size()==0){
			System.out.println("There are 0 edges");
		}
		for(Edge e:pathEdges){
			edgecolor.add((MyEdge) e);
			nodecolor.add(((MyEdge)e).getP1());
			nodecolor.add(((MyEdge)e).getP2());
		}
	}
}