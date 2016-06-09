import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
/**
 * Created by Anil Tallam
 */

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	DrawPanel dp;
    private ArrayList<MyNode> tmp_plist=new ArrayList<>();
    private ArrayList<MyEdge> tmp_elist=new ArrayList<>();
	private ArrayList<JButton> buttons1=new ArrayList<>();
	private ArrayList<JButton> buttons2=new ArrayList<>();
	public MyFrame() {
		this.dp=new DrawPanel();
		this.load();
		createButtons(buttons1,buttons2);
		addListeners(buttons1,buttons2);
		addToUI(buttons1,buttons2);

		this.setTitle("Graph Visualiser");
		this.add(dp,BorderLayout.CENTER);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	private void createButtons(ArrayList<JButton> buttons1, ArrayList<JButton> buttons2) {
		buttons1.add(new JButton("Add Node"));
		buttons1.add(new JButton("Add Edge"));
		buttons1.add(new JButton("Edit Node"));
		buttons1.get(2).setEnabled(false);
		buttons1.add(new JButton("Delete Node"));
		buttons1.add(new JButton("Delete Edge"));
		buttons1.add(new JButton("Get shortest path"));
		buttons1.add(new JButton("Find all paths"));

		buttons2.add(new JButton("Clear Map"));
		buttons2.add(new JButton("Undo Clear"));
		buttons2.add(new JButton("Save Map"));

	}
	private void addListeners(final ArrayList<JButton> buttons1, ArrayList<JButton> buttons2) {
		ActionListener al=new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
				for(JButton b:buttons1){
					b.setEnabled(true);
				}
				JButton b=(JButton)ae.getSource();


				if(ae.getActionCommand().equals("Add Node")){
					DrawPanel.currentaction=MyAction.nodecreate;
				}
				else if(ae.getActionCommand().equals("Add Edge")){
					DrawPanel.currentaction=MyAction.edgecreate;
				}
				else if(ae.getActionCommand().equals("Edit Node")){
					DrawPanel.currentaction=MyAction.nodeedit;
				}
				else if(ae.getActionCommand().equals("Delete Node")){
					DrawPanel.currentaction=MyAction.nodedelete;
				}
				else if(ae.getActionCommand().equals("Delete Edge")){
					DrawPanel.currentaction=MyAction.edgedelete;
				}
				else if(ae.getActionCommand().equals("Get shortest path")){
					DrawPanel.currentaction=MyAction.findpath;
				}
				else if(ae.getActionCommand().equals("Find all paths")){
					DrawPanel.currentaction=MyAction.allpaths;
				}

				else if(ae.getActionCommand().equals("Clear Map")){
					if(!dp.graph.nodes.isEmpty()){
						tmp_plist.clear();tmp_elist.clear();
						tmp_plist.addAll(dp.graph.nodes);tmp_elist.addAll(dp.graph.edges);
						dp.graph.nodes.clear();
						dp.graph.edges.clear();
					}
				}
				else if(ae.getActionCommand().equals("Undo Clear")){
					if(dp.graph.nodes.isEmpty() && dp.graph.edges.isEmpty()){
						dp.graph.nodes.addAll(tmp_plist);
						dp.graph.edges.addAll(tmp_elist);
						tmp_plist.clear();tmp_elist.clear();
					}
				}
				else if(ae.getActionCommand().equals("Save Map")){
					save();
				}
				if(buttons1.contains(b)){
					b.setEnabled(false);
				}
				dp.cleanMap();
			}

		};
		for(JButton b:buttons1){
			b.addActionListener(al);
		}
		for(JButton b:buttons2){
			b.addActionListener(al);
		}
	}
	private void addToUI(ArrayList<JButton> buttons1, ArrayList<JButton> buttons2) {
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		JPanel jp=new JPanel(new GridLayout(buttons1.size(),1));
		for(JButton b:buttons1){
			jp.add(b);
		}
		p.add(jp,BorderLayout.NORTH);

		jp=new JPanel(new GridLayout(buttons2.size(),1));
		for(JButton b:buttons2){
			jp.add(b);
		}
		p.add(jp,BorderLayout.SOUTH);

		this.add(p,BorderLayout.WEST);
	}
	private void save() {
		File dataf = new File("dataf.txt");
		try{
			BufferedWriter nbr = new BufferedWriter(new FileWriter(dataf));
			for(MyNode mp: dp.graph.nodes){
				String str = mp.getName()+","+mp.getX()+","+mp.getY();
				nbr.write(str);
				nbr.newLine();
			}
			nbr.write("Edges");nbr.newLine();
			for(MyEdge me:dp.graph.edges){
				String str = me.p1.getName()+","+me.p2.getName();
				nbr.write(str);
				nbr.newLine();
			}
			nbr.close();
		}catch(Exception e){
			System.out.println("Error Saving");
			DrawPanel.jl.setText("Error Saving");
		}
	}
	private void load(){
		File dataf = new File("dataf.txt");
		BufferedReader nbr=null;
		try{
			nbr = new BufferedReader(new FileReader(dataf));
			String line;
			Boolean nowedges=false;
			while((line=nbr.readLine()) != null){
				if(line.trim().equals("Edges")){
					nowedges=true;
					continue;
				}
				if(!line.equals("")){
					String[] n=line.split(",");
					if(!nowedges){ //create nodes
						if(n.length==3){
							MyNode mp=new MyNode(Double.parseDouble(n[1]),Double.parseDouble(n[2]),n[0]);
							dp.graph.nodes.add(mp);
						}
					}else{ //create edges
						if(n.length==2){
							MyNode p1=null,p2=null;
							for(MyNode mp:dp.graph.nodes){
								if(mp.getName().equals(n[0])){
									p1=mp;
									if(p1!=null && p2!=null){
										break;
									}
								}
								if(mp.getName().equals(n[1])){
									p2=mp;
								}
							}
							if(p1!=null && p2!=null){
								MyEdge me=new MyEdge(p1,p2);
								dp.graph.edges.add(me);
							}
						}
					}
				}
			}
			nbr.close();
			dp.repaint();
		}catch(Exception e){
			System.out.println("Error Loading");
			DrawPanel.jl.setText("Error Loading");
		}
	}
}
