import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class LloydAlgorithm {

	public static void main(String[] args) {

		File inputFile = new File("input.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		final int k = scanner.nextInt();
		final int DIMENSIONS = scanner.nextInt();
		//System.out.println("k is " + k + " and m is " + DIMENSIONS);
		
		scanner.nextLine();
		ArrayList<ArrayList<Double>> points = new ArrayList<ArrayList<Double>>();
		while (scanner.hasNextLine()) {
			String currLine = scanner.nextLine();
			String contents[] = currLine.split(" ");
			//System.out.println(currLine);
			ArrayList<Double> currPoint = new ArrayList<Double>();
			for(int i = 0; i < DIMENSIONS; i++) {
				currPoint.add(Double.parseDouble(contents[i]));
			}
			points.add(currPoint);
		}
		scanner.close();
		
		final int NUM_POINTS = points.size();
		
		// centers: a list of points
		ArrayList<ArrayList<Double>> centers = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < k; i++) {
			centers.add(points.get(i));
		}
		
		// clusters: a set of lists of points. In each list, the first element is the center.
		HashSet<ArrayList<ArrayList<Double>>> clusters = new HashSet<ArrayList<ArrayList<Double>>>();
		for (int i = 0; i < k; i++) {
			ArrayList<ArrayList<Double>> currCluster = new ArrayList<ArrayList<Double>>();
			currCluster.add(points.get(i));
			clusters.add(currCluster);
		}
		
		boolean centersAreChanging = true;
		while (centersAreChanging) {
			
			clusters.clear();
			for (int i = 0; i < k; i++) {
				ArrayList<ArrayList<Double>> currCluster = new ArrayList<ArrayList<Double>>();
				currCluster.add(centers.get(i));
				clusters.add(currCluster);
			}
			
			// Assign points: run through points and assign it to nearest center-cluster
			for (int i = 0; i < NUM_POINTS; i++) { // for each point...
				
				double min = Double.MAX_VALUE;
				int corI = 0;
				for (int j = 0; j < k; j++) { // for each center...
					
					double innerSum = 0.0;
					for (int d = 0; d < DIMENSIONS; d++) {
						innerSum += Math.pow(points.get(i).get(d) - centers.get(j).get(d), 2);
					}
					innerSum = Math.sqrt(innerSum);
					if (innerSum < min) {
						corI = j;
						min = innerSum;
					}
				}
				
				for (ArrayList<ArrayList<Double>> e : clusters) { // for each cluster...
					if (e.get(0).equals(centers.get(corI))) {
						e.add(points.get(i));
					}
				}
			}
			
			// At this point, clusters should have points assigned... now calculate new center
			ArrayList<ArrayList<Double>> oldCenters = new ArrayList<ArrayList<Double>>(centers);
			for (ArrayList<ArrayList<Double>> c : clusters) { // for each cluster...
				
				ArrayList<Double> newCenter = new ArrayList<Double>();
				for (int d = 0; d < DIMENSIONS; d++) { // for each dimension...
					double dimSum = 0.0;
					int i = 0;
					for (ArrayList<Double> p : c) { // for each point in the cluster...
						if (i == 0) {
							i++;
							continue;
						}
						//System.out.println(p.get(d));
						dimSum += p.get(d);
						//System.out.println(dimSum);
					}
					//System.out.println(NUM_POINTS);
					dimSum = dimSum/(c.size() - 1);
					//System.out.println(dimSum);
					newCenter.add(dimSum);
				}
				
				int oldCenterIndex = centers.indexOf(c.get(0));
				//System.out.println(oldCenterIndex);
				c.set(0, newCenter);
				centers.set(oldCenterIndex, newCenter);
			}
			
			for (ArrayList<Double> c : centers) {
				//System.out.println(c);
			}
			
			// Check if centers actually changed
			for (ArrayList<Double> cen : oldCenters) { // for each old center...
				// System.out.println(cen);
				if (!centers.contains(cen)) {
					break;
				}
				centersAreChanging = false;
			}
		}
		
		StringBuilder answer = new StringBuilder();
		for (ArrayList<Double> c : centers) {
			answer.append(c.toString());
		}
		
		//System.out.println(answer);
		
		String answer2 = answer.toString().replaceAll(",", "");
		answer2 = answer2.toString().replaceAll("\\[", "");
		answer2 = answer2.toString().replaceAll("\\]", " ");
		
		String answers[] = answer2.split(" ");
		ArrayList<Double> dAnswers = new ArrayList<Double>();
		for (String e : answers) {
			dAnswers.add(Double.parseDouble(e));
		}
		
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.HALF_UP);
		int lb = 0;
		for (int i = 0; i < dAnswers.size(); i++) {
			System.out.print(df.format(dAnswers.get(i)) + " ");
			lb++;
			if (lb == DIMENSIONS) {
				lb = 0;
				System.out.println();
			}
		}
	}
}
