package utils;

/**
 * This class will represent an edge in the graph
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
class Edge {
    public final int src, dst;
    public double weight;
    
    /**
     * Constructor
     * @param src
     * @param dst
     * @param weight
     */
    public Edge(int src, int dst, double weight) {
      this.src = src;
      this.dst = dst;
      this.weight = weight;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + src + "->" + dst + ";" + weight + "]";
    }

    /**
     * Two edges are equal if they have source node, end node and weight.
     */
    @Override
    public boolean equals(Object obj) {
    	Edge oEdge = (Edge) obj;
        return oEdge.weight == weight && oEdge.src == src && oEdge.dst == dst;
    }
}
