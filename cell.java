public class cell{
	private boolean wumpus = false;
	private boolean pit = false;
	private boolean breeze = false;
	private boolean smell = false;
	private boolean gold = false;
	private boolean glimmer = false;
	private boolean player = false;
	private boolean bow = false;

	public cell(){}

	public boolean getwumpus(){
		return this.wumpus;
	}

	public void setwumpus(boolean alive){
		this.wumpus = alive;
	}

	public boolean getpit(){
		return this.pit;
	}

	public void setpit(){
		this.pit = true;
	}

	public boolean getbreeze(){
		return this.breeze;
	}

	public void setbreeze(){
		this.breeze = true;
	}

	public boolean getsmell(){
		return this.smell;
	}

	public void setsmell(){
		this.smell = true;
	}

	public boolean getgold(){
		return this.gold;
	}

	public void setgold(){
		this.gold = true;
	}

	public boolean getglimmer(){
		return this.glimmer;
	}

	public void setglimmer(){
		this.glimmer = true;
	}

	public boolean getplayer(){
		return this.player;
	}

	public void setplayer(boolean alive){
		this.player = alive;
	}
	
	public boolean getbow(){
		return this.bow;
	}
	
	public void setbow(boolean there){
		this.bow = there;
	}
	
}
