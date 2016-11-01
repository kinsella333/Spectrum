package test;
import gridCreation.*;

public class Test {

	public static void main(String[] args) {
		double eTime = System.nanoTime();
		
		SolutionGenerator test = new SolutionGenerator(7, 1);
	
		int[] colorCount = test.getColorCount();
		System.out.println(test.toString() + "\n");
		
		test.isGridValid();
		
		System.out.println("\nError Counts\nYellow: "+test.errorCount[0]);
		System.out.println("Blue: "+test.errorCount[1]);
		System.out.println("Green: "+test.errorCount[2]);
		System.out.println("Orange: "+test.errorCount[3]);
		System.out.println("Red: "+test.errorCount[4]);
		System.out.println("Purple: "+test.errorCount[5]);
		

		System.out.println("\nColor Counts\nYellow: "+colorCount[0]);
		System.out.println("Blue: "+colorCount[1]);
		System.out.println("Green: "+colorCount[2]);
		System.out.println("Orange: "+colorCount[3]);
		System.out.println("Red: "+colorCount[4]);
		System.out.println("Purple: "+colorCount[5]);
		
		
		System.out.println("\n" + Math.round(System.nanoTime() - eTime)/1000000000d + " seconds");
		
		
	}

}
