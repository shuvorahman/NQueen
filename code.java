package nQueen;

import java.util.Arrays;
import java.util.Scanner;

public class Board {
	int numberOfSolution;
	
	Board(){
		numberOfSolution = 0;
	}
	
	public boolean placeNQueens(int[] X, int N, int i){
		boolean flag = true;
		
		if (i == N){
			//printSolution();
			numberOfSolution++;
			System.out.println(Arrays.toString(X));
			return true;
		}
		
		for(int j = 0; j<N; j++){
			//System.out.println("row "+i);
			X[i] = j;
			//System.out.println("col "+j);
			flag = checkConstraints(X,N,i);
			if (!flag)	continue;
			//System.out.println("queen done");
			placeNQueens(X,N,i+1);
			/*if (successor){
				return true;
			}*/			
		}
		
		return false;		
	}
	
	public boolean checkConstraints(int[] A, int n, int r){
		for(int i = 0; i<r; i++){
			if(A[i] == A[r] || A[r] - (r-i) == A[i] || A[r] + (r-i) == A[i]){
				//System.out.println("flag Raised");
				return false;
			}
		}
		return true;
	}
	

	
	public boolean placeNQueensFC(int [][] Y, int[] X, int N, int i){		
		if (i == N){
			numberOfSolution++;
			System.out.println(Arrays.toString(X));
			return true;
		}

		for(int j = 0; j<N; j++){
			System.out.println("row "+i);
			if(Y[i][j] == 0){
				X[i] = j;
				System.out.println("col "+j);
				System.out.println("queen done");
				forwardCheck(Y,i,j,N);
				placeNQueensFC(Y,X,N,i+1);
				reverseForward(Y,i,j,N);
			}	
		}		
		return false;		
	}
		
	public void forwardCheck(int[][] X, int i, int j, int n){
		for(int a = i+1; a<n; a++){
			X[a][j]++;
			System.out.println("pruning down");
			if(j-(a-i) >= 0){
				X[a][j-(a-i)]++;
				System.out.println("pruning left");
			}
			
			if(j+(a-i) < n){				
				X[a][j+(a-i)]++;
				System.out.println("pruning right");
			}
		}
	}
	
	public void reverseForward(int[][] X, int i, int j, int n){
		for(int a = i+1; a<n; a++){
			X[a][j]--;
			System.out.println("pruning down");
			if(j-(a-i) >= 0){
				X[a][j-(a-i)]--;
				System.out.println("pruning left");
			}
			
			if(j+(a-i) < n){				
				X[a][j+(a-i)]--;
				System.out.println("pruning right");
			}
		}
	}
	
	public boolean placeNQueensMRV(int [][] X, int[] Y, int[] mrv, int[] flag, int n, int i){	
		
		for(int j=0; j<n; j++){
			System.out.println("row "+i);
			if (X[i][j] == 0){
				Y[i]= j;
				System.out.println("col "+j);
				System.out.println("queen done");
		
				setMRV(X,mrv,flag,i,j,n,Y);
				if(check(Y,flag,n)){
					resetMRV(X,mrv,flag,i,j,n,Y);
					return true;
				}
				placeNQueensMRV(X,Y,mrv,flag,n,largestArrayNumberIndex(mrv,flag,n));							
				resetMRV(X,mrv,flag,i,j,n,Y);
			}
		}
		return false;	
	}
	
	public boolean check(int[] Y, int[] flag, int n){
		int count = 0;
		for(int k=0; k<n; k++){
			if(flag[k] == 1){
				count++;
			}					
		}
		
		if (count == n){
			//printSolution();
			numberOfSolution++;
			System.out.println(Arrays.toString(Y));
			System.out.println("");
			System.out.println("");
			return true;
		}
		return false;
	}
	
	public void setMRV(int[][] X, int[] mrv, int[] flag, int i, int j, int n,int[] Y){	
		flag[i] = 1;
		System.out.println("pruning");
		for(int a = i+1; a<n; a++){
			X[a][j]++;
			//System.out.println("pruning down");
			if(j-(a-i) >= 0){
				X[a][j-(a-i)]++;
				//System.out.println("pruning lower left");
			}			
			if(j+(a-i) < n){				
				X[a][j+(a-i)]++;
				//System.out.println("pruning lower right");
			}
		}
		
		for(int a = i-1; a>=0; a--){
			X[a][j]++;
			//System.out.println("pruning up");
			if(j-(i-a) >= 0){
				X[a][j-(i-a)]++;
				//System.out.println("pruning upper left");
			}			
			if(j+(i-a) < n){				
				X[a][j+(i-a)]++;
				//System.out.println("pruning upper right");
			}
		}
		
		countMRV(X,mrv,flag,n);
		System.out.println(Arrays.toString(flag));
		System.out.println(Arrays.toString(mrv));
		System.out.println(Arrays.toString(Y));
		
	}
	
	public void countMRV(int[][] X, int[] mrv,int[] flag, int n){
		for(int i = 0; i<n; i++){
			mrv[i] = 0;
		}
		
		for(int a = 0; a<n; a++){
			if(flag[a]==0){
				for(int b = 0; b<n; b++){
					if(X[a][b] != 0){
						mrv[a]++;
					}
				}
			}
		}
	}
	
	public void resetMRV(int[][] X, int[] mrv, int[] flag, int i, int j, int n, int[] Y){				
		flag[i] = 0;
		System.out.println("rev pruning");
		for(int a = i+1; a<n; a++){
			X[a][j]--;
			//System.out.println("rev pruning down");
			if(j-(a-i) >= 0){
				X[a][j-(a-i)]--;
				//System.out.println("rev pruning lower left");
			}			
			if(j+(a-i) < n){				
				X[a][j+(a-i)]--;
				//System.out.println("rev pruning lower right");
			}
		}
		
		for(int a = i-1; a>=0; a--){
			X[a][j]--;
			//System.out.println("rev pruning up");
			if(j-(i-a) >= 0){
				X[a][j-(i-a)]--;
				//System.out.println("rev pruning upper left");
			}			
			if(j+(i-a) < n){				
				X[a][j+(i-a)]--;
				//System.out.println("rev pruning upper right");
			}
		}
		countMRV(X,mrv,flag,n);
		System.out.println(Arrays.toString(flag));
		System.out.println(Arrays.toString(mrv));
		System.out.println(Arrays.toString(Y));
	}
	
	public int largestArrayNumberIndex(int[] A, int[] flag, int n){
		int temp = -1;
		int index = -1;
		for (int i = 0; i<n; i++){
			if(flag[i] ==0 && A[i] > temp){
				temp = A[i];
				index = i;
			}
		}
		System.out.println("row "+index+" has highest mrv val");
		return index;
	}
	
	public static void main(String[] args){
		int a;
		int[] queenBoard;
		int[] countMRV;
		int[] flag;
		int[][] board;
		System.out.println("N = ");
		
		Scanner reader = new Scanner(System.in);
		a = reader.nextInt();

		board = new int[a][a];
		queenBoard = new int[a];
		countMRV = new int[a];
		flag = new int[a];
		
		for(int i = 0; i<a; i++){
			queenBoard[i] = 0;
			countMRV[i] = 0;
			flag[i] = 0;
			for(int j = 0; j<a; j++){
				board[i][j] = 0;
			}
		}	
		
		Board b = new Board();
		//b.placeNQueens(queenBoard, a, 0);
		//b.placeNQueensFC(board,queenBoard,a,0);
		b.placeNQueensMRV(board, queenBoard, countMRV, flag, a, 0);
		System.out.println("number of Solution " + b.numberOfSolution);
	}
}
