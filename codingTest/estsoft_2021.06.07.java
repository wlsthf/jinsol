public class CodingTest {
	public static void main(String[] args) {
		String [][] str = {{"101","10100010101101100"},{"110","110110110"},{"000","00000000"},{"00","1111111"}};
		for(int i=0; i<str.length; i++) {
			System.out.println("testCase" + (i+1) + " : "  + solution(str[i][0], str[i][1]));
		}
	}
	
	public static int solution(String p, String s) {
		int answer = 0;
		String[] pSplit = p.split("");
		String[] sSplit = s.split("");
		final int pLength = p.length();
		final int sLength = sSplit.length;
		int count =0;
		int k = 0;

		for(int i=0; i<sSplit.length; i++){
			if(pSplit[k].equals(sSplit[i]))	k++;

			if(k==pLength) {
				k=0;
				count++;
			}
		}

		answer = sLength-(pLength*count);
		if(count==0) answer = -1;
		return answer;
	}
	
}
