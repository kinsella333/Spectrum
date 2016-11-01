package gridCreation;

public class ColorSquare {

		private String color, neighbors = "";
		private int position = 0;
		public boolean placement = false;
		
		public ColorSquare(){
			
		}
		
		public ColorSquare(String color){
			this.color = color;
		}
		
		public void setPosition(int position){
			this.position = position;
		}
		
		public int getPosition(){
			return this.position;
		}
		
		public void setColor(String color){
			this.color = color;
		}
		
		public String getColor(){
			return this.color;
		}
		
		public void setNeighbors(String neighbors){
			this.neighbors = neighbors;
		}
		
		public String getNeighbors(){
			return this.neighbors;
		}
		@Override
		public String toString(){
			String output = this.color;
			return output;
		}
		
}
