/**
 * 
 */
package QueryExecution;

/**
 * Stores the path in query and its instantiation in the graph
 * @author Manish Gupta (gupta58@illinois.edu)
 * University of Illinois at Urbana Champaign
 */
public class PathAndInstance {
	/**
	 * @param pu
	 * @param ipu
	 */
	public PathAndInstance(Path pu, Path ipu) {
		p=pu;
		instance=ipu;
	}
	Path p;
	Path instance;
}

