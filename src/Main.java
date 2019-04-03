
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class Main extends JPanel {

    public static final int BREAKFASTTIME = 0;
    public static final int LUNCHTIME = 1;
    public static final int DINNERTIME = 2;
    public static final int BRUNCHTIME = 3;
    public int fuzzyTime = -1;
    public int oldFuzzyTime = -1;
    public boolean raphtailiaTime = false;
    public int eggTimeCounter[] = {0,0,0,0};
    public boolean eggTime = false;
    String currentTime = " ";
    String currentTime2 = " ";
    String day = " ";    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image raphitaliaFingerSpin = toolkit.getImage("51b.gif");
    Image raphitaliaPie = toolkit.getImage("large.jpg");
    String title = "BoneMenu";
    ArrayList<String> global= new ArrayList<String>();
    ArrayList<String> roots= new ArrayList<String>();
    ArrayList<String> sizzle= new ArrayList<String>();
    ArrayList<String> pomodoro= new ArrayList<String>();
    ArrayList<String> rise= new ArrayList<String>();
    ArrayList<String> rosies= new ArrayList<String>();
    String menuSearch = null;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, 1366, 768);
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
		g2.drawString(this.day+":  "+this.currentTime2, 850, 50);
		checkRaphtailiaTime(g2);
		if(this.checkForEggTime(g2)!=true){
			g2.setColor(Color.LIGHT_GRAY);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g2.drawString(this.getFuzzyTime(), 475, 80);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
			g2.drawString("Menu:", 600, 125);
			
			
			drawSection(g2, global, 5);
			drawSection(g2, roots, 200);
			drawSection(g2, sizzle, 400);
			drawSection(g2, pomodoro, 650);
			drawSection(g2, rise, 900);
			drawSection(g2, rosies, 1150);
			
		}
	}
	public void drawSection(Graphics2D g2, ArrayList<String> section, int x) {
		for(int i=0;i<section.size();i++){
			if(i==0) {
				g2.setFont(new Font("TimesRoman", Font.BOLD, 32));
				g2.drawString(section.get(i)+":", x, 200);
			}
			else{
				if(i<27) {
					g2.setFont(new Font("TimesRoman", Font.PLAIN, 18));
					g2.drawString(section.get(i), x, 205+20*(i));
				}
				else {
					g2.setFont(new Font("TimesRoman", Font.PLAIN, 18));
					g2.drawString(section.get(i), x+100, 205+20*(i-26));
				}
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("What's at the Bone ¯\\_(ツ)_/¯");
		Main window = new Main();
		frame.add(window);
		frame.setSize(1366, 768);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while (true) {
			window.updateTime();
			
			window.repaint();
			Thread.sleep(10);
		}
	}
	public void checkRaphtailiaTime(Graphics2D g2){
		if(this.raphtailiaTime){
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
			g2.setColor(Color.WHITE);
			g2.drawImage(raphitaliaFingerSpin, 0, 150, this);
			g2.drawImage(raphitaliaPie, 700, 0, this);
			g2.drawString("Raphtailia Time!", 300, 700);
		}
	}
	public boolean checkForEggTime(Graphics2D g2){
		boolean end = eggTime;
		for(int i=0;i<this.eggTimeCounter.length;i++){
			if(eggTimeCounter[i]!=0){
				end = true;
			}
		}
		if(end == true){
			g2.setColor(Color.WHITE);
			if(this.eggTime!=true) {
				int seconds = ((this.eggTimeCounter[0]*10+this.eggTimeCounter[1])*60)+(this.eggTimeCounter[2]*10+this.eggTimeCounter[3]);
				int countDown = 3600 - seconds;
                int numberOfMinutes = countDown/60; 
				int numberOfSeconds = countDown%60;
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
				g2.drawString("Egg Time Countdown: ", 200, 300);
				g2.drawString(numberOfMinutes+" : "+numberOfSeconds, 200, 450);
				return true;
			}
			else {
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 200));
				g2.drawString("EGG TIME!!", 50, 400);
				return true;
			}
		}
		return false;
	}
	public String getFuzzyTime(){
		if(this.fuzzyTime == BREAKFASTTIME){
			return "Breakfast Time";
		}
		else if(this.fuzzyTime == LUNCHTIME){
			return "Lunch Time";
		}
		else if(this.fuzzyTime == DINNERTIME){
			return "Dinner Time";
		}
		else if(this.fuzzyTime == BRUNCHTIME){
			return "Brunch Time";
		}
		return "";
	}
	public void updateTime(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.currentTime = dtf.format(now);
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM/dd/yyyy  hh:mm:ss a");
		this.currentTime2 = dtf2.format(now);
		
		Date date = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
		simpleDateformat = new SimpleDateFormat("EEEE");
		this.day = simpleDateformat.format(date);
		this.setTimeFuzzy(currentTime,day);
	}
	
	public void setTimeFuzzy(String time, String day){
    	if(day.equals("Wednesday")){
    		this.raphtailiaTime = true;
    	}
    	else {
    		this.raphtailiaTime = false;
    	}
    	if(day.equals("Wednesday") && Character.getNumericValue(time.charAt(11))==0&&Character.getNumericValue(time.charAt(12))==0){
			this.eggTimeCounter[0] = Character.getNumericValue(time.charAt(14));
			this.eggTimeCounter[1] = Character.getNumericValue(time.charAt(15));
			this.eggTimeCounter[2] = Character.getNumericValue(time.charAt(17));
			this.eggTimeCounter[3] = Character.getNumericValue(time.charAt(18));
    	}
    	else if(day.equals("Wednesday") && Character.getNumericValue(time.charAt(11))==0&&Character.getNumericValue(time.charAt(12))==1){
    		this.eggTime = true;
    		for(int i=0;i<this.eggTimeCounter.length;i++) {
    			this.eggTimeCounter[i]=0;
    		}
    	}
    	else{
    		for(int i=0;i<this.eggTimeCounter.length;i++) {
    			this.eggTimeCounter[i]=0;
    		}
    	}
    	if((Character.getNumericValue(time.charAt(11))==0&&Character.getNumericValue(time.charAt(12))==1)) {
    		setMenuNull();
    		parseMenu();
    	}
    	if(day.equals("Saturday") || (day.equals("Sunday") && (Character.getNumericValue(time.charAt(11))<=1&&Character.getNumericValue(time.charAt(12))<4))){
    		this.fuzzyTime=BRUNCHTIME;
    		if(this.oldFuzzyTime!=this.fuzzyTime) {
    			setMenuNull();
    			parseMenu();
    			this.oldFuzzyTime=this.fuzzyTime;
    		}
    	}
    	else{
    		if((Character.getNumericValue(time.charAt(11))==2&&Character.getNumericValue(time.charAt(12))>=0)||Character.getNumericValue(time.charAt(11))<1){
        		this.fuzzyTime=BREAKFASTTIME;
        		if(this.oldFuzzyTime!=this.fuzzyTime) {
        			setMenuNull();
        			parseMenu();
        			this.oldFuzzyTime=this.fuzzyTime;
        		}
        	}
        	else if(Character.getNumericValue(time.charAt(11))==1&&Character.getNumericValue(time.charAt(12))<4){
        		this.fuzzyTime=LUNCHTIME;
        		if(this.oldFuzzyTime!=this.fuzzyTime) {
        			setMenuNull();
        			parseMenu();
        			this.oldFuzzyTime=this.fuzzyTime;
        		}
        	}
        	else{
        		this.fuzzyTime=DINNERTIME;
        		if(this.oldFuzzyTime!=this.fuzzyTime) {
        			setMenuNull();
        			parseMenu();
        			this.oldFuzzyTime=this.fuzzyTime;
        		}
        	}
    	}
    }
    public void setMenuNull(){
    	global.clear();
    	rise.clear();
    	sizzle.clear();
    	pomodoro.clear();
    	roots.clear();
    	rosies.clear();
    }
	public void downloadMenu() {
		try { 
			  
            // Create URL object 
            URL url = new URL("https://rose-hulman.cafebonappetit.com/cafe/cafe"); 
            BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream())); 
  
            // Enter filename in which you want to download 
            BufferedWriter writer = new BufferedWriter(new FileWriter("Download.html")); 
              
            // read each line from stream till end 
            String line; 
            while ((line = readr.readLine()) != null) { 
                writer.write(line); 
            } 
  
            readr.close(); 
            writer.close(); 
            System.out.println("Successfully Downloaded."); 
        } 
  
        // Exceptions 
        catch (MalformedURLException mue) { 
            System.out.println("Malformed URL Exception raised"); 
        } 
        catch (IOException ie) { 
           
	    }
	}
	public void parseMenuItems(){
	    Scanner sc2 = null;
	    try {
	        sc2 = new Scanner(new File("menuItems.txt"));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    boolean hitExtras = false;
	    boolean waitForDesserts = false;
   
		String currentSection = "";
		
		while (sc2.hasNext()) {
			String s = sc2.next();

            if(s.equals("Extras")){
            	hitExtras = true;
            }
            if (hitExtras==true&&!s.equals("Extras")) {
            	if(waitForDesserts==false&&!s.equals("nutrition")&&!s.equals("information")&&!s.equals("cal.")&&isNumeric(s)==false){
            		if(s.equals("roots") || s.contentEquals("global") || s.contentEquals("sizzle")|| s.contentEquals("rise")|| (s.contentEquals("rosies"))|| s.contentEquals("pomodoro")){
            				currentSection = s;
            				System.out.println(s);
            				if(s.equals("roots")&&roots.size()>0) {
            					waitForDesserts = true;
            				}
            				else if(s.equals("global")&&global.size()>0) {
            					waitForDesserts = true;
            				}
            				else if(s.equals("sizzle")&&sizzle.size()>0) {
            					waitForDesserts = true;
            				}
            				else if(s.equals("rise")&&rise.size()>0) {
            					waitForDesserts = true;
            				}
            				else if(s.equals("pomodoro")&&pomodoro.size()>0) {
            					waitForDesserts = true;
            				}
            				else if(s.equals("rosies")&&rosies.size()>0) {
            					waitForDesserts = true;
            				}
            			}
            			if(currentSection.equals("sizzle")) {
							sizzle.add(s);
						}
						else if(currentSection.equals("global")) {
							global.add(s);
						}	
						else if(currentSection.equals("roots")) {
							roots.add(s);
						}	
						else if(currentSection.equals("pomodoro")) {
							pomodoro.add(s);
						}	
						else if(currentSection.equals("rise")) {
							rise.add(s);
						}	
						else if(currentSection.equals("rosies")) {
							rosies.add(s);
						}
            		} 
            }
        }
	}
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
    public void parseMenu(){
    	downloadMenu();
    	File input = new File("Download.html");
    	Document doc=null;
		try {
			doc = Jsoup.parse(input, "UTF-8", "https://rose-hulman.cafebonappetit.com/cafe/cafe");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Elements links = doc.getElementsByClass("site-panel--daypart");
		boolean linkFound = false;
		for(Element link : links){
			if(fuzzyTime == BREAKFASTTIME&&link.id().equals("breakfast")){
				menuSearch = "breakfast";
				linkFound = true;
			}
			else if(fuzzyTime == LUNCHTIME&&link.id().equals("lunch")){
				menuSearch = "lunch";
				linkFound = true;
			}
			else if(fuzzyTime == DINNERTIME&&link.id().equals("dinner")){
				menuSearch = "dinner";
				linkFound = true;
			}
			else if(fuzzyTime == BRUNCHTIME&&link.id().equals("brunch")){
				menuSearch = "brunch";
				linkFound = true;
			}
			System.out.println("Id : "+link.id());
			System.out.println("Text : "+link.text());
			if(linkFound){ 
				BufferedWriter writer = null;
				try
				{
				    writer = new BufferedWriter( new FileWriter("menuItems.txt"));
				    writer.write(link.text());
				}
				catch ( IOException e)
				{
				}
				finally
				{
				    try
				    {
				        if ( writer != null)
				        writer.close( );
				    }
				    catch ( IOException e)
				    {
				    }
				}
				linkFound = false;
			}
		}
        parseMenuItems();  
    }
}