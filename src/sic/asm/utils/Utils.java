package sic.asm.utils;

public class Utils {
	
	
	
	public static String toHex(int b) {
		return toHex(b,2);
		
//		int b1 = b & 0xF0;
//		int b2 = b & 0x0F;
//		StringBuilder sb = new StringBuilder();
//		sb.append(Integer.toHexString(b1));
//		sb.append(Integer.toHexString(b2));
//		return sb.toString();
	}
	
	public static void pr(String s) {
		System.out.print(s);
	}
	
	public static String toHex(int val, int n) {
		
		StringBuilder sb = new StringBuilder();

		
		while (n>0 || val>0) {
			int b = val&0xF;
			sb.append(Integer.toHexString(b));
			val>>=4;
			n--;
		}
		
		sb.reverse();
		

		return sb.toString().toUpperCase();
	}
	
	public static String toBin(int b) {
		return toBin(b,8);
	}
	
	public static String toBin(int val, int n) {
		StringBuilder sb = new StringBuilder();

		while (n>0 || val>0) {
			sb.append(1&val);
			val>>=1;
			n--;
			
		}
		
		sb.reverse();
		
		return sb.toString();
	}
	
	public static String stringFixedLength(String s, int len) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i<len) {
			if (s!=null && i<s.length()) {
				sb.append(s.charAt(i));
			} else {
				sb.append(' ');
			}
			i++;
		}
		
		return sb.toString();
	}

}
