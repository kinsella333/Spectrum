package gridCreation;

public class ColorSquare {

		private String color;
		
		public ColorSquare(){
			
		}
		
		public ColorSquare(String color){
			this.color = color;
		}
		
		public void setColor(String color){
			this.color = color;
		}
		
		public String getColor(){
			return this.color;
		}
		@Override
		public String toString(){
			String output = this.color;
			return output;
		}
		
}
