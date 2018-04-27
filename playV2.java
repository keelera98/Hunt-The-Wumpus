import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class playV2{

	public static void main(String args[]){
		Random rand = new Random();
		int x, y;
		int wx = 0;
		int wy = 0;
		int gx = 0;
		int gy = 0;
		int bx = 0;
		int by = 0;
		int size = 0;
		int tempsize = 0;
		int tempx = 0;
		int tempy = 0;
		Scanner input = new Scanner(System.in);
		int gamechoice = 0;
		boolean valid = false;
		boolean validx = false;
		boolean validy = false;
		boolean incorr = false;
		
		makeSound(true);
		System.out.println("Hunt The Wumpus");
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		System.out.println("Your job is to find the gold! Don't get caught by the Wumpus or fall into a pit!");
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		System.out.println("You can only move forward, backwards, left, and right. Watch for clues on where to go.");
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		System.out.println("If you find a bow you can kill the Wumpus.");
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		System.out.println("You can save your game after each move. Saves will exit the game.");
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		while(!incorr){
			try{
				System.out.printf("Enter 0 to load a game or 1 to play a new game: ");
				gamechoice = input.nextInt();
				if(gamechoice == 1){
					incorr = true;
				}else{
					if(gamechoice == 0){
						incorr = true;
					}else{
						incorr = false;
					}
				}
			}catch(InputMismatchException e){
				input.nextLine();
			}
		}
		if(gamechoice == 0){
			loadGame();
			//System.out.println("works");
		}
		if(gamechoice == 1){
			try{
				Thread.sleep(1000);
			}catch(Exception e){}

			while(!valid){
				System.out.println("What size do you want the map to be?");
				System.out.printf("Enter a value that is no less than 4 and no greater than 10: ");
				try{
					tempsize = input.nextInt();
					if((tempsize >= 4) && (tempsize <= 10)){
					size = tempsize;
					valid = true;
					}else{
						valid = false;
					}
				}catch(InputMismatchException e){
					input.nextLine();
				}
			}
				valid = false;

				cell ray[][] = new cell [size][size];

				for(int j = 0; j < size; j++){
					for(int z = 0; z < size; z++){
						ray[j][z] = new cell();
					}
				}

				x = rand.nextInt(size);
				y = rand.nextInt(size);

				//player will spawn in 0,0
				while(!valid){
					if((x == 0) && (y == 0)){
						x = rand.nextInt(size);
						y = rand.nextInt(size);
						valid = false;
					}else{
						wx = x;
						wy = y;
						valid = true;
					}
				}

				ray[wx][wy].setwumpus(true);

				if(wx - 1 >= 0){
					ray[wx -1][wy].setsmell();
				}
				if(wx + 1 < size){
					ray[wx + 1][wy].setsmell();
				}
				if(wy - 1 >= 0){
					ray[wx][wy - 1].setsmell();
				}
				if(wy + 1 < size){
					ray[wx][wy + 1].setsmell();
				}

				x = rand.nextInt(size);
				y = rand.nextInt(size);

				valid = false;

				while(!valid){
					if((wx == x) && (wy == y)){
						x = rand.nextInt(size);
						y = rand.nextInt(size);
						valid = false;
					}else{
						if((x == 0) && (y == 0)){
							x = rand.nextInt(size);
							y = rand.nextInt(size);
							valid = false;
						}else{
							gx = x;
							gy = y;
							valid = true;
						}
					}
				}

				ray[gx][gy].setgold();

				if(gx - 1 >= 0){
					ray[gx - 1][gy].setglimmer();
				}
				if(gx + 1 < size){
					ray[gx + 1][gy].setglimmer();
				}
				if(gy - 1 >= 0){
					ray[gx][gy - 1].setglimmer();
				}
				if(gy + 1 < size){
					ray[gx][gy + 1].setglimmer();
				}

				valid = false;

				setPit(ray, gx, gy, size, bx, by);

				ray[0][0].setplayer(true);
				
				x = rand.nextInt(size);
				y = rand.nextInt(size);

				valid = false;

				while(!valid){
					if((wx == x) && (wy == y)){
						x = rand.nextInt(size);
						y = rand.nextInt(size);
						valid = false;
					}else{
						if((x == 0) && (y == 0)){
							x = rand.nextInt(size);
							y = rand.nextInt(size);
							valid = false;
						}else{
							if((x == gx) && (y == gy)){
								x = rand.nextInt(size);
								y = rand.nextInt(size);
								valid = false;
							}else{
								bx = x;
								by = y;
								valid = true;
							}
						}
					}
				}

				ray[bx][by].setbow(true);
					
				System.out.println("You Enter a dark cave.");
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
				//makeSound(false);
				playGame(ray, size, tempx, tempy);
		}
				
	}

	public static void playGame(cell ray[][], int size, int tempx, int tempy){
		boolean corr = false;
		boolean incorr = false;
		boolean endgame = false;
		boolean fbow = false;
		boolean exists = false;
		//int tempx = 0;
		//int tempy = 0;
		int playerx = 0;
		int playery = 0;
		int savechoice = 0;
		int overw = 0;
		String choice;
		String filename = "";
		Scanner in = new Scanner(System.in);
		File fileoutput, fileoutput2, fileoutput3, fileoutput4, fileoutput5, fileoutput6, fileoutput7, fileoutput8, fileoutput9, fileoutput10, dir;
		PrintWriter writer, writer2, writer3, writer4, writer5, writer6, writer7, writer8, writer9, writer10;
		playerx = tempx;
		playery = tempy;
		while(endgame == false){

			try{
				Thread.sleep(500);
			}catch(Exception e){}
		
			/*for(int x2 = 0; x2 < size; x2++){
				for(int y2 = 0; y2 < size; y2++){
					System.out.println("x: " + x2 + " y: " + y2 + "\n" + " | values: Player " + ray[x2][y2].getplayer() + " Breeze " + ray[x2][y2].getbreeze() + " | Smell: " + ray[x2][y2].getsmell() + " | Glow: " +
					ray[x2][y2].getglimmer() + " | Pit: " + ray[x2][y2].getpit() + " | Wumpus: " + ray[x2][y2].getwumpus() + " | Gold: " + ray[x2][y2].getgold() + " | Bow: " + ray[x2][y2].getbow());
				}
			}*/
			System.out.println("Map of the Cave");
			
			for(int x2 = 0; x2 < size; x2++){
				for(int y2 = 0; y2 < size; y2++){
					if(ray[x2][y2].getplayer() == true){
						System.out.println("Room: " + "X: " + (x2 + 1) + " Y: " + (y2 + 1) + " | " + "You are here!");
					}else{
						System.out.println("Room: " + "X: " + (x2 + 1) + " Y: " + (y2 + 1) + " | " + "Unknown!");
					}
				}
			}
			
			try{
				Thread.sleep(500);
			}catch(Exception e){}

			ray[playerx][playery].setplayer(false);
			if(fbow){
				ray[playerx][playery].setbow(false);
			}
			
			if(ray[tempx][tempy].getsmell()){
				System.out.println("You smell something strange.");
				endgame = false;
				corr = false;
				incorr = false;
			}else{
				endgame = false;
				corr = false;
				incorr = false;
			}
			if(ray[tempx][tempy].getglimmer() == true){
				System.out.println("You see something glowing in the distance.");
				endgame = false;
				corr = false;
				incorr = false;
			}else{
				endgame = false;
				corr = false;
				incorr = false;
			}
			if(ray[tempx][tempy].getbreeze() == true){
				System.out.println("You feel a breeze.");
				endgame = false;
				corr = false;
				incorr = false;
			}else{
				endgame = false;
				corr = false;
				incorr = false;
			}
			if(ray[tempx][tempy].getbow() == true){
				if(!fbow){
					System.out.println("You found a bow!");
					endgame = false;
					corr = false;
					incorr = false;
					fbow = true;
				}
			}else{
				endgame = false;
				corr = false;
				incorr = false;
			}
			if((ray[tempx][tempy].getbreeze() != true) && (ray[tempx][tempy].getglimmer() != true) && (!ray[tempx][tempy].getsmell())){
				System.out.println("Nothing here, keep moving!");
			}

			while(!corr){	
				try{
					System.out.printf("Do you want to move left, right, forward, or backwards? ");
					choice = in.next().toLowerCase();
					if(choice.equals("left")){
						tempx = tempx - 1;
						corr = true;
						if(tempx < 0){
							System.out.println("You hit a wall!");
							tempx = tempx + 1;
							corr = false;
						}
					}else{
						if(choice.equals("right")){
							tempx = tempx + 1;
							corr = true;
							if(tempx >= ray.length){
								tempx = tempx - 1;
								corr = false;
								System.out.println("You hit a wall!");
							}
						}else{
							if(choice.equals("backwards")){
								tempy = tempy - 1;
								corr = true;
								if(tempy < 0){
									tempy = tempy + 1;
									corr = false;
									System.out.println("You hit a wall!");
								}
							}else{
								if(choice.equals("forward")){
									tempy = tempy + 1;
									corr = true;
									if(tempy >= ray.length){
										tempy = tempy - 1;
										corr = false;
										System.out.println("You hit a wall!");
									}
								}else{
									if(!choice.equals("forward") || !choice.equals("backward") || !choice.equals("right") || !choice.equals("left")){
										corr = false;
									}
								}
							}
						}
					}
				}catch(InputMismatchException e){
					in.nextLine();
				}
			}
			
			playerx = tempx;
			playery = tempy;

			ray[tempx][tempy].setplayer(true);
			if(fbow){
				ray[tempx][tempy].setbow(true);
			}

			if(ray[tempx][tempy].getpit() == true){
				System.out.println("You fell in a pit and died!");
				endgame = true;
				incorr = true;
				makeSound(false);
			}else{
				if(ray[tempx][tempy].getwumpus() == true){
					if(fbow){
						System.out.println("You shot the Wumpus!");
						endgame = false;
						incorr = false;
						ray[tempx][tempy].setwumpus(false);
					}else{
						System.out.println("The Wumpus killed you!");
						endgame = true;
						incorr = true;
						makeSound(false);
					}
				}else{
					if(ray[tempx][tempy].getgold() == true){
						System.out.println("You found the gold! You win!");
						endgame = true;
						incorr = true;
						makeSound(false);
					}else{
						endgame = false;
						corr = false;
						incorr = false;
						makeSound(false);
					}
				}
			}
			
			while(!incorr){
				try{
					System.out.printf("Do you want to save your game? 0 for no and 1 for yes: ");
					savechoice = in.nextInt();
					if(savechoice == 0){
						incorr = true;
					}else{
						if(savechoice == 1){
							incorr = true;
							makeSound(false);
						}else{
							incorr = false;
						}
					}
				}catch(InputMismatchException e){
					in.nextLine();
				}
			}
			if(savechoice == 1){
				try{
					while(!exists){
						System.out.printf("What do you want to call your save folder?: ");
						filename = in.next();
						dir = new File(filename);
						if(dir.exists()){
							try{
								System.out.printf("File already exists do you want to overwrite? 0 for no 1 for yes: ");
								overw = in.nextInt();
								if(overw == 0){
									exists = false;
								}else{
									if(overw == 1){
										exists = true;
										//dir.mkdir();
									}else{
										exists = false;
									}
								}
							}catch(InputMismatchException e){
								exists = false;
								in.nextLine();
							}
						}else{
							exists = true;
							dir.mkdir();
						}
					}
					makeSound(false);
					System.out.println("Saving...");
					try{
						Thread.sleep(1000);
					}catch(Exception e){}
					fileoutput = new File(filename, "/wumpus.txt");
					writer = new PrintWriter(fileoutput);
					for(int h = 0; h < ray.length; h++){
						for(int k = 0; k < ray.length; k++){
							writer.println(ray[h][k].getwumpus());
						}
					}
					writer.close();
					fileoutput2 = new File(filename, "/gold.txt");
					writer2 = new PrintWriter(fileoutput2);
					for(int r = 0; r < ray.length; r++){
						for(int t = 0; t < ray.length; t++){
							writer2.println(ray[r][t].getgold());
						}
					}
					writer2.close();
					fileoutput3 = new File(filename, "/pit.txt");
					writer3 = new PrintWriter(fileoutput3);
					for(int b = 0; b < ray.length; b++){
						for(int v = 0; v < ray.length; v++){
							writer3.println(ray[b][v].getpit());
						}
					}
					writer3.close();
					fileoutput4 = new File(filename, "/smell.txt");
					writer4 = new PrintWriter(fileoutput4);
					for(int p = 0; p < ray.length; p++){
						for(int o = 0; o < ray.length; o++){
							writer4.println(ray[p][o].getsmell());
						}
					}
					writer4.close();
					fileoutput5 = new File(filename, "/breeze.txt");
					writer5 = new PrintWriter(fileoutput5);
					for(int f = 0; f < ray.length; f++){
						for(int g = 0; g < ray.length; g++){
							writer5.println(ray[f][g].getbreeze());
						}
					}
					writer5.close();
					fileoutput6 = new File(filename, "/glimmer.txt");
					writer6 = new PrintWriter(fileoutput6);
					for(int q = 0; q < ray.length; q++){
						for(int w = 0; w < ray.length; w++){
							writer6.println(ray[q][w].getglimmer());
						}
					}
					writer6.close();
					fileoutput7 = new File(filename, "/player.txt");
					writer7 = new PrintWriter(fileoutput7);
					for(int l = 0; l < ray.length; l++){
						for(int m = 0; m < ray.length; m++){
							writer7.println(ray[l][m].getplayer());
						}
					}
					writer7.close();
					fileoutput8 = new File(filename, "/sizeofmap.txt");
					writer8 = new PrintWriter(fileoutput8);
					writer8.println(size);
					writer8.close();
					fileoutput9 = new File(filename, "/loc.txt");
					writer9 = new PrintWriter(fileoutput9);
					writer9.println(tempx);
					writer9.println(tempy);
					writer9.close();
					fileoutput10 = new File(filename, "/bow.txt");
					writer10 = new PrintWriter(fileoutput10);
					for(int d = 0; d < ray.length; d++){
						for(int u = 0; u < ray.length; u++){
							writer10.println(ray[d][u].getbow());
						}
					}
					writer10.close();
					makeSound(false);
					endgame = true;
					//makeSound(false);
				}catch(FileNotFoundException e){
					System.out.println("you shouldn't see this");
				}
			}

		}
	}
	
	public static void setPit(cell[][] ray, int gx, int gy, int size, int bx, int by){
		int x = 0;
		int y = 0;
		int px = 0;
		int py = 0;
		int count;
		boolean valid = false;
		Random r = new Random();
		
		count = (int) ((size * size) * .2);
		
		for(int pits = 0; pits < count; pits++){
			x = r.nextInt(size);
			y = r.nextInt(size);
			while(!valid){
				if((gx == x) && (gy == y)){
					x = r.nextInt(size);
					y = r.nextInt(size);
					valid = false;
				}else{
					if((x == 0) && (y == 0)){
						x = r.nextInt(size);
						y = r.nextInt(size);
						valid = false;
					}else{
						if((x == bx) && (y == by)){
							x = r.nextInt(size);
							y = r.nextInt(size);
							valid = false;
						}else{
							px = x;
							py = y;
							valid = true;
						}
					}
				}
			}

			ray[px][py].setpit();

			if(px - 1 >= 0){
				ray[px - 1][py].setbreeze();
			}
			if(px + 1 < size){
				ray[px + 1][py].setbreeze();
			}
			if(py - 1 >= 0){
				ray[px][py - 1].setbreeze();
			}
			if(py + 1 < size){
				ray[px][py + 1].setbreeze();
			}
			valid = false;
		}
	}
	
	public static void loadGame(){
		File fileinput;
		int size = 0;
		int tempx = 0;
		int tempy = 0;
		boolean found = false;
		Scanner textin = new Scanner(System.in);
		String name = "";
		System.out.println("Loading...");
		Scanner reader;

		while(!found){
			try{
				System.out.printf("Please enter file name: ");
				name = textin.next();
				fileinput = new File(name, "/sizeofmap.txt");
				reader = new Scanner(fileinput);
				size = reader.nextInt();
				found = true;
			}catch(FileNotFoundException e){
				found = false;
				System.out.println("File not found");
				textin.nextLine();
			}catch(InputMismatchException e){
				found = false;
				textin.nextLine();
			}
		}
		try{
			fileinput = new File(name, "/loc.txt");
			reader = new Scanner(fileinput);
			int loc[] = new int[2];
			while(reader.hasNext()){
				loc[0] = reader.nextInt();
				loc[1] = reader.nextInt();
			}
			tempx = loc[0];
			//System.out.println("x " + tempx);
			tempy = loc[1];
			//System.out.println("y " + tempy);
		}catch(FileNotFoundException e){
			System.out.println("You shouldn't see this");
		}
		boolean temp[][] = new boolean[size][size];
		cell ray[][] = new cell [size][size];
		for(int j = 0; j < size; j++){
			for(int z = 0; z < size; z++){
				ray[j][z] = new cell();
			}
		}
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		try{
			fileinput = new File(name,"/wumpus.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					ray[n][y].setwumpus(temp[n][y]);
				}
			}
			fileinput = new File(name, "/gold.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					if(temp[n][y] == true){
						ray[n][y].setgold();
					}
				}
			}
			fileinput = new File(name, "/pit.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					if(temp[n][y] == true){
						ray[n][y].setpit();
					}
				}
			}
			fileinput = new File(name, "/breeze.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					if(temp[n][y] == true){
						ray[n][y].setbreeze();
					}
				}
			}
			fileinput = new File(name, "/smell.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					if(temp[n][y] == true){
						ray[n][y].setsmell();
					}
				}
			}
			fileinput = new File(name, "/glimmer.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					if(temp[n][y] == true){
						ray[n][y].setglimmer();
					}
				}
			}
			fileinput = new File(name, "/player.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					ray[n][y].setplayer(temp[n][y]);
				}
			}
			fileinput = new File(name, "/bow.txt");
			reader = new Scanner(fileinput);
			for(int n = 0; n < ray.length; n++){
				for(int y = 0; y < ray.length; y++){
					temp[n][y] = reader.nextBoolean();
					ray[n][y].setbow(temp[n][y]);
				}
			}
			playGame(ray, size, tempx, tempy);
		}catch(FileNotFoundException e){
			System.out.println("you shouldn't see this");
		}
		
	}
	
	public static void makeSound(boolean stop){
		try{
			File audiofile = new File("theme.au");
			AudioInputStream audiostream = AudioSystem.getAudioInputStream(audiofile);
			AudioFormat format = audiostream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip audioclip = (Clip) AudioSystem.getLine(info);
			
			if(stop){
				audioclip.open(audiostream);
				audioclip.start();
				audioclip.loop(Clip.LOOP_CONTINUOUSLY);
			}else{
				audioclip.stop();
				audioclip.close();
				audiostream.close();
			}
		}catch(UnsupportedAudioFileException e){
			System.out.println("you shouldn't see this");
		}catch(LineUnavailableException e){
			System.out.println("you shouldn't see this");
		}catch(IOException e){
			System.out.println("you shouldn't see this");
		}
	}

}
