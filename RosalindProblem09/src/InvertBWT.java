import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InvertBWT {

	public static void main(String[] args) {

		File inputFile = new File("input.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String input = scanner.nextLine();
		char inputArray[] = input.toCharArray();
		char sortedArray[] = input.toCharArray();
		Arrays.sort(sortedArray);
		
		ArrayList<Character> bwt = new ArrayList<Character>();
		for (char c : inputArray) {
			bwt.add(c);
		}
		ArrayList<Character> first = new ArrayList<Character>();
		for (char c : sortedArray) {
			first.add(c);
		}
		
		StringBuilder inverse = new StringBuilder();
		int startIndex = bwt.indexOf('$');
		while(true) {
			
			char desiredChar = first.get(startIndex);
			int counterFirst = 0;
			
			if (desiredChar == '$') {
				inverse.append('$');
				break;
			}
			
			for (int i = 0; i < first.size(); i++) {
				if (first.get(i) == desiredChar) {
					counterFirst++;
				}
				if (i == startIndex) {
					break;
				}
			}
			
			inverse.append(desiredChar);
			
			int counterBwt = 0;
			for (int i = 0; i < bwt.size(); i++) {
				if (desiredChar == bwt.get(i)) {
					counterBwt++;
				}
				if (counterBwt == counterFirst) {
					startIndex = i;
					break;
				}
			}
		}
		
		System.out.println(inverse.toString());
	}
}
