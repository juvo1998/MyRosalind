import java.util.Scanner;

public class StringOccurrences {

	public static void main(String[] args) {
		
		StringBuilder output = new StringBuilder();
		
		int equalCounter = 0;
		
		Scanner stdIn = new Scanner(System.in);
		
		String substring = stdIn.nextLine();
		char substringArray[] = substring.toCharArray();
		
		String input = stdIn.nextLine();
		char inputArray[] = input.toCharArray();
		
		for (int i = 0; i < inputArray.length; i++) {
			
			for (int j = 0; j < substringArray.length; j++) {
				
				if (i + j >= inputArray.length) {
					break;
				}
				
				if (substringArray[j] == inputArray[i + j]) {
					equalCounter++;
				} else {
					break;
				}
			}
			
			if (equalCounter == substringArray.length) {
				output.append(i + " ");
			}
			
			equalCounter = 0;
		}
		
		output.deleteCharAt(output.length() - 1);
		System.out.println(output);
		
		stdIn.close();
	}
}
