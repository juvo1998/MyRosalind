import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class MostProbableKmer {

	public static void main(String[] args) {

		Scanner stdIn = new Scanner(System.in);
		
		String input = stdIn.nextLine();
		char inputArray[] = input.toCharArray();
		int k = stdIn.nextInt();
		
		HashMap<String, Double> profile = new HashMap<String, Double>();
		
		/* KEY:
		 * 
		 * The first index in profile symbolizes the nucleotide.
		 * 0 = A
		 * 1 = C
		 * 2 = G
		 * 3 = T
		 * 
		 * The second index in profile symbolizes the index of the k-mer.
		 */
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < k; j++) {	
				
				if (i == 0) {
					profile.put("A, " + j, stdIn.nextDouble());
				} else if (i == 1) {
					profile.put("C, " + j, stdIn.nextDouble());
				} else if (i == 2) {
					profile.put("G, " + j, stdIn.nextDouble());
				} else {
					profile.put("T, " + j, stdIn.nextDouble());
				}
			}
		}
		
		stdIn.close();
		
		char mostProbable[] = new char[k];
		double maxProbability = 0.0;
		double currProbability = 0.0;
		
		for (int i = 0; i < inputArray.length - k + 1; i++) {
			
			for (int j = 0; j < k; j++) {
				
				if (j == 0) {
					currProbability = profile.get(inputArray[i] + ", 0");
				} else {
					currProbability = currProbability * profile.get(inputArray[i + j] + ", " + j);
				}
			}
			
			if (currProbability > maxProbability) {
				
				maxProbability = currProbability;
				mostProbable = Arrays.copyOfRange(inputArray, i, i + k);
			}
		}
		
		StringBuilder answer = new StringBuilder();
		
		for (char ans : mostProbable) {
			answer.append(ans);
		}
		
		System.out.println(answer);
	}
}
