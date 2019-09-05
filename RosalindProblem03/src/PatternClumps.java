import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class PatternClumps {

	public static void main(String[] args) {
		
		/*
		 * Make a window
		 * 
		 * iterate through window, simultaneously adding distinct words to map; increment when needed
		 * 
		 * if value of word >= t, print
		 * 
		 * wipe map
		 * 
		 * slide window
		 * 
		 * repeat
		 */
		
		Scanner stdIn = new Scanner(System.in);
		
		String input = stdIn.next();
		char inputArray[] = input.toCharArray();
		
		int k = stdIn.nextInt(); // length of k-mer
		int L = stdIn.nextInt(); // sliding window size
		int t = stdIn.nextInt(); // times seen
		
		HashMap<String, Integer> kMerMap = new HashMap<String, Integer>();
		ArrayList<String> answerArray = new ArrayList<String>();
		
		for (int i = 0; i < inputArray.length - (L - 1); i++) {
			
			// Make a new window
			char inputWindowArray[] = Arrays.copyOfRange(inputArray, i, i + (L - 1));
			
			// Iterate through new window
			for (int j = 0; j < inputWindowArray.length - (k - 1); j++) {
				
				String kMer = new String(inputWindowArray, j, k);
				
				if (kMerMap.get(kMer) == null) {
					kMerMap.put(kMer, 1);
					
				} else {
					
					kMerMap.put(kMer, kMerMap.get(kMer) + 1);
					
					if (kMerMap.get(kMer) >= t) {
						if (!answerArray.contains(kMer)) {
							answerArray.add(kMer);
						}
					}
				}
			}
			
			kMerMap.clear();
		}
		
		StringBuilder output = new StringBuilder();
		
		for (String answer : answerArray) {
			output.append(answer + " ");
		}
		
		output.deleteCharAt(output.length() - 1);
		System.out.println(output);	
		
		stdIn.close();
	}
}
