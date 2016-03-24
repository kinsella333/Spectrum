package gridCreation;

public class ColorSquare {

		private char color;
		
		public ColorSquare(){
			
		}
		
		public ColorSquare(char color){
			this.color = color;
		}
		
		public void setColor(char color){
			this.color = color;
		}
		
		public char getColor(){
			return this.color;
		}
		@Override
		public String toString(){
			String output = "" + this.color;
			return output;
		}
		
}
