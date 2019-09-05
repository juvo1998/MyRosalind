import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DeBruijnGraph {

	public static void main(String[] args) {

		// Map: from string -> string array
		File file = new File("input.txt");
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int k = Integer.parseInt(fileScanner.nextLine());
		String input = fileScanner.nextLine();

		TreeMap<String, ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();
		int subLength = k - 1;	
		
		for (int i = 0; i < input.length() - subLength; i++) {
			
			String curr = input.substring(i, i + subLength);
			//System.out.println(curr);
			
			if (!map.containsKey(curr)) {
				ArrayList<String> currList = new ArrayList<String>();
				currList.add(input.substring(i + 1, i + subLength + 1));
				map.put(curr, currList);
			} else {
				map.get(curr).add(input.substring(i + 1, i + subLength + 1));
				Collections.sort(map.get(curr));
			}
		}
		
		StringBuilder ans = new StringBuilder();
		for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> values = entry.getValue();
			StringBuilder strValues = new StringBuilder();
			for (String v : values) {
				strValues.append(v + ",");
			}
			strValues.deleteCharAt(strValues.length() - 1);
			ans.append(key + " -> " + strValues + System.getProperty("line.separator"));
		}
		
		System.out.println(ans);
	}
}
