import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.Map;
import java.util.Map.Entry;
  
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 



public class Main{ 
	static ArrayList<property> propertyObjects = new ArrayList<property>();
	static ArrayList<String> chanceCards = new ArrayList<String>();
	static ArrayList<String> communityCards = new ArrayList<String>();
	static ArrayList<players> playerObjects = new ArrayList<players>();
	
	
	static int chanceCardCounter=0;
	static int communityCardCounter=0;
	static int lastDicee=0;
	
	private static void DosyayaEkle(String metin){
        try{
              File output = new File("output.txt");
              FileWriter yazici = new FileWriter(output,true);
              BufferedWriter yaz = new BufferedWriter(yazici);
              yaz.write(metin);
              yaz.close();
        }
        catch (Exception hata){
              hata.printStackTrace();
        }
  }

	public static void propertyjsonread(JSONArray propertyJSONArray, String tempKindOfProperty) {
		Iterator itr2 = propertyJSONArray.iterator(); 

        while (itr2.hasNext()){
            
            int tempId= 0;
            String tempName= null;
            int tempCost= 0;

            Iterator itr1 = ((Map) itr2.next()).entrySet().iterator(); 

            while (itr1.hasNext()) {
                Map.Entry pair = (Entry) itr1.next();
                if(pair.getKey().equals("id")) {
                	tempId= Integer.parseInt(pair.getValue().toString());
                }
                if(pair.getKey().equals("name")) {
                	tempName= pair.getValue().toString();
                }
                if(pair.getKey().equals("cost")) {
                	tempCost= Integer.parseInt(pair.getValue().toString());
                }
            }
            
            property temp = new property(tempId,tempName,tempCost,tempKindOfProperty);
			
			propertyObjects.add(temp);
                        
        }
	}
	public static void listjsonread(JSONArray listJSONArray, ArrayList cardsList) {
		int cardCounter=0;
		
		Iterator itr2 = listJSONArray.iterator(); 
        
        while (itr2.hasNext()){
            Iterator itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = (Entry) itr1.next();
                cardsList.add(pair.getValue());
            }
        }
        
	}
	public static void chanceMethod(players player, account bank, int dice, String[] squares, players player1, players player2) {
		int rent=0;
		int p;
		int l;
		
		
		if(chanceCardCounter==0) {
			
			player.setWhichSquare(1);
			bank.setMoney(bank.getMoney()-200);
			player.setMoney(player.getMoney()+200);
			chanceCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" advance to go (collect 200)\n");
		}
		else if(chanceCardCounter==1) {
			player.setWhichSquare(27);
			for(p=1;p<dice+1;p++) {
    			if(squares[(player.getWhichSquare()+p)%40]=="go"){
    				player.setMoney(player.getMoney()+200);
    				bank.setMoney(bank.getMoney()-200);
    			}
    		}
			
			for(p=0;p<propertyObjects.size();p++) {
				if(propertyObjects.get(p).getName().equals("Leicester Square")) {
					if(propertyObjects.get(p).getWhoOwnThisProperty()==0) {   
						if(player.getMoney()>=propertyObjects.get(p).getCost()) {
							player.getOwnProperty().add(propertyObjects.get(p));							
							propertyObjects.get(p).setWhoOwnThisProperty(player.getPlayerID());
							player.setMoney(player.getMoney()-propertyObjects.get(p).getCost());
							bank.setMoney(bank.getMoney()+propertyObjects.get(p).getCost());
							DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
				    				+"Player "+player.getPlayerID()+" draw Advance to Leicester Square Player"
									+player.getPlayerID()+"bought Leicester Square"+"\n");
						}
						else {
							player.setHeBankrupt(true);
						
						}
					}
					else if(propertyObjects.get(p).getWhoOwnThisProperty()!=0 && propertyObjects.get(p).getWhoOwnThisProperty()!=player.getPlayerID()){  
							for(l=0;l<playerObjects.size();l++) {
								if(playerObjects.get(l).getPlayerID()!=player.getPlayerID()) {
									rent= (propertyObjects.get(p).getCost()/10)*3;
									if(player.getMoney()>=rent) {	
										player.setMoney(player.getMoney()-rent);
										playerObjects.get(l).setMoney(playerObjects.get(l).getMoney()+rent);
										DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
							    				+"Player "+player.getPlayerID()+" draw Advance to Leicester Square Player"
												+player.getPlayerID()+" draw Advance to Leicester Square Player "+player.getPlayerID()+"paid rent for Leicester Square"+"\n");
									}
									else {
										player.setHeBankrupt(true);
										
									}
								}
							}					
					}	
				}
			}
			chanceCardCounter++;
		}
		else if(chanceCardCounter==2) {
			if(player.getWhichSquare()==8) {
				player.setWhichSquare(5);
				if(player.getMoney()>=100) {
					player.setMoney(player.getMoney()-100);
					bank.setMoney(bank.getMoney()+100);
					DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
		    				+"Player "+player.getPlayerID()+" draw Go back 3 spaces Player"+player.getPlayerID()+"paid tax"+"\n");
				}
				else {
					player.setHeBankrupt(true);
					
				}
			}
			else if(player.getWhichSquare()==23) {
				player.setWhichSquare(20);
				for(p=0;p<propertyObjects.size();p++) {
					if(propertyObjects.get(p).getName().equals("Vine Street")) {
						if(propertyObjects.get(p).getWhoOwnThisProperty()==0) {
							if(player.getMoney()>=propertyObjects.get(p).getCost()) {
								player.getOwnProperty().add(propertyObjects.get(p));							
								propertyObjects.get(p).setWhoOwnThisProperty(player.getPlayerID());
								player.setMoney(player.getMoney()-propertyObjects.get(p).getCost());
								bank.setMoney(bank.getMoney()+propertyObjects.get(p).getCost());
								DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
					    				+"Player "+player.getPlayerID()+" draw Go back 3 spaces Player"+player.getPlayerID()+"bought Vine Street"+"\n");
							}
							else {
								player.setHeBankrupt(true);
								
							}
						}
						else if(propertyObjects.get(p).getWhoOwnThisProperty()!=0 && propertyObjects.get(p).getWhoOwnThisProperty()!=player.getPlayerID()){  
								for(l=0;l<playerObjects.size();l++) {
									if(playerObjects.get(l).getPlayerID()!=player.getPlayerID()) {
										rent= (propertyObjects.get(p).getCost()/10)*3;
										if(player.getMoney()>=rent) {	
											player.setMoney(player.getMoney()-rent);
											playerObjects.get(l).setMoney(playerObjects.get(l).getMoney()+rent);
											DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
								    				+"Player "+player.getPlayerID()+" draw Go back 3 spaces Player"+player.getPlayerID()+"paid rent for Vine Street"+"\n");
										}
										else {
											player.setHeBankrupt(true);
											
										}
									}
								}
							
						}	
						else {
							DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
				    				+"Player "+player.getPlayerID()+" draw Go back 3 spaces Player"+player.getPlayerID()+"has Vine Street"+"\n");
						}
					}
				}
			}
			else if(player.getWhichSquare()==37) {
				player.setWhichSquare(34);
				chanceCardCounter++;
				communityChestMethod(player, bank, dice, player1, player2);
			}
		chanceCardCounter++;
		}
		else if(chanceCardCounter==3) {
			if(player.getMoney()>=15) {
				player.setMoney(player.getMoney()-15);
				bank.setMoney(bank.getMoney()+15);
				DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	    				+"Player "+player.getPlayerID()+" draw Pay poor tax of $15\n");			}
			else {
				player.setHeBankrupt(true);
				
			}
			chanceCardCounter++;
		}
		else if(chanceCardCounter==4) {
			
			player.setMoney(player.getMoney()+150);
			bank.setMoney(bank.getMoney()-150);
			chanceCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw Your building loan matures - collect $150\n");
		}
		else if(chanceCardCounter==5) {
			player.setMoney(player.getMoney()+100);
			bank.setMoney(bank.getMoney()-100);
			chanceCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw You have won a crossword competition - collect $100 \n");
		}
	}
	
	private static void communityChestMethod(players player, account bank,int dice,players player1, players player2) {
		int b;
		if(communityCardCounter==0) {
			player.setWhichSquare(1);
			bank.setMoney(bank.getMoney()-200);
			player.setMoney(player.getMoney()+200);
			communityCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw Community Chest -advance to go\n");
		}
		else if(communityCardCounter==1) {
			player.setMoney(player.getMoney()+75);
			bank.setMoney(bank.getMoney()-75);
			communityCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw Bank error in your favor - collect $75\n");
		}
		else if(communityCardCounter==2) {
			if(player.getMoney()>=50) {
				player.setMoney(player.getMoney()-50);
				bank.setMoney(bank.getMoney()+50);
				communityCardCounter++;
				DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	    				+"Player "+player.getPlayerID()+" draw Doctor's fees - Pay $50\n");
			}
			else {
				player.setHeBankrupt(true);
				

			}
		}
		else if(communityCardCounter==3) {
			for(b=0;b<playerObjects.size();b++) {
				if(playerObjects.get(b).getPlayerID()!=player.getPlayerID()) {
					if(playerObjects.get(b).getMoney()>=10) {	
						playerObjects.get(b).setMoney(playerObjects.get(b).getMoney()-10);
						player.setMoney(player.getMoney()+10);
						communityCardCounter++;
						DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
			    				+"Player "+player.getPlayerID()+" draw It is your birthday Collect $10 from each player\n");
					}
					else {
						playerObjects.get(b).setHeBankrupt(true);
										

					}
				}
			}
		}
		else if(communityCardCounter==4) {
			for(b=0;b<playerObjects.size();b++) {
				if(playerObjects.get(b).getPlayerID()!=player.getPlayerID()) {
					if(playerObjects.get(b).getMoney()>=50) {	
						playerObjects.get(b).setMoney(playerObjects.get(b).getMoney()-50);
						player.setMoney(player.getMoney()+50);
						communityCardCounter++;
						DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
			    				+"Player "+player.getPlayerID()+" draw Grand Opera Night - collect $50 from every player for opening night seats\n");
					}
					else {
						playerObjects.get(b).setHeBankrupt(true);
					}
				}
			}
		}
		else if(communityCardCounter==5) {
			player.setMoney(player.getMoney()+20);
			bank.setMoney(bank.getMoney()-20);
			communityCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw Income Tax refund - collect $20\n");
		}
		else if(communityCardCounter==6) {
			player.setMoney(player.getMoney()+100);
			bank.setMoney(bank.getMoney()-100);
			communityCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw Life Insurance Matures - collect $100\n");
		}
		else if(communityCardCounter==7) {
			if(player.getMoney()>=100) {
				player.setMoney(player.getMoney()-100);
				bank.setMoney(bank.getMoney()+100);
				communityCardCounter++;
				DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	    				+"Player "+player.getPlayerID()+" draw Pay Hospital Fees of $100\n");
			}
			else {
				player.setHeBankrupt(true);
				
			}
		}
		else if(communityCardCounter==8) {
			if(player.getMoney()>=50) {
				player.setMoney(player.getMoney()-50);
				bank.setMoney(bank.getMoney()+50);
				communityCardCounter++;
				DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	    				+"Player "+player.getPlayerID()+" draw Pay School Fees of $50\n");
			}
			else {
				player.setHeBankrupt(true);
				
			}
		}
		else if(communityCardCounter==9) {
			player.setMoney(player.getMoney()+100);
			bank.setMoney(bank.getMoney()-100);
			communityCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw You inherit $100\n");
		}
		else if(communityCardCounter==10) {
			player.setMoney(player.getMoney()+50);
			bank.setMoney(bank.getMoney()-50);
			communityCardCounter++;
			DosyayaEkle("Player "+player.getPlayerID()+"	"+dice+"	"+player.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    				+"Player "+player.getPlayerID()+" draw From sale of stock you get $50\n");
		}		
	}
	

	
    public static void main(String[] args) throws Exception{
    	
    	int xFor;
    	int yFor;
    	int rent1;
    	
    	
    	try{
            File output = new File("output.txt");
            FileWriter yazici = new FileWriter(output,false);
            BufferedWriter yaz = new BufferedWriter(yazici);
            yaz.write("");
            yaz.close();
    	}
    	catch (Exception hata){
            hata.printStackTrace();
    	}
    	
    	
    	
    	Object propertyJSON = new JSONParser().parse(new FileReader("property.json"));            
        JSONObject propertyJSONObject = (JSONObject) propertyJSON;         
        JSONArray propertyJSONArray1 = (JSONArray) propertyJSONObject.get("1");
        JSONArray propertyJSONArray2 = (JSONArray) propertyJSONObject.get("2");
        JSONArray propertyJSONArray3 = (JSONArray) propertyJSONObject.get("3");
        
        propertyjsonread(propertyJSONArray1,"1");
        
        propertyjsonread(propertyJSONArray2,"2");
        
        propertyjsonread(propertyJSONArray3,"3");
        
        Object listJSON = new JSONParser().parse(new FileReader("list.json")); 
        JSONObject listJSONObject = (JSONObject) listJSON;         
        JSONArray listJSONArray1 = (JSONArray) listJSONObject.get("chanceList"); 
        JSONArray listJSONArray2 = (JSONArray) listJSONObject.get("communityChestList"); 
        
        listjsonread(listJSONArray1,chanceCards);
        listjsonread(listJSONArray2,communityCards);
        
        
        account banker= new account(100000);
        players player1= new players(15000, 1, 1);
        players player2= new players(15000, 2, 1);
        playerObjects.add(player1);
        playerObjects.add(player2);
        
        String squares[] = new String [41];
        
        for(xFor=0;xFor<propertyObjects.size();xFor++) {
        	squares[propertyObjects.get(xFor).getId()]= propertyObjects.get(xFor).getName();
        }
        
        squares[1]= "go";
        squares[3]= "community chest";
        squares[18]= "community chest";
        squares[34]= "community chest";
        squares[8]= "chance";
        squares[23]= "chance";
        squares[37]= "chance";
        squares[11]= "jail";
        squares[31]= "go to jail";
        squares[21]= "free parking";
        squares[5]= "tax square";
        squares[39]= "tax square";
        
        
        
        FileReader fileReader = new FileReader(args[0]);
        String commandLine;

        BufferedReader commands = new BufferedReader(fileReader);
        
        
        boolean bankrupted=false;
        
        
        while ((commandLine = commands.readLine()) != null) {
        	
        	String commandLineFeatures[]= commandLine.split(" ");
        	
        	if(player1.isHeBankrupt() || player2.isHeBankrupt()) {
        		
        		bankrupted=true;
        		String movementFeatures1[]= commandLineFeatures[1].split(";");
        		
        		
        		if(player1.isHeBankrupt()==true) {
        		
        			DosyayaEkle("Player "+player1.getPlayerID()+"	"+movementFeatures1[1]+"	"+player1.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    	    				+"Player "+player1.getPlayerID()+" goes bankrupt\n");
        			break;
        		}
        		else if(player2.isHeBankrupt()==true) {
        		
        			DosyayaEkle("Player "+player2.getPlayerID()+"	"+movementFeatures1[1]+"	"+player2.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    	    				+"Player "+player2.getPlayerID()+" goes bankrupt\n");
        			break;
        		}
        		
        	}
        	
        	
        	       	
        	else if(commandLineFeatures[0].equals("Player")) {
        		String movementFeatures[]= commandLineFeatures[1].split(";");
        		lastDicee= Integer.parseInt(movementFeatures[1]);
        		players tempCurrentPlayer = null;
        		if(movementFeatures[0].equals("1")) {
        			
        			tempCurrentPlayer= player1;
        		}
        		else if(movementFeatures[0].equals("2")) {
        			
        			tempCurrentPlayer= player2;
        		}
        		

        		if(tempCurrentPlayer.getPunishment()==0) {
        			
        			for(xFor=1;xFor<Integer.parseInt(movementFeatures[1])+1;xFor++) {
            			if(squares[(tempCurrentPlayer.getWhichSquare()+Integer.parseInt(movementFeatures[1]))%40]=="jail") {
            				break;
            			}
            			else if(squares[(tempCurrentPlayer.getWhichSquare()+xFor)%40]=="go"){
            				tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()+200);
            				banker.setMoney(banker.getMoney()-200);
            			}
            		}
        			
            		tempCurrentPlayer.setWhichSquare(tempCurrentPlayer.getWhichSquare()+Integer.parseInt(movementFeatures[1]));
            		
            		if(squares[tempCurrentPlayer.getWhichSquare()]=="go") {
            			DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        						+"Player "+tempCurrentPlayer.getPlayerID()+" is in GO square\n");
            		}
            		
            		else if(squares[tempCurrentPlayer.getWhichSquare()]=="jail") {
        				DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        						+"Player "+tempCurrentPlayer.getPlayerID()+" went to jail\n");
        				tempCurrentPlayer.setPunishment(3);
        			}
        			else if(squares[tempCurrentPlayer.getWhichSquare()]=="go to jail") {
        				DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        						+"Player "+tempCurrentPlayer.getPlayerID()+" went to jail\n");
        				tempCurrentPlayer.setWhichSquare(11);
        				tempCurrentPlayer.setPunishment(3);
        			}
        			else if(squares[tempCurrentPlayer.getWhichSquare()]=="tax square") {
	        			if(tempCurrentPlayer.getMoney()>=100) {	
        					tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-100);
	        				banker.setMoney(banker.getMoney()+100);
	        				DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	    	        				+"Player "+tempCurrentPlayer.getPlayerID()+" paid tax\n");
	        			}
	        			else {
	        				tempCurrentPlayer.setHeBankrupt(true);
	        				
	        			}
        			}
        			else if(squares[tempCurrentPlayer.getWhichSquare()]=="free parking") {
        				tempCurrentPlayer.setPunishment(1);
        				DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        				+"Player "+tempCurrentPlayer.getPlayerID()+" is in free parking\n");
        			}
        			else if(squares[tempCurrentPlayer.getWhichSquare()]=="chance") {
        				if(chanceCardCounter==6) {
        					chanceCardCounter=0;
        				}
        				chanceMethod(tempCurrentPlayer,banker,Integer.parseInt(movementFeatures[1]),squares,player1,player2);
        				       					
        			}
        			else if(squares[tempCurrentPlayer.getWhichSquare()]=="community chest") {
        				if(communityCardCounter==6) {
        					communityCardCounter=0;
        				}
        				communityChestMethod(tempCurrentPlayer,banker,Integer.parseInt(movementFeatures[1]), player1, player2);
        			}
        			else if(squares[tempCurrentPlayer.getWhichSquare()]!=null) {
        				for(xFor=0;xFor<propertyObjects.size();xFor++) {
        					if(squares[tempCurrentPlayer.getWhichSquare()].equals(propertyObjects.get(xFor).getName())) {
        						if(propertyObjects.get(xFor).getWhoOwnThisProperty()==0) {			
        							if(tempCurrentPlayer.getMoney()>=propertyObjects.get(xFor).getCost()) {
        								tempCurrentPlayer.getOwnProperty().add(propertyObjects.get(xFor));
        								propertyObjects.get(xFor).setWhoOwnThisProperty(tempCurrentPlayer.getPlayerID());
        								tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-propertyObjects.get(xFor).getCost());
        								banker.setMoney(banker.getMoney()+propertyObjects.get(xFor).getCost());
        								DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        					    				+"Player "+tempCurrentPlayer.getPlayerID()+" bought "+propertyObjects.get(xFor).getName()+"\n");
        							}
        							else {
        		        				tempCurrentPlayer.setHeBankrupt(true);
        		        				
        							}
        						}
        						else if(propertyObjects.get(xFor).getWhoOwnThisProperty()!=0 && propertyObjects.get(xFor).getWhoOwnThisProperty()!=tempCurrentPlayer.getPlayerID()) { 	
        							for(yFor=0;yFor<playerObjects.size();yFor++) {
        								if(playerObjects.get(yFor).getPlayerID()!=tempCurrentPlayer.getPlayerID()) {
        									if(propertyObjects.get(xFor).getWhatKindProperty().equals("1")) {
	        									if(propertyObjects.get(xFor).getCost()<2000) {
	        										rent1= (propertyObjects.get(xFor).getCost()/10)*4;
	        										if(tempCurrentPlayer.getMoney()>=rent1) {
	        											tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-rent1);
	        											playerObjects.get(yFor).setMoney(playerObjects.get(yFor).getMoney()+rent1);
	        											DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	        	        					    				+"Player "+tempCurrentPlayer.getPlayerID()+" paid rent for "+propertyObjects.get(xFor).getName()+"\n");
	        										}
	        										else {
	        					        				tempCurrentPlayer.setHeBankrupt(true);
	        					        				
	        										}
	        									}
	        									else if(propertyObjects.get(xFor).getCost()<3000) {
        											rent1= (propertyObjects.get(xFor).getCost()/10)*3;
	        										if(tempCurrentPlayer.getMoney()>=rent1) {
	        											tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-rent1);
	        											playerObjects.get(yFor).setMoney(playerObjects.get(yFor).getMoney()+rent1);
	        											DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	        	        					    				+"Player "+tempCurrentPlayer.getPlayerID()+" paid rent for "+propertyObjects.get(xFor).getName()+"\n");
	        										}
	        										else {
	        					        				tempCurrentPlayer.setHeBankrupt(true);
	        					        				
	        										}
	        									}
	        									else if(propertyObjects.get(xFor).getCost()<4000) {
	        										rent1= (propertyObjects.get(xFor).getCost()/100)*35;
	        										if(tempCurrentPlayer.getMoney()>=rent1) {
	        											tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-rent1);
	        											playerObjects.get(yFor).setMoney(playerObjects.get(yFor).getMoney()+rent1);
	        											DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
	        	        					    				+"Player "+tempCurrentPlayer.getPlayerID()+" paid rent for "+propertyObjects.get(xFor).getName()+"\n");
	        										}
	        										else {
	        					        				tempCurrentPlayer.setHeBankrupt(true);
	        					        				
	        										}
	        									}
        									}
        									else if(propertyObjects.get(xFor).getWhatKindProperty().equals("2")) {
        										rent1= Integer.parseInt(movementFeatures[1])*4;
        										if(tempCurrentPlayer.getMoney()>=rent1) {
        											tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-rent1);
        											playerObjects.get(yFor).setMoney(playerObjects.get(yFor).getMoney()+rent1);
        											DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        	        					    				+"Player "+tempCurrentPlayer.getPlayerID()+" paid rent for "+propertyObjects.get(xFor).getName()+"\n");
        										}
        										else {
        					        				tempCurrentPlayer.setHeBankrupt(true);
        					        				
        										}
        									}
        									else if(propertyObjects.get(xFor).getWhatKindProperty().equals("3")) {
        										int y;
        										int railroadCounter=0;
        										for(y=0;y<playerObjects.get(yFor).getOwnProperty().size();y++) {
        											if(playerObjects.get(yFor).getOwnProperty().get(y).getWhatKindProperty().equals("3")){
        												railroadCounter++;
        											}
        										}
        										rent1=railroadCounter*25;
        										if(tempCurrentPlayer.getMoney()>=rent1) {
        											tempCurrentPlayer.setMoney(tempCurrentPlayer.getMoney()-rent1);
        											playerObjects.get(yFor).setMoney(playerObjects.get(yFor).getMoney()+rent1);
        											DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
        	        					    				+"Player "+tempCurrentPlayer.getPlayerID()+" paid rent for "+propertyObjects.get(xFor).getName()+"\n");
        										}
        										else {
        					        				tempCurrentPlayer.setHeBankrupt(true);
        					        				
        										}
        									}
        								}
        							}
        						}
        						else {
        							DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
    					    				+"Player "+tempCurrentPlayer.getPlayerID()+" has "+propertyObjects.get(xFor).getName()+"\n");
        						}
        					}
        				}
        			}
        			
        			
        		}
        		else if(tempCurrentPlayer.getPunishment()>0) {
        			DosyayaEkle("Player "+tempCurrentPlayer.getPlayerID()+"	"+movementFeatures[1]+"	"+tempCurrentPlayer.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()
        			+"	"+"Player "+tempCurrentPlayer.getPlayerID()+" in jail (count="+(4-tempCurrentPlayer.getPunishment())+")\n");
        			
        			tempCurrentPlayer.setPunishment(tempCurrentPlayer.getPunishment()-1);
        		}
        		
        		
        	}
        	else if(commandLineFeatures[0].equals("show()")) {
        		int counter1;
        		int counter2;
        		DosyayaEkle("-------------------------------------------------------------------------------------------------------------------------\n");
        		DosyayaEkle("Player "+player1.getPlayerID()+"	"+player1.getMoney()+"	have: ");
        		for(counter1=0;counter1<player1.getOwnProperty().size();counter1++) {
        			DosyayaEkle(player1.getOwnProperty().get(counter1).getName()+", ");
        		}
        		DosyayaEkle("\nPlayer "+player2.getPlayerID()+"	"+player2.getMoney()+"	have: ");
        		for(counter2=0;counter2<player2.getOwnProperty().size();counter2++) {
        			DosyayaEkle(player2.getOwnProperty().get(counter2).getName()+", ");
        		}
        		
        		DosyayaEkle("\nBanker "+ banker.getMoney()+"\n");
        		if(player1.getMoney()>player2.getMoney()) {
        			DosyayaEkle("Winner Player "+player1.getPlayerID()+"\n");
        		}
        		else {
        			DosyayaEkle("Winner Player "+player2.getPlayerID()+"\n");
        		}
        		DosyayaEkle("-------------------------------------------------------------------------------------------------------------------------\n");
        	}
        	
        	
        }
        if(bankrupted==false) {
        	if(player1.isHeBankrupt() || player2.isHeBankrupt()) {
	    		
	    		if(player1.isHeBankrupt()==true) {
	    		
	    			DosyayaEkle("Player "+player1.getPlayerID()+"	"+"	"+player1.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
		    				+"Player "+player1.getPlayerID()+" goes bankrupt\n");
	    			
	    		}
	    		else if(player2.isHeBankrupt()==true) {
	    		
	    			DosyayaEkle("Player "+player2.getPlayerID()+"	"+lastDicee+"	"+player2.getWhichSquare()+"	"+player1.getMoney()+"	"+player2.getMoney()+"	"
		    				+"Player "+player2.getPlayerID()+" goes bankrupt\n");
	    			
	    		}
	    		
	    	}
        }
        int last1;
        int last2;
        DosyayaEkle("-------------------------------------------------------------------------------------------------------------------------\n");
		DosyayaEkle("Player "+player1.getPlayerID()+"	"+player1.getMoney()+"	have: ");
		for(last1=0;last1<player1.getOwnProperty().size();last1++) {
			DosyayaEkle(player1.getOwnProperty().get(last1).getName()+", ");
		}
		DosyayaEkle("\nPlayer "+player2.getPlayerID()+"	"+player2.getMoney()+"	have: ");
		for(last2=0;last2<player2.getOwnProperty().size();last2++) {
			DosyayaEkle(player2.getOwnProperty().get(last2).getName()+", ");
		}
		
		DosyayaEkle("\nBanker "+ banker.getMoney()+"\n");
		if(player1.getMoney()>player2.getMoney()) {
			DosyayaEkle("Winner Player "+player1.getPlayerID()+"\n");
		}
		else if(player2.getMoney()>player1.getMoney()){
			DosyayaEkle("Winner Player "+player2.getPlayerID()+"\n");
		}
		else if(player1.getMoney()==player2.getMoney()) {
			DosyayaEkle("Scoreless \n");
		}
		DosyayaEkle("-------------------------------------------------------------------------------------------------------------------------\n");

        commands.close();
    }
    
}