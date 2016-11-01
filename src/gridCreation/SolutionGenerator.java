package gridCreation;

import java.util.ArrayList;
import java.util.Random;

public class SolutionGenerator {
	
	public static int NUMBER_OF_COLORS = 6;
	public static String COLORS = "YBGORP";
	private int size, difficulty, totalSize, depth = 0, testI = 0;
	private ArrayList<ColorSquare> grid = new ArrayList<ColorSquare>();
	private int[] colorCount = new int[6];
	public int[] errorCount = new int[6];
	public boolean deadlock = true, failedRecursive = false;
	
	public SolutionGenerator(int size, int difficulty){
		this.size = size;
		this.difficulty = difficulty;
		this.totalSize = size*size;
		int k = 0;
		while(this.deadlock){
			this.grid = new ArrayList<ColorSquare>();
			populate();
			generate();
			k++;
			if(k == 10000){
				System.err.println("Poop");
				return;
			}
		}
		
		for(int i = 0; i < this.totalSize; i++){
			
			switch(this.grid.get(i).getColor()){
		 	case "Y":
		 		this.colorCount[0]++;
		 		break;
		 	case "B":
		 		this.colorCount[1]++;
		 		break;
		 	case "G":
		 		this.colorCount[2]++;
		 		break;
		 	case "O":
		 		this.colorCount[3]++;
		 		break;
		 	case "R":
		 		this.colorCount[4]++;
		 		break;
		 	case "P":
		 		this.colorCount[5]++;
		 		break;
		 	default:
		 		break;
			}
		}
	}
	
	private void populate(){
		ColorSquare square = new ColorSquare("%");
		for(int i = 0; i < this.totalSize; i++){
			square.setPosition(i);
			this.grid.add(square);
		}
		for(int i = 0; i < this.totalSize; i++){
			this.grid.get(i).setNeighbors(getNeighbors(i));
		}
	}
	
	private void generate(){
		//String test = "PYGYRPRYGRRRPPGYYYG";
		int count = 0;
		for(int i = 0; i < this.totalSize; i++){
			if(this.grid.get(i).getColor() != "%"){
				continue;
			}
			this.depth = 0;
			testI = i;
			failedRecursive = false;
			String neighbors = getNeighbors(i);
			ColorSquare square = /*new ColorSquare(test.charAt(count) + "");*/randSquare();
			//count++;
			square.setNeighbors(neighbors);
			
			if(!place(neighbors, square, i)){
				int k;
				for(k = 0; k < NUMBER_OF_COLORS; k++){
					square.setColor("%");
					ColorSquare newS = new ColorSquare(COLORS.substring(k, k+1));
					neighbors = getNeighbors(i);
					newS.setNeighbors(neighbors);
					if(place(neighbors, newS, i)){
						k = -1;
						break;
					}
				}
				if(k != -1){
					return;
				}
			}
			//test = test.concat(this.grid.get(i).getColor());
		}
		this.deadlock = false;
		//System.out.println("TEST: " + test);
	}
	
	private boolean checkNeighbors(String neighbors, ColorSquare current, int index){
		switch(current.getColor()){
		case "R":
			if(!neighbors.contains("R")){
				return false;
			}
			break;
		case "P":
			if(neighbors.contains("P")){
				return false;
			}
			break;
		case "G":
			if(!neighbors.contains("B")){
				return false;
			}
			break;
		case "B":
			int count = 0;
			for(int i = 0; i < 4; i++){
				if(neighbors.charAt(i) == 'G') count++;
			}
			if(count < 2){
				return false;
			}
			break;
		case "Y":
			if(!neighbors.contains("@")) return false;
			break;
		case "O":
			if(!checkOrange(neighbors)) return false;
	 		break;
		default:
			return true;
		}
		return true;
	}
	
	private boolean checkNeighborsAddition(String neighbors, ColorSquare current, int index){
		if(!neighbors.contains("%")) return false;
		int rand = 9;
		ColorSquare square = new ColorSquare("@");
		boolean fixed = false, changed = false;
		String rightNeighbors = null;
		String botNeighbors = null;
		if(current.placement){
			rand = (new Random()).nextInt(100);
			fixed = true;
		}else if(!(current.getColor() == "P" || current.getColor() == "Y")){
			current.setNeighbors(neighbors);
			current.setPosition(index);
			current.placement = true;
			this.grid.set(index, current);
			updateNeighbors(index);
			
			/*if(current.getColor() == "B"){
				System.out.println("3: Index: " + index + " Neighbors: " + neighbors);
			}*/
			
			changed = true;
		}
		if(neighbors.charAt(1) != '@'){
			rightNeighbors = getNeighbors(index + 1);
		}
		if(neighbors.charAt(2) != '@'){
			botNeighbors = getNeighbors(index + this.size);
		}
		
		switch(current.getColor()){
		case "R":
			square.setColor("R");
			break;
		case "P":
			return false;
		case "G":
			square.setColor("B");
			break;
		case "B":
			square.setColor("G");
			break;
		case "Y":
			return false;
		case "O":
			fixed = false;
	 		break;
		default:
			return true;
		}
		if(depth > this.size/2 && fixed == true){
			return true;
		}else{
			if((neighbors.charAt(1) == '%' && rand%10 > 7) && square.getColor() != "@"){
				square.setNeighbors(rightNeighbors);
				if(place(rightNeighbors, square, index + 1)){
					fixed = true;
				}
			}
			rand = rand * index;
			if((neighbors.charAt(2) == '%' && (rand%10 > 7 || fixed == false)) && square.getColor() != "@"){
				square.placement = false;
				square.setNeighbors(botNeighbors);
				square.setPosition(index + this.size);
				if(place(botNeighbors, square, index + this.size)){
					fixed = true;
				}
			}
		}
		
		if((changed && !fixed) || this.failedRecursive){
			current.setColor("%");
			current.placement = false;
			this.grid.set(index, current);
			updateNeighbors(index);
			fixed = false;
			if(depth > 1){
				this.failedRecursive = true;
			}
		}
		return fixed;
	}
	
	private boolean place(String neighbors,ColorSquare square, int i){
		if(this.grid.get(i).getColor() != "%") return false;
		boolean canPlace = false;
		this.depth++;
		//System.out.println("Root Index: " + testI + "\n" + this.toString() + "\n");
		if(checkNeighbors(neighbors, square, i)){
			square.setPosition(i);
			square.setNeighbors(getNeighbors(i));
			square.placement = true;
			this.grid.set(i, square);
			updateNeighbors(i);
			canPlace = true;
		}
		if(checkNeighborsAddition(neighbors, square, i) && !canPlace){
			square.setPosition(i);
			square.setNeighbors(getNeighbors(i));
			square.placement = true;
			this.grid.set(i, square);
			updateNeighbors(i);
			canPlace = true;
		}
		return canPlace;
	}
	
	private void updateNeighbors(int i){
		if(i >= this.size){
			this.grid.get(i - this.size).setNeighbors(getNeighbors(i - this.size));
		}
		if(i < this.size*(this.size - 1)){
			this.grid.get(i + this.size).setNeighbors(getNeighbors(i + this.size));
		}
		if(i%this.size != 0){
			this.grid.get(i - 1).setNeighbors(getNeighbors(i - 1));
		}
		if((i+1)%(this.size) != 0){
			this.grid.get(i + 1).setNeighbors(getNeighbors(i + 1));
		}
	}
	
	private boolean checkOrange(String neighbors){
		if(neighbors.contains("O") || neighbors.contains("%")){
			return false;
		}
		int[] counts = new int[6];
 		
 		for(int i = 0; i < 4; i++){
 			switch(neighbors.charAt(i)){
		 	case 'Y':
		 		counts[0]++;
		 		break;
		 	case 'B':
		 		counts[1]++;
		 		break;
		 	case 'G':
		 		counts[2]++;
		 		break;
		 	case 'R':
		 		counts[3]++;
		 		break;
		 	case 'P':
		 		counts[4]++;
		 		break;
		 	default:
		 		break;
 			}
 		}
 		for(int i = 0; i < 5; i++){
 			if(counts[i] > 1) return false;
 		}
 		 
		return true;
	}
	
	private String getNeighbors(int i){
		String neighbors = "";
		if(i < this.size){
			neighbors += "@";
		}else{
			neighbors += grid.get(i - this.size).getColor();
		}
		if(i%this.size == this.size - 1){
			neighbors += "@";
		}else{
			neighbors += grid.get(i + 1).getColor();
		}
		if(i > (this.size*(this.size -1)) - 1){
			neighbors += "@";
		}else{
			neighbors += grid.get(i + this.size).getColor();
		}
		if(i%size == 0){
			neighbors += "@";
		}else{
			neighbors += grid.get(i - 1).getColor();
		}
		return neighbors;
	}
	
	private ColorSquare randSquare(){
		ColorSquare rand = new ColorSquare("@");
		
		int random = (new Random()).nextInt(10);
		
		if(random < this.difficulty*2){
			rand.setColor("O");
		}
		else if(random < this.difficulty*3){
			rand.setColor("Y");
		}
		
		else if(random < this.difficulty*4){
			rand.setColor("G");
		}
		else if(random < this.difficulty*4){
			rand.setColor("B");
		}
		else if(random < this.difficulty*7){
			rand.setColor("P");
		}
		else{
			rand.setColor("R");
		}
		return rand;
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
	
	public boolean isGridValid(){
		boolean valid = true;
		for(int i = 0; i < this.totalSize; i++){
			if(!checkNeighbors(this.grid.get(i).getNeighbors(), this.grid.get(i), i)){
				System.out.println("ERROR IN SQUARE AT INDEX: " + i + "  COLOR: " + this.grid.get(i).getColor() + "  Neighbors: " + this.grid.get(i).getNeighbors());
				switch(this.grid.get(i).getColor()){
			 	case "Y":
			 		this.errorCount[0]++;
			 		break;
			 	case "B":
			 		this.errorCount[1]++;
			 		break;
			 	case "G":
			 		this.errorCount[2]++;
			 		break;
			 	case "O":
			 		this.errorCount[3]++;
			 		break;
			 	case "R":
			 		this.errorCount[4]++;
			 		break;
			 	case "P":
			 		this.errorCount[5]++;
			 		break;
			 	default:
			 		break;
	 			}
				valid = false;
			}
		}
		return valid;
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
