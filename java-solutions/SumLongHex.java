public class SumLongHex {	
    public static void main(String[] args) {
		Long ans = 0L;
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args[i].length(); j++) {
	        char sym = args[i].charAt(j);
                if (!Character.isWhitespace(sym)) {
					int start = j;
					j++;
					while (j < args[i].length() && !Character.isWhitespace(args[i].charAt(j))) {
						j++;
					}
					String num_now = args[i].substring(start,j);
					ans += strToNum(num_now);
				}
			}
        }
        System.out.println(ans);
    }   
	static long strToNum(String str_num) {
			long rnum;
			if (str_num.length() >= 2) {
				if ( str_num.startsWith("0x") || str_num.startsWith("0X")) {
				str_num = str_num.substring(2,str_num.length());
				rnum = Long.parseUnsignedLong(str_num.toString(), 16);
				return rnum;
				}
			}
			rnum = Long.parseLong(str_num.toString());
			return rnum;
	}
}
