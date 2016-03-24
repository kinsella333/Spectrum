package gridCreation;

import java.util.ArrayList;

public class SolutionGenerator {
	
	private int size, diff;
	private ArrayList<ColorSquare> grid = new ArrayList<ColorSquare>();
	
	public SolutionGenerator(int size, int diff){
		this.size = size;
		this.diff = diff;
		
		if(this.size > 10 || this.size < 2){
			System.out.println("Invalid Grid Size");
			grid.add(new ColorSquare('X'));
			this.size = 1;
		}else{
			generate();
		}
		
	}
	
	private void generate(){
		
	}
	
	private ArrayList<ColorSquare> colorGen(){
		ArrayList<ColorSquare> colorSet = new ArrayList<ColorSquare>();
		while(colorSet.size() < this.size + 1){
			
		}
		
		return colorSet;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public int getDiff(){
		return this.diff;
	}
	
	public ArrayList<ColorSquare> getGrid(){
		return this.grid;
	}
	
	@Override
	public String toString(){
		String output = "";
		for(int i = 0; i < this.size*this.size; i++){
			if(i % this.size == 0 && i != 0){
				output = output + "\n";
			}
			output = output + " " + grid.get(i);
		}
		return output;
	}
	
}
