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
			//printSolution();
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
	
	public boolean placeNQueensMRV(int [][] X, int[] Y, int[] mrv, int[] flag, int N, int i){		
		int temp1 = -1;
		int temp2 = -1;
		
		
		for(int j = 0; j<N; j++){
			int count = 0;
			//System.out.println("row "+i);
			if(X[i][j] == 0){
				Y[i] = j;
				//System.out.println("col "+j);
				//System.out.println("queen done");
				flag[i] = 1;//occupied
				
				for(int a= 0; a<N; a++){					
					if(flag[a] == 1)	count++;			
				}
				
				
				System.out.println("count "+count);
				if(count == N){
					numberOfSolution++;
					System.out.println(Arrays.toString(Y));
					System.out.println("");
					System.out.println("");
					System.out.println("");
					flag[N-1] = 0; 
					return true;
				}
				setMRV(X,mrv,flag,i,j,N);
				System.out.println(Arrays.toString(mrv));
				System.out.println(Arrays.toString(flag));
				System.out.println(Arrays.toString(Y));
				
				for(int a = 0; a<N; a++){
					if(flag[a] == 0){
						if(mrv[a] > temp1){
							temp1 = mrv[a];
							temp2 = a;
						}
					}
				}
				placeNQueensMRV(X,Y,mrv,flag,N,temp2);
				//unoccupied
				reverseMRV(X,mrv,flag,i,j,N);
				flag[i] = 0;
				//System.out.println("flag "+i+"is"+flag[i]);
			}	
		}		
		return false;		
	}
	public void setMRV(int[][] X, int[] mrv, int[] flag, int i, int j, int n){				
		for(int a = 0; a<n; a++){
			mrv[a] = 0;
		}
		for(int a = 0; a<n; a++){
			if(flag[a]==0){
				X[a][j]++;
				//System.out.println("pruning down");
				if(j-(a-i) >= 0){
					X[a][j-(a-i)]++;
					//System.out.println("pruning left");
				}			
				if(j+(a-i) < n){				
					X[a][j+(a-i)]++;
					//System.out.println("pruning right");
				}	
			}
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
	
	public void reverseMRV(int[][] X, int[] mrv, int[] flag, int i, int j, int n){				
		for(int a = 0; a<n; a++){
			if(flag[a]==0){
				X[a][j]--;
				//System.out.println("back pruning down");
				if(j-(a-i) >= 0){
					X[a][j-(a-i)]--;
					//System.out.println("back pruning left");
				}			
				if(j+(a-i) < n){				
					X[a][j+(a-i)]--;
					//System.out.println("back pruning right");
				}	
			}
		}
		
		for(int a = 0; a<n; a++){
			if(flag[a]==0){
				for(int b = 0; b<n; b++){
					if(X[a][b] != 0){
						mrv[a]--;
					}
				}
			}
		}	
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
