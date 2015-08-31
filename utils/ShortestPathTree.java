package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a shortest path tree created when running Dijkstra on a graph
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
class ShortestPathTree {
	ShortestPathTreeNode root;
	HashMap<Integer, ShortestPathTreeNode> leaves;
	HashSet<Edge> otherTightEdges;
	ShortestPathTree(int id)
	{
		root = new ShortestPathTreeNode(id, 0);
		leaves = new HashMap<Integer, ShortestPathTreeNode>();
		leaves.put(id, root);
	}
	public boolean addEdge(int from, int to)
	{
		if(!leaves.containsKey(from))
			return false;
		ShortestPathTreeNode n = leaves.get(from);
		ShortestPathTreeNode t = new ShortestPathTreeNode(to, n.label+1);
		t.parent = new ArrayList<ShortestPathTreeNode>();
		t.parent.add(n);
		leaves.put(to, t);
		return true;
	}
	public void display()
	{
		System.out.println("Number of leaves: "+leaves.size());
	}
	public void computeOtherTightEdges(int from, int to)
	{
		ShortestPathTreeNode n1 = leaves.get(from);
		ShortestPathTreeNode n2 = leaves.get(to);
		if(n1==null && n2==null)
			return;
		if(n1.label==n2.label+1 && !n1.parent.contains(n2))
		{
			n1.parent.add(n2);
		}
		if(n1.label+1==n2.label && !n2.parent.contains(n1))
		{
			n2.parent.add(n1);
		}
	}
	public ShortestPathTree randomSample()
	{
		ShortestPathTree t1 = new ShortestPathTree(root.nodeid);
		for (Integer id : this.leaves.keySet()) {
			ShortestPathTreeNode n = leaves.get(id);
			if(n.parent==null)
				continue;
			int choice = (int)(Math.random()*n.parent.size());
			ShortestPathTreeNode n1 = new ShortestPathTreeNode(n.nodeid, n.label);
			ArrayList<ShortestPathTreeNode> parents = new ArrayList<ShortestPathTreeNode>();
			parents.add(n.parent.get(choice));
			n1.parent=parents;
			t1.leaves.put(n1.nodeid, n1);
		}
		return t1;
	}
	public void computeImportance(HashMap<String, Double> imp)
	{
		for(Integer id : leaves.keySet())
		{
			ShortestPathTreeNode node1 = leaves.get(id);
			while(node1.parent!=null)
			{
				ShortestPathTreeNode node2 = node1.parent.get(0);
				int id1=node1.nodeid;
				int id2=node2.nodeid;
				String key="";
				if(id1>id2)
					key=id2+"#"+id1;
				else
					key=id1+"#"+id2;
				imp.put(key, imp.get(key)+1);
				node1=node2;
			}
		}
	}
	public static void main(String[] args) {
		ShortestPathTree t = new ShortestPathTree(10);
		t.addEdge(10,20);
	}
}