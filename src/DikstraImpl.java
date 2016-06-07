import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pushparaj Motimarri
 */
class DikstraImpl{
	HashMap<Node,DksRow> nos =new HashMap<Node,DksRow>();
	int visitedcount=0;
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	public ArrayList<Edge> path=new ArrayList<Edge>();
	public DikstraImpl(ArrayList<Node> nodes, ArrayList<Edge> edges){
		this.nodes=nodes;
		this.edges=edges;
		for(Node n: nodes){
			nos.put(n, new DksRow());
		}
	}

	public boolean isVisited(Node n){
		return nos.get(n).visited;
	}
	public void setVisited(Node n){
		visitedcount++;
		nos.get(n).visited=true;
	}
	
	public int getShortDist(Node n){
		return nos.get(n).shortdist;
	}
	public void setShortDist(Node n, int d){
		nos.get(n).shortdist=d;
	}
	public Node getPrevNode(Node n){
		return nos.get(n).prev;
	}
	public void setPrevNode(Node n, Node prev){
//		for(Edge e:edges){
//			if(e.getFromNode()==prev && e.getToNode()==n){
//				this.path.add(e);
//			}
//		}
		nos.get(n).prev=prev;
	}
	public int getDist(Node n1, Node n2){
		for(Edge e:edges){
			if(e.getFromNode()==n1 && e.getToNode()==n2){
				return (int) e.getDist();
			}
		}
		return (int) MyConstants.inf;
	}
	public ArrayList<Node> getAdjNodes(Node n){
		ArrayList<Node> an= new ArrayList<Node>();
		for(Edge e:edges){
			if(e.getFromNode()==n){
				an.add(e.getToNode());
			}
		}
		return an;
	}
	
	Boolean anyUnvisted(){
		return (nodes.size()==visitedcount)?false:true;
	}
	
	ArrayList<Edge> myalgo(Node start, Node end){	
		nos.get(start).shortdist=0;
		//nos.get(start).visited=true;
		Node n=null;
		while(anyUnvisted()){//continue if there are any unvisited nodes 
			//get node with least shortDist value among all unvisited nodes
			n=null;
			for(Node i: nodes){	
				if(!isVisited(i)){
					System.out.println(i.getName()+" is visited :"+nos.get(i).visited);
					if(n==null){
						n=i;
					}
					else if( (getShortDist(i)) < (getShortDist(n)) ){
						n=i;
					}
				}
			}
			//continue if shortDist value of that node is not infinite number
			if(getShortDist(n)!=MyConstants.inf){
				//if that node is end then stop algo
				if(n==end)
					// stop algO and return from function
					break;
				//else continue with algo
				else
                 modifiedDijkAlgo(n);
			}
		}
		
		//make a list for path
		//ArrayList<Node> path=new ArrayList<Node>();
		Node i=end;
		while(i!=null){
			if(getPrevNode(i)!=null)
				path.add(getEdge(getPrevNode(i),i));
			i=getPrevNode(i);
		}
		return path;
	}
	
	private Edge getEdge(Node n1, Node n2) {
		for(Edge e:edges){
			if(e.getFromNode()==n1 && e.getToNode()==n2)
				return e;
		}
		return null;
	}

	private void modifiedDijkAlgo(Node n){
		int cndist= getShortDist(n);
		for(Node i : getAdjNodes(n)){ 
			if(!isVisited(i)){
				int temp = cndist+getDist(n,i);
				if(temp<getShortDist(i)){ 
					setShortDist(i,temp); 
					setPrevNode(i,n);}
			}
		}
		setVisited(n);
	}
	@SuppressWarnings("unused")
	private void DijkAlgo(Node n){
		int cndist= getShortDist(n);
		for(Node i : getAdjNodes(n)){ 
			if(!isVisited(i)){
				int temp = cndist+getDist(n,i);
				if(temp<getShortDist(i)){ 
					setShortDist(i,temp); 
					setPrevNode(i,n);}
			}
		}
		setVisited(n);
		n=null;
		while(anyUnvisted()){//continue if there are any unvisited nodes 
			//get node with least shortDist value among all unvisited nodes
			for(Node i: nodes){	
				if(!isVisited(i)){
					if(n==null){
						n=i;
					}
					else if( (getShortDist(i)) < (getShortDist(n)) ){
						n=i;
					}
				}
			}
			//continue if shortDist value of that node is not infinite number
			if(getShortDist(n)!=MyConstants.inf){
				DijkAlgo(n);
			}
		}
	}
}

class DksRow{
  int shortdist; 
  boolean visited;
  Node prev;
  public DksRow() {
	  this.shortdist=(int) MyConstants.inf;
	  this.visited=false;
	  this.prev=null;
  }
}