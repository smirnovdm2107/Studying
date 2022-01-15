import java.util.Scanner;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
public class Reverse{
	public static void main(String[] args) {
		try {
			SuperScanner scan = new SuperScanner(new InputStreamReader(System.in));
			int[][] nums = new int[10][10];
			int i = 0;
			int j = 0;

			while (scan.hasNextLine()) {
				SuperScanner strScanner = new SuperScanner(scan.nextLine());
				while (strScanner.hasNext()) {
					if (j == nums[i].length) {
						nums[i] = Arrays.copyOf(nums[i], nums[i].length * 2);
					}
					nums[i][j] = strScanner.nextInt();
					j++;
				}
				strScanner.close();
				nums[i] = Arrays.copyOfRange(nums[i], 0, j);
				i++;
				if (i == nums.length) {
					nums = Arrays.copyOf(nums, nums.length * 2);
					for (int k = i; k < nums.length; k++) {
						nums[k] = new int[10];
					}
				}
				j = 0;
			}

			i--;
			while (i >= 0) {
				for (int t = nums[i].length - 1; t >= 0; t--) {
					System.out.print(nums[i][t] + " ");
				}
				System.out.println();
				i--;
			}
			scan.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}
}	