package utils;

import java.util.ArrayList;

/**
 * Node in a shortest path tree
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
class ShortestPathTreeNode
{
	public ShortestPathTreeNode(int to, double d) {
		nodeid=to;
		parent=null;
		label=d;
	}
	Integer nodeid;
	ArrayList<ShortestPathTreeNode> parent;
	double label;
}