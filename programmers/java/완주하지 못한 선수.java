import java.util.*;
class Solution {
	public String solution(String[] participant, String[] completion) {
		String answer = "";
		Arrays.sort(participant);
		Arrays.sort(completion);
		for(int i=0; i<participant.length; i++){
			if(i == completion.length) {
				return participant[i];
			}else if(!participant[i].equals(completion[i])){
				return participant[i];
			}
		}
		return answer;
	}
}
