import java.util.*;
import java.io.*;

public class test{
	public static void main(String[] args){
		try {
			SuperScanner in = new SuperScanner(new InputStreamReader(System.in));
			int n = in.nextInt();
			int[][] rels1 = new int[n][n];
			int[][] rels2 = new int[n][n];
			boolean[] isRel1 = new boolean[5];
			boolean[] isRel2 = new boolean[5];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					rels1[i][j] = in.nextInt();


					if (i == j) { //cheking ref and aref
						if (rels1[i][j] == 0) {
							isRel1[0] = false;
						} else {
							isRel1[1] = false;
						}
					}


				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					rels2[i][j] = in.nextInt();

					if (i == j) { //cheking ref and aref
						if (rels1[i][j] == 0) {
							isRel2[0] = false;
						} else {
							isRel2[1] = false;
						}
					}
				}
			}
			in.close();

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {



				}
			}



		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

}