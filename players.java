import java.util.ArrayList;

public class players extends account{
	private int playerID;
	private int whichSquare=1;
	private int punishment=0;
	private boolean isHeBankrupt=false;
	
	
	
	
	public boolean isHeBankrupt() {
		return isHeBankrupt;
	}

	public void setHeBankrupt(boolean isHeBankrupt) {
		this.isHeBankrupt = isHeBankrupt;
	}
	private ArrayList<property> ownProperty = new ArrayList<property>();
    

	public players(int money, int playerID, int whichSquare) {
		super(money);
		this.playerID = playerID;
		this.whichSquare = whichSquare;
	}	


	


	public ArrayList<property> getOwnProperty() {
		return ownProperty;
	}





	public void setOwnProperty(ArrayList<property> ownProperty) {
		this.ownProperty = ownProperty;
	}





	public int getPunishment() {
		return punishment;
	}

	public void setPunishment(int punishment) {
		this.punishment = punishment;
	}

	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getWhichSquare() {
		return whichSquare;
	}
	public void setWhichSquare(int whichSquare) {
		if(whichSquare<=40) {
			this.whichSquare = whichSquare;
		}
		else if(whichSquare>40) {
			this.whichSquare = whichSquare%40;
		}
	}
	
	
	

}
