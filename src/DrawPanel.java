import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


class DrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	static MyAction currentaction=MyAction.nodeedit;
    static JLabel jl=new JLabel(DrawPanel.currentaction.name);
    
    java.awt.geom.Ellipse2D.Double el;
    Point2D curr_p,start_p,temp_mp,p1,p2;
    MyGraph graph;
    public DrawPanel() {
    	graph=new MyGraph();
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        add(jl);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(DrawPanel.currentaction==MyAction.nodecreate){
                	graph.addNode(e.getX(),e.getY(),"N"+(graph.nodes.size()+1));
                	repaint();
                }
                if(DrawPanel.currentaction==MyAction.edgecreate){
                	start_p=graph.getMyNodeAt(e.getPoint());
                }
                if(DrawPanel.currentaction==MyAction.nodeedit){
                	Point2D mp;
                	if((mp=graph.getMyNodeAt(e.getPoint()))!=null)
                		temp_mp=mp;
                }
                if(DrawPanel.currentaction==MyAction.nodedelete){
                	Point2D mp;
                	if((mp=graph.getMyNodeAt(e.getPoint()))!=null){
                		graph.removeAdjacentEdges((MyNode) mp);
                		graph.nodes.remove(mp);
                		repaint();
                	}
                }
                if(DrawPanel.currentaction==MyAction.edgedelete){
                	if(true){
                		MyEdge me1=null;
                		if(el==null)
                		 el=new java.awt.geom.Ellipse2D.Double(e.getX()-5,e.getY()-5,10,10);
                		else{ 
                			el.x=e.getX()-5;
                			el.y=e.getY()-5;
                		}
                		for(MyEdge me:graph.edges){
                			if(me.intersects(el.getBounds2D())){
                				me1=me;
                				break;
                			}
                		}
                		if(me1!=null) graph.edges.remove(me1);
                		
                		repaint();
                	}
                }
                if(DrawPanel.currentaction==MyAction.findpath){
                	Point2D mp;
                	if((mp=graph.getMyNodeAt(e.getPoint()))!=null){
                		if(p1==null){
                			p1=mp;
                			jl.setText(MyAction.findpath.name);
                			jl.setText(jl.getText()+"\t from "+((MyNode) p1).getName());
                			repaint();
                		}else if(p2==null){
                			p2=mp;
                			graph.findBestPath((Node)p1, (Node)p2);
                			jl.setText(jl.getText()+" to "+((MyNode) p2).getName());
                			repaint();
                		}
                		else{
                			cleanMap();
                			p1=mp;
                			jl.setText(DrawPanel.currentaction.name);
                			jl.setText(jl.getText()+"\t From "+((MyNode) p1).getName());
                			repaint();
                		}
                	}

                }
				if(DrawPanel.currentaction==MyAction.allpaths){
					///TODO
					Point2D mp;
					if((mp=graph.getMyNodeAt(e.getPoint()))!=null){
						if(p1 != null && p2 != null) {
							p1 = p2 = null;
							cleanMap();
						}
						if(p1==null){
							p1=mp;
							jl.setText(MyAction.allpaths.name);
							jl.setText(jl.getText()+"\t from "+((MyNode) p1).getName());
							repaint();
						}else if(p2==null){
							p2=mp;
							graph.findAllPaths((Node)p1, (Node)p2); ///TODO change this
							jl.setText(jl.getText()+" to "+((MyNode) p2).getName());
							repaint();
						}
					}

				}
            }
            public void mouseReleased(MouseEvent e){
                if(DrawPanel.currentaction==MyAction.edgecreate){

                	if((curr_p=graph.getMyNodeAt(e.getPoint()))!=null && curr_p!=start_p){
                		
                		graph.addEdge((MyNode)start_p,(MyNode)curr_p);
                	}
                	start_p=null;curr_p=null;
               		repaint();
                }
                if(DrawPanel.currentaction==MyAction.nodeedit){
                	temp_mp=null;
                }
            }

        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {   	
                if(DrawPanel.currentaction==MyAction.edgecreate){
                	if(start_p!=null){ 
                		if(curr_p==null) curr_p=e.getPoint();
                		else curr_p.setLocation(e.getPoint());
                		repaint();
                	}
                }
                if(DrawPanel.currentaction==MyAction.nodeedit){
                	if(temp_mp!=null){ 
                		temp_mp.setLocation(e.getX(), e.getY());
                		repaint();
                	}
                }
            }
            public void mouseMoved(MouseEvent e){
            	if(DrawPanel.currentaction==MyAction.edgedelete){
            		if(el==null)
               		 el=new java.awt.geom.Ellipse2D.Double(e.getX()-5,e.getY()-5,10,10);
               		else{ 
               			el.x=e.getX()-5;
               			el.y=e.getY()-5;
               		}
            		repaint();
            	}
            }
        });
        
    }
    public void cleanMap(){
    	el=null;
        curr_p=null;start_p=null;temp_mp=null;p1=null;p2=null;
        jl.setText(DrawPanel.currentaction.name);
        graph.edgecolor.clear();
        graph.nodecolor.clear();
        repaint();
    }
	public Dimension getPreferredSize() {
        return new Dimension(750,650);
    }
    public Color getBackground(){
		return Color.white;
    	
    }

    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
       
        Graphics2D g2=(Graphics2D) g;
        for(MyEdge me:graph.edges){
        	g2.setColor(graph.getEdgeColor(me));
        	if(me==null){
        		System.out.println("Null edge encountered");
        		continue;
        	}
            g2.draw(me);
            Point2D p1=me.getP1(),p2=me.getP2();
     	    double dist=me.getDist();
     	    
     	    //draw direction arrows
     	    g2.drawPolygon(getPolygon(p1.getX(),p1.getY(),(int )(p1.getX()+p2.getX())/2,(int )(p1.getY()+p2.getY())/2));
     	    g2.fillPolygon(getPolygon(p1.getX(),p1.getY(),(int )(p1.getX()+p2.getX())/2,(int )(p1.getY()+p2.getY())/2));
     	    g2.setColor(Color.BLACK);
     	    
     	    g2.drawString(""+(int)dist, (int )(p1.getX()+p2.getX())/2, (int )(p1.getY()+p2.getY())/2); 
        }
        
        for(Point2D mp:graph.nodes){
        	int x=(int) (mp.getX()-5);
        	int y=(int) (mp.getY()-5);
        	String s=graph.getNodeName((MyNode)mp);
        	java.awt.geom.Ellipse2D.Double el=new java.awt.geom.Ellipse2D.Double(x,y,10,10);
        	g2.draw(el);
        	g2.setColor(graph.getNodeColor((MyNode)mp));
            g2.fill(el);
            
            g2.setColor(Color.BLACK);
            g2.drawString(s, x, y); 
        }
       if(this.start_p!=null && this.curr_p!=null){
    	   Point2D p1=start_p,p2=curr_p;
    	   g2.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    	   double dist=p1.distance(p2);
    	   g2.drawPolygon(getPolygon(p1.getX(),p1.getY(),(int )(p1.getX()+p2.getX())/2,(int )(p1.getY()+p2.getY())/2));
    	   g2.fillPolygon(getPolygon(p1.getX(),p1.getY(),(int )(p1.getX()+p2.getX())/2,(int )(p1.getY()+p2.getY())/2));
    	   g2.drawString(""+(int)dist, (int )(p1.getX()+p2.getX())/2, (int )(p1.getY()+p2.getY())/2);
       }
       if(this.el!=null){
    	   g2.setColor(Color.GREEN);
    	   g2.draw(el);
    	   g2.setColor(Color.BLACK);
       }
       if(p1!=null){
    	   Double x=p1.getX()-5;
    	   Double y=p1.getY()-5;
    	   java.awt.geom.Ellipse2D.Double el=new java.awt.geom.Ellipse2D.Double(x,y,10,10);
    	   g2.setColor(Color.GREEN);
    	   g2.draw(el);
    	   g2.drawString("start", x.intValue(), y.intValue()+25);
    	   g2.setColor(Color.BLACK);
       }
       if(p2!=null){
    	   Double x=p2.getX()-5;
    	   Double y=p2.getY()-5;
    	   java.awt.geom.Ellipse2D.Double el=new java.awt.geom.Ellipse2D.Double(x,y,10,10);
    	   g2.setColor(Color.GREEN);
    	   g2.draw(el);
    	   g2.drawString("end", x.intValue(), y.intValue()+25);
    	   g2.setColor(Color.BLACK);
       }
    }
	private Polygon getPolygon(double i, double j, int x, int y) {
		double x1,y1,x2,y2;
		double l,h;
		double theta;
		if((x-i)>0)
			theta=Math.atan((y-j)/(x-i));
		else
			theta=Math.PI+Math.atan((y-j)/(x-i));
		l=5*Math.sqrt(2)*Math.cos((Math.PI/4) +theta);
		h=5*Math.sqrt(2)*Math.sin((Math.PI/4) +theta);
		x1=x-l;
		y1=y-h;
		l=10*Math.cos((Math.PI/2) -theta);
		h=10*Math.sin((Math.PI/2) -theta);
		x2=x1-l;
		y2=y1+h;
		Polygon p=new Polygon();
		p.addPoint(x, y);
		p.addPoint((int)x1,(int)y1);
		p.addPoint((int)x2,(int)y2);
		return p;
	}  
}