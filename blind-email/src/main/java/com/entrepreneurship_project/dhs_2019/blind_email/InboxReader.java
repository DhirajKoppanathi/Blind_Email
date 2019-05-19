package com.entrepreneurship_project.dhs_2019.blind_email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InboxReader {
	private static GmailInbox g;
	private static String readingText;
	private static FileReader textSource;
	private static TextVoiceReader tvr;
	
	public static void main(String[] args) {
		g = new GmailInbox();
		g.read();
		try {
			textSource = new FileReader("C:\\Users\\dhira\\Documents\\AP_CSA\\Entrepreneurship_Proj_2019\\blind-email\\src\\main\\resources\\contents.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    int i; 
	    try {
			while ((i=textSource.read()) != -1) 
			  readingText += (char) i;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    tvr = new TextVoiceReader(readingText);
	    tvr.speak();
	  } 
	
	
		
}


