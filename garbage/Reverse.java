import java.util.Scanner;
public class Reverse{
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String strs[];
		strs = new String[1_000_000];
		int i = 0;
		while (scan.hasNextLine()) {
			String str_now = scan.nextLine();
			strs[i] = str_now;
			i++;
		}
		scan.close();
		i--;
		while (i >= 0) {
			for (int j = strs[i].length() - 1; j >= 0; j--) {
				char sym_now = strs[i].charAt(j);
				if (sym_now != ' ') {
					int end = j+1;
					j--;
					while (j >= 0 && strs[i].charAt(j) != ' ') {
						j--;
					}
					String num_str = strs[i].substring(j+1,end);
					System.out.print(num_str + " ");
				}
			}
			System.out.println();
			i--;
		}
	}
}	