package gridCreation;

import java.util.ArrayList;
import java.util.Random;

public class SolutionGenerator {
	
	public static int NUMBER_OF_COLORS = 5;
	private int size, difficulty, totalSize, redDefault;
	private ArrayList<ColorSquare> grid = new ArrayList<ColorSquare>();
	private int[] colorCount = new int[5];
	
	public SolutionGenerator(int size, int difficulty){
		this.size = size;
		this.difficulty = difficulty;
		this.totalSize = size*size;
		
		generate();
	}
	
	private void generate(){
		
		for(int i = 0; i < totalSize; i++){
			double random = this.difficulty*((new Random()).doubles(0.0,this.difficulty).limit(1).toArray()[0]);
			int temp = i;
			
			//Begins Red
			if(redDefault == 1){
				grid.add(new ColorSquare("R"));
				colorCount[0]++;
				redDefault = 0;
			}
			//Orange
			else if(random > this.difficulty*.95 && canPlace(new ColorSquare("O"), grid, this.size) == 1){
				grid.add(new ColorSquare("O"));
				colorCount[4]++;
			}
			//Yellow
			else if(random > this.difficulty*.2 && canPlace(new ColorSquare("Y"), grid,this.size) == 1){
				grid.add(new ColorSquare("Y"));
				colorCount[3]++;
			}
			//Blue
			else if(random > this.difficulty*.77 && canPlace(new ColorSquare("G"), grid, this.size ) == 1){
				grid.add(new ColorSquare("G"));
				colorCount[2]++;
			}
			//Green
			else if(random > this.difficulty*.5 && canPlace(new ColorSquare("B"), grid, this.size) == 1){
				grid.add(new ColorSquare("B"));
				colorCount[1]++;
			}
			//Red
			else if(canPlace(new ColorSquare("R"), grid,this.size) == 1){
				grid.add(new ColorSquare("R"));
				colorCount[0]++;
			}
			else if(canPlace(new ColorSquare("DR"), grid,this.size) == -1){
				redDefault = 1;
				i--;
			}else{
				i--;
			}
			
		}
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int[] getColorCount(){
		return this.colorCount;
	}
	
	public int getDiff(){
		return this.difficulty;
	}
	
	public ArrayList<ColorSquare> getGrid(){
		return this.grid;
	}
	
	public static int canPlace(ColorSquare square, ArrayList<ColorSquare> grid, int size){
		int position = grid.size(), upCheck = 0, leftCheck = 0;
		
		if(position - size > 0){
			upCheck = 1;
		}else if(position - 1 > 0){
			leftCheck = 1;
		}
		
		switch(square.getColor()){
		 	case "R":
		 		if(upCheck == 1){
		 			if(grid.get(position - size).getColor() == "R"){
		 				return 1;
		 			}
		 		}
		 		else if(leftCheck == 1){
		 			if(grid.get(position - 1).getColor() == "R"){
		 				return 1;
		 			}
		 		} 
		 		return 0;
		 	case "G":
		 		if(upCheck == 1){
		 			if(grid.get(position - size).getColor() == "B"){
		 				return 1;
		 			}
		 		}
		 		else if(leftCheck == 1){
		 			if(grid.get(position - 1).getColor() == "B"){
		 				return 1;
		 			}
		 		} 
		 		return 0;
		 	case "B":
		 		
		 		return 0;
		 	case "Y":
		 		if(position % size == 0 || position < size || position % size == size - 1 || position > size*(size - 1)){
		 			return 1;
		 		}
		 		return 0;
		 	case "O":
		 		return 0;
		 	default:
		 		return -1;
			 
		 }
	}
	
	@Override
	public String toString(){
		String output = "";
		for(int i = 0; i < totalSize; i++){
			if(i % this.size == 0 && i != 0){
				output = output + "\n";
			}
			output = output + " " + grid.get(i);
		}
		return output;
	}
	
}
