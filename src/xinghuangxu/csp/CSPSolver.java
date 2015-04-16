package xinghuangxu.csp;

/*
 * Xinghuang Xu
 * xinghuangxu@gmail.com
 */
public class CSPSolver {
	
	public static Boolean minConflicts(CSP csp, int maxSteps){
		CSP current=csp;
		int loc=-1;
		for(int i=0;i<maxSteps;i++){
			loc= current.getNextConflictedVariable();
			if(loc==-1)return true; //no conflict found
			current.makeMinConflictMove(loc); //move to the min conflict location
			current.incrementMoveCount();
		}
		return false;
	}
}
