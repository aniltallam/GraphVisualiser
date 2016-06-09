import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Anil Tallam on 6/7/2016.
 */
public class PathFinder {

    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;
    private Node start;
    private Node end;

    private HashSet<Node> visitedNodes = new HashSet<>();
    HashMap<Node, Integer> nodeDist = new HashMap<>();
    //private int uniquePathCount = 0;

    ArrayList<Edge> p_edges;
    ArrayList<Edge> bestPath;

    private static PathFinder thisObject;
    public static PathFinder getInstance(ArrayList<Node> nodes, ArrayList<Edge> edges, Node start, Node end){
        thisObject = null; //For Testing only
        if (thisObject == null) {
            thisObject = new PathFinder(nodes, edges, start, end);
        } else if(thisObject.nodes != nodes || thisObject.edges != edges || thisObject.start != start || thisObject.end != end){
            thisObject = new PathFinder(nodes, edges, start, end);
        }
        return thisObject;
    }
    private PathFinder(ArrayList<Node> nodes, ArrayList<Edge> edges, Node start, Node end){
        this.nodes=nodes;
        this.edges=edges;
        this.start = start;
        this.end = end;
        calculateDistance(start, end);
    }

    public ArrayList<Edge> getAllPathEdges() {
        if(p_edges == null) {
            p_edges = new ArrayList<>();
            addAllPathEdges(start);
        }
        return p_edges;
    }

    private void addAllPathEdges(Node curr) {
        if(curr == end)
            return;
        for (Node next: getNextNodes(curr)) {
            if (nodeDist.get(next) == Integer.MAX_VALUE) {
                continue;
            }
            List<Edge> ed = edges.stream().filter((e) -> e.getFromNode() == curr && e.getToNode() ==next).collect(Collectors.toList());
            p_edges.addAll(ed);
            addAllPathEdges(next);
        }
    }

    public ArrayList<Edge> getShortestPathEdges() {
        if(bestPath == null) {
            bestPath = new ArrayList<>();
            addShortestPathEdges(start);
        }
        System.out.println("nodeDist = " + nodeDist);
        return bestPath;
    }

    private void addShortestPathEdges(Node curr) {
        if(curr == end)
            return;
        List<Node> next_nodes = getNextNodes(curr).stream().filter( next1 -> nodeDist.get(next1) < Integer.MAX_VALUE).collect(Collectors.toList());
        if (next_nodes.isEmpty())
            return;
        Collections.sort(next_nodes, (lhs, rhs) -> Integer.compare((nodeDist.get(lhs) + dist(curr, lhs)), nodeDist.get(rhs) + dist(curr, rhs)) );

        Node next = next_nodes.get(0); // gp through shortest near element
        List<Edge> ed = edges.stream().filter((e) -> e.getFromNode() == curr && e.getToNode() ==next).collect(Collectors.toList());
        bestPath.addAll(ed);
        addShortestPathEdges(next);
    }

//    private void addPathEdges(Node curr) {
//        for (Node next: getNextNodes(curr)) {
//            if (!pathNodes.contains(next)) {
//                continue;
//            }
//            List<Edge> ed = edges.stream().filter((e) -> e.getFromNode() == curr && e.getToNode() ==next).collect(Collectors.toList());
//            p_edges.addAll(ed);
//            addPathEdges(next);
//        }
//    }

//    private boolean findPaths(Node curr, Node end) {
//        visitedNodes.add(curr);
//        if(curr == end) {
//            pathNodes.add(curr);
//            uniquePathCount++;
//            return true;
//        }
//
//        boolean retVal = false;
//        for (Node next: getNextNodes(curr)) {
//            if (visitedNodes.contains(next)) {
//                if(pathNodes.contains(next)) {
//                    pathNodes.add(curr);
//                    uniquePathCount++;
//                    retVal = true;
//                }
//            } else if (findPaths(next, end)) {
//                pathNodes.add(curr);
//                retVal = true;
//            }
//        }
//        return retVal;
//    }

    private void calculateDistance(Node curr, Node end) {
        visitedNodes.add(curr);
        nodeDist.put(curr,Integer.MAX_VALUE);

        if(curr == end) {
            nodeDist.put(curr,0);
            return;
        }
        for (Node next: getNextNodes(curr)) {
            if(!visitedNodes.contains(next))
                calculateDistance(next, end);
            int val = nodeDist.get(next);
            if (val < Integer.MAX_VALUE) {
                int temp_dist = dist(curr,next) + val;
                if(temp_dist < nodeDist.get(curr))
                    nodeDist.put(curr, temp_dist);
            }
        }
    }

    public String getMessage() {
        return "Node Distances : "+nodeDist;
    }

    //Helper Methods
    private List<Node> getNextNodes(Node curr){
        ArrayList<Node> next_nodes = new ArrayList<>();
        List<Edge> ed = edges.stream().filter((e) -> e.getFromNode() == curr).collect(Collectors.toList());
        for (Edge edge: ed) {
            next_nodes.add(edge.getToNode());
        }
        return next_nodes;
    }

    private int dist(Node curr, Node next) {
        List<Edge> ed = edges.stream().filter((e) -> e.getFromNode() == curr && e.getToNode() == next).collect(Collectors.toList());
        return ed.get(0).getDist();
    }
}
