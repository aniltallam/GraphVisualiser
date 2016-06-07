
public class MyAction {
	static MyAction nodecreate=new MyAction("CREATE NODE");
	static MyAction edgecreate=new MyAction("CREATE EDGE");
	static MyAction nodeedit=new MyAction("EDIT NODE");
	static MyAction nodedelete=new MyAction("DELETE NODE");
	static MyAction edgedelete=new MyAction("DELETE EDGE");
	static MyAction findpath=new MyAction("Shortest path");
	static MyAction allpaths=new MyAction("All paths");
	String name;
	private MyAction(String name){
		this.name=name;
		
	}
}
