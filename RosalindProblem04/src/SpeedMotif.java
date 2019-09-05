import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpeedMotif {

	public static void main(String[] args) throws IOException {
		
		File inputFile = new File("rosalind_kmp.txt");
		Scanner stdIn = new Scanner(inputFile);
		stdIn.nextLine();
		
		StringBuilder input = new StringBuilder();
		
		while (stdIn.hasNextLine()) {
			
			input.append(stdIn.nextLine().trim());
		}
		
		stdIn.close();
		
		System.out.println(input.charAt(0));
		char inputArray[] = input.toString().toCharArray();
		
		int j = 0;
		
		int failure[] = new int[input.length()];
		failure[0] = j;
		
		
		int reminder = -1;
		int i = 1;
		
		while (i < input.length()) { 
			
			if (inputArray[i] == inputArray[j]) {

				j++;
				failure[i] = j;
				
				if (inputArray[i] == inputArray[0]) {
					reminder = i;
				}
				
				i++;
				
			} else if (inputArray[i] == inputArray[0]) {
				
				reminder = i;
				j = 0;
				
			} else {
				
				j = 0;
				
				if (reminder != -1 && inputArray[i] == inputArray[i - reminder]) {
					failure[i] = i - reminder + 1;
					j = i - reminder + 1;
				} else {
					failure[i] = 0;
				}
				
				reminder = -1;
				i++;
			}
		}
		
		// File outputFile = new File("output.txt");
		// outputFile.createNewFile();

		// FileWriter writer = new FileWriter(outputFile);
		
		for (int element : failure) {
			System.out.print(element + " ");
		}
	
		// writer.close();
	}
}
