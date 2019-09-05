import java.util.ArrayList;
import java.util.Scanner;

public class GreedyMotifSearch {

	public static void main(String[] args) {

		int k = 3;
		int t = 5;
		
		Scanner stdIn = new Scanner(System.in);
		String firstLine = stdIn.nextLine();
		char firstLineArray[] = firstLine.toCharArray();

		char dna[][] = new char[t][firstLine.length()];
		
		for (int i = 0; i < t; i++) {
			
			if (i == 0) {
				
				for (int j = 0; j < firstLine.length(); j++) {
					dna[i][j] = firstLineArray[j];
				}
				
			} else {
				
				String nextLine = stdIn.nextLine();
				char nextLineArray[] = new char[nextLine.length()];
				
				for (int j = 0; j < nextLine.length(); j++) {
					dna[i][j] = nextLineArray[j];
				}
			}
		}
		
		stdIn.close();
		
		ArrayList<String> bestMotifs = new ArrayList<String>();
		
		for (int i = 0; i < t; i++) {
			
			StringBuilder currMotif = new StringBuilder();
			
			for (int j = 0; j < k; j++) {
				currMotif.append(dna[i][j]);
			}
			
			bestMotifs.add(currMotif.toString());
		}
	}

}
