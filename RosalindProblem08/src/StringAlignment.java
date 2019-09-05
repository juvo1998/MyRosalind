import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class StringAlignment {

	public static void main(String[] args) {

		File input = new File("input.txt");
		Scanner inputScanner = null;
		try {
			inputScanner = new Scanner(input);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String str1 = inputScanner.nextLine();
		String str2 = inputScanner.nextLine();
		inputScanner.close();
		
		int stringMatrix[][] = new int[str1.length() + 1][str2.length() + 1];
		stringMatrix[0][0] = 0;
		
		final int PENALTY = -5;
		
		File blosumFile = new File("blosum.txt");
		Scanner blosumScanner = null;
		try {
			blosumScanner = new Scanner(blosumFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int blosumMatrix[][] = new int[20][20];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				blosumMatrix[i][j] = Integer.parseInt(blosumScanner.next());
			}
		}
		blosumScanner.close();
		
		HashMap<Character, Integer> charToInt = new HashMap<Character, Integer>();
		charToInt.put('A', 0);
		charToInt.put('C', 1);
		charToInt.put('D', 2);
		charToInt.put('E', 3);
		charToInt.put('F', 4);
		charToInt.put('G', 5);
		charToInt.put('H', 6);
		charToInt.put('I', 7);
		charToInt.put('K', 8);
		charToInt.put('L', 9);
		charToInt.put('M', 10);
		charToInt.put('N', 11);
		charToInt.put('P', 12);
		charToInt.put('Q', 13);
		charToInt.put('R', 14);
		charToInt.put('S', 15);
		charToInt.put('T', 16);
		charToInt.put('V', 17);
		charToInt.put('W', 18);
		charToInt.put('Y', 19);
		
		for (int i = 0; i < str1.length(); i++) {
			stringMatrix[i + 1][0] = PENALTY * (i + 1);
		}
		for (int i = 0; i < str2.length(); i++) {
			stringMatrix[0][i + 1] = PENALTY * (i + 1);
		}
		
		for (int i = 1; i < str1.length() + 1; i++) {
			for (int j = 1; j < str2.length() + 1; j++) {
				
				int alignedScore = stringMatrix[i - 1][j - 1] + blosumMatrix[charToInt.get(str1.charAt(i - 1))][charToInt.get(str2.charAt(j - 1))];
				int str1ToGapScore = stringMatrix[i - 1][j] + PENALTY;
				int str2ToGapScore = stringMatrix[i][j - 1] + PENALTY;
				
				stringMatrix[i][j] = Integer.max(alignedScore, Integer.max(str1ToGapScore, str2ToGapScore));
			}
		}
		
		//for (int j = 0; j < str2.length() + 1; j++) {
		//	for (int i = 0; i < str1.length() + 1; i++) {
		//		System.out.print(stringMatrix[i][j] + " ");
		//	}
		//	System.out.println();
		//}
		System.out.println();
		System.out.println();

		System.out.println(stringMatrix[str1.length()][str2.length()]);
		
		int i = str1.length();
		int j = str2.length();
		StringBuilder str1Builder = new StringBuilder();
		StringBuilder str2Builder = new StringBuilder();
		
		while (i > 0 && j > 0) {
			if (stringMatrix[i][j] - blosumMatrix[charToInt.get(str1.charAt(i - 1))][charToInt.get(str2.charAt(j - 1))]
					== stringMatrix[i - 1][j - 1]) {
				str1Builder.append(str1.charAt(i - 1));
				str2Builder.append(str2.charAt(j - 1));
				i--;
				j--;
			} else if (stringMatrix[i][j] - PENALTY == stringMatrix[i][j - 1]) {
				str1Builder.append("-");
				str2Builder.append(str2.charAt(j - 1));
				j--;
			} else if (stringMatrix[i][j] - PENALTY == stringMatrix[i - 1][j]) {
				str1Builder.append(str1.charAt(i - 1));
				str2Builder.append("-");
				i--;
			} else {
				System.out.println("error");
			}
		}
		
		if (i > 0) {
			while (i > 0) {
				str1Builder.append(str1.charAt(i - 1));
				str2Builder.append("-");
				i--;
			}
		} else if (j > 0) {
			while (j > 0) {
				str1Builder.append("-");
				str2Builder.append(str2.charAt(j - 1));
				j--;
			}
		}
		
		str1Builder.reverse();
		str2Builder.reverse();
		
		System.out.println(str1Builder);
		System.out.println(str2Builder);
	}

}
