package xinghuangxu.csp;

/*
 * Xinghuang Xu
 * xinghuangxu@gmail.com
 */
public interface CSP {
	
	boolean isGoal();

	int getNextConflictedVariable();
	
	boolean isConflict(int location);

	void makeMinConflictMove(int loc);

	void incrementMoveCount();

}
