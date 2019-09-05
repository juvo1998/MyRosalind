import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CyclicPeptideSearch {

	public static void main(String[] args) {

		File inputFile = new File("input.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Integer> inputSpectrum = new ArrayList<Integer>();
		while(scanner.hasNext()) {
			inputSpectrum.add(scanner.nextInt()); 
		}
		
		scanner.close();
		
		char[] aminoAcids = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M',
				'F', 'P', 'S', 'T', 'W', 'Y', 'V'};
		
		// Create a map for easy finding of amino mass.
		HashMap<Character, Integer> aminoMass = new HashMap<Character, Integer>();
		aminoMass.put('A', 71);
		aminoMass.put('R', 156);
		aminoMass.put('N', 114);
		aminoMass.put('D', 115);
		aminoMass.put('C', 103);
		aminoMass.put('Q', 128);
		aminoMass.put('E', 129);
		aminoMass.put('G', 57);
		aminoMass.put('H', 137);
		aminoMass.put('I', 113);
		aminoMass.put('L', 113);
		aminoMass.put('K', 128);
		aminoMass.put('M', 131);
		aminoMass.put('F', 147);
		aminoMass.put('P', 97);
		aminoMass.put('S', 87);
		aminoMass.put('T', 101);
		aminoMass.put('W', 186);
		aminoMass.put('Y', 163);
		aminoMass.put('V', 99);

		ArrayList<String> tePeptides = new ArrayList<String>();
		ArrayList<String> peptides = new ArrayList<String>(); // returning this
		peptides.add("");
		
		// While loop: only stop when peptides are empty.
		while (peptides.size() != 0) {
			
			ArrayList<String> tempPeptides = new ArrayList<String>();
			for (int i = 0; i < peptides.size(); i++) {
				
				String curr = peptides.get(i);
				for (int j = 0; j < aminoAcids.length; j++) {
					tempPeptides.add(curr + aminoAcids[j]);
				}
			}
			
			peptides.clear();
			peptides.addAll(tempPeptides);
			
			for (int i = 0; i < peptides.size(); i++) {
				
				String peptideStr = peptides.get(i);
				int[] currSpectrum = null;
				// cyclospectrum to return to above
				// Caluclate Cyclospectrum from the current spectrum's iterations.
				if (peptideStr.length() == 1) {
					
					currSpectrum = new int[1];
					currSpectrum[0] = aminoMass.get(peptideStr.charAt(0));
					
				} else {
					
					currSpectrum = new int[(peptideStr.length() - 1) * peptideStr.length() + 2];
					currSpectrum[1] = peptideMass(peptideStr, aminoMass);
					
					int cSpecIndex = 2;
					
					for (int j = 0; j < peptideStr.length(); j++) {
						currSpectrum[cSpecIndex++] = peptideMass("" + peptideStr.charAt(j), aminoMass);
					}
					
					for (int j = 2; j < peptideStr.length(); j++) {
						for (int k = 0; k < peptideStr.length(); k++) {
							
							String temp = "";
							if (k > peptideStr.length() - j) {
								temp += peptideStr.substring(k, peptideStr.length());
								temp += peptideStr.substring(0, (k + j) % peptideStr.length());
							} else {
								temp = peptideStr.substring(k, k + j);
							}
							
							currSpectrum[cSpecIndex++] = peptideMass(temp, aminoMass);
						}
					}
				}
				
				Arrays.sort(currSpectrum);
				
				//System.out.println("For i: " + i + ", " + peptideMass(peptideStr, aminoMass) + " and " + inputSpectrum.get(inputSpectrum.size() - 1));
				//System.out.println(!isConsistent(inputSpectrum, currSpectrum));
				if (peptideMass(peptideStr, aminoMass) == inputSpectrum.get(inputSpectrum.size() - 1)) {
					
					//if (inputSpectrum.equals(Arrays.asList(currSpectrum))) {
					//	tePeptides.add(peptideStr);
					//}
					
					for (int j = 0; j < inputSpectrum.size(); j++) {
						if (!(inputSpectrum.get(j) == currSpectrum[j])) {
							break;
						}
						if (j == inputSpectrum.size() - 1) {
							tePeptides.add(peptideStr);
						}
					}
					
					peptides.remove(i--);
					
				} else if (!isConsistent(inputSpectrum, currSpectrum)) {
					peptides.remove(i--);
				}
			}
		}
		
		// Begin building output
		ArrayList<String> output = new ArrayList<String>();
		
		for (int i = 0; i < tePeptides.size(); i++) {
			
			ArrayList<Integer> peptideInts = pepToInt(tePeptides.get(i), aminoMass);
			String build = "" + peptideInts.get(0);
			
			for (int j = 1; j < peptideInts.size(); j++) {
				build += ("-" + peptideInts.get(j));
			}
			
			if (!output.contains(build)) {
				output.add(build);
			}
		}
		
		for (int i = 0; i < output.size(); i++) {
			System.out.print(output.get(i) + " ");
		}
		
	}
	
	// Converts the peptides to Integers by iterating through the string
	private static ArrayList<Integer> pepToInt(String peptideStr, HashMap<Character, Integer> aminoMass) {
		
		ArrayList<Integer> peptideInts = new ArrayList<Integer>();
		for (int i = 0; i < peptideStr.length(); i++) {
			peptideInts.add(aminoMass.get(peptideStr.charAt(i)));
		}
		
		return peptideInts;
	}

	// Checks consistency through the input spectrum and the current iteration's spectrum.
	private static boolean isConsistent(ArrayList<Integer> inputSpectrum, int[] currSpectrum) {

		for (int i = 0; i < currSpectrum.length; i++) {
			
			int v = currSpectrum[i];
			
			int sumValCurr = 0;
			for (int j = 0; j < currSpectrum.length; j++) {
				if (currSpectrum[j] == v) {
					sumValCurr++;
				}
			}
			
			int sumValInput = 0;
			for (int j = 0; j < inputSpectrum.size(); j++) {
				if (inputSpectrum.get(j) == v) {
					sumValInput++;
				}
			}
			
			if (sumValCurr > sumValInput) {
				return false;
			}
		}
		
		return true;
	}

	// Easily calculate's mass using earlier amino mass map.
	private static int peptideMass(String peptideStr, HashMap<Character, Integer> aminoMass) {
		int mass = 0;
		for (int i = 0; i < peptideStr.length(); i++) {
			mass += aminoMass.get(peptideStr.charAt(i));
		}
		return mass;
	}
}
