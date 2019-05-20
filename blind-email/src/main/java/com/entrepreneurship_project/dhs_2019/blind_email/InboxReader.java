package com.entrepreneurship_project.dhs_2019.blind_email;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InboxReader {
	private static GmailInbox g;
	private static String readingText;
	private static FileReader textSource;
	private static TextVoiceReader tvr;
	
	public static void textCreater() {
	    int i; 
	    try {
			while ((i=textSource.read()) != -1) 
			  readingText += (char) i;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void fileObtainer() {
		try {
			textSource = new FileReader("C:\\Users\\dhiraj56457\\Documents\\AP_CSA\\Blind_Email\\blind-email\\src\\main\\resources\\contents.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		g = new GmailInbox();
		g.read();
		fileObtainer();
		textCreater();
	    tvr = new TextVoiceReader(readingText);
	    tvr.speak();
	  } 
}


