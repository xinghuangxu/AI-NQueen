package xinghuangxu.csp;

import java.util.Random;
import java.util.Scanner;

/*
 * Xinghuang Xu
 * xinghuangxu@gmail.com
 */
public class NQueen implements CSP {

	private int size;
	private int[] locs;
	private int counter = 0;
	private Random randomGenerator;
	private int moveCount=0;

	public NQueen(int size) {
		this.size = size;
		this.locs = new int[size];
		this.randomGenerator = new Random();
		for(int i=0;i<size;i++){
			locs[i]=randomGenerator.nextInt(size);
		}
	}

	public NQueen(int n, int[] init) {
		this.size=n;
		this.locs=init;
	}

	public static void main(String[] arg) {
		int n=initializeNQuee();
		int maxRestart=100000;
		int totalCount=0;
		for(int i=0;i<maxRestart;i++){
			NQueen nqueen = new NQueen(n);
			boolean result=CSPSolver.minConflicts(nqueen, n*100);
//			System.out.println("Move Count:"+nqueen.getMoveCount());
			totalCount+=nqueen.getMoveCount();
			if(result){
//				System.out.println("Restart Time:"+i);
				System.out.println("Goal State:");
				System.out.println(nqueen.toString());
				return;
			}
		}
		System.out.println("Total Count:"+totalCount);
	}
	
	private static int initializeNQuee() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please Enter the size of the n queen problem:");
		int n = in.nextInt();
		int[] init=new int[n];
		int nextNum = -1;
		for(int i=0;i<n;i++){
			boolean next = false;
			while (!next) {
				System.out.println("Please Enter the location(1-"+n+") for the "+(i+1)+"th column:");
				nextNum = in.nextInt();
				if(nextNum<1||nextNum>n){
					System.out.println("Number " + nextNum
							+ " out of range.(1-" + n + ")");
				}else{
					init[i]=nextNum-1;
					next=true;
				}
			}
		}
		NQueen nqueen=new NQueen(n,init);
		System.out.println("Initial State:");
		System.out.println(nqueen.toString());
		return n;
	}

	public int getMoveCount(){
		return this.moveCount;
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<size;i++){
			sb.append((locs[i]+1)+" ");
		}
		sb.append("\n");
		for(int i=0;i<this.size;i++){
			for(int j=0;j<this.size;j++){
				if(locs[j]==i){
					sb.append("X ");
				}else{
					sb.append("O ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean isGoal() {
		for (int i = 0; i < this.size; i++) {
			if (this.isConflict(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getNextConflictedVariable() {
		int iterationCount = this.counter;
		while (true) {
			counter = (counter + 1) % (this.size);
			if (iterationCount == this.counter) {
				return -1;
			}
			// check if location count has conflict
			if (this.isConflict(counter)) {
				return counter;
			}
		}
	}

	@Override
	public boolean isConflict(int location) {
		// check row conflict
		for (int i = 0; i < this.size; i++) {
			if (location != i && locs[location] == locs[i])
				return true;
		}
		// check diagonal conflict
		for (int i = 0; i < this.size; i++) {
			if (location != i) {
				if (Math.abs(location - i) == Math
						.abs(locs[location] - locs[i])) {
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public void makeMinConflictMove(int location) {
		int minConflictMove = location;
		int minConflict = Integer.MAX_VALUE;
		for (int i = 0; i < this.size; i++) { //choose the move value
			if (locs[location] != i ) {
				int conflict = this.getConflictCount(location, i);
				if (conflict < minConflict) {
					minConflictMove = i;
					minConflict = conflict;
				} else if (conflict == minConflict) {
					if (randomGenerator.nextInt(2) == 0) {
						minConflictMove = i;
						minConflict = conflict;
					}
				}
			}
		}
		locs[location]=minConflictMove;
	}

	private int getConflictCount(int location, int value) {
		int conflictCount = 0;
		// check row conflict
		for (int i = 0; i < this.size; i++) {
			if (location != i && value == locs[i])
				conflictCount++;
		}
		// check diagonal conflict
		for (int i = 0; i < this.size; i++) {
			if (location != i) {
				if (Math.abs(location - i) == Math.abs(value - locs[i])) {
					conflictCount++;
				}
			}

		}
		return conflictCount;
	}

	@Override
	public void incrementMoveCount() {
		this.moveCount++;
	}
}
