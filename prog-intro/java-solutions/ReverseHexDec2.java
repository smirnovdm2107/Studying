import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
public class ReverseHexDec2 {

    public static void main(String[] args) {
        try {
            SuperScanner in = new SuperScanner(new InputStreamReader(System.in));
            int[][] nums = new int[10][10];
            int i = 0;
            int j = 0;

            while (in.hasNextLine()) {
                SuperScanner strScanner = new SuperScanner(in.nextLine());
                while (strScanner.hasNext()) {
                    if (j == nums[i].length) {
                        nums[i] = Arrays.copyOf(nums[i], nums[i].length * 2);
                    }
                    String numNow = strScanner.next();
                    if (numNow.startsWith("0x") || numNow.startsWith("0X")) {
                        nums[i][j] = Integer.parseUnsignedInt(numNow.substring(2, numNow.length()), 16);
                    } else {
                        nums[i][j] = Integer.parseInt(numNow);
                    }
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
                    System.out.print("0x" + Integer.toHexString(nums[i][t]) + " ");
                }
                System.out.println();
                i--;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Can't close scanner:" + e.getMessage());
        }
    }
}