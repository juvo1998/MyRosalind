import java.util.HashMap;
import java.util.Scanner;

public class CountingDna {

	public static void main(String[] args) {
		
		Scanner stdIn = new Scanner(System.in);
		String dna = stdIn.nextLine();
		char dnaArray[] = dna.toCharArray();
		
		// A C G T
		
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		map.put('A', 0);
		map.put('C', 0);
		map.put('G', 0);
		map.put('T', 0);
		
		for (char dnaChar : dnaArray) {
			map.put(dnaChar, map.get(dnaChar) + 1);
		}
		
		System.out.println(map.get('A') + " " + map.get('C') + " " + map.get('G') + " " + map.get('T'));
		
		stdIn.close();
	}

}
