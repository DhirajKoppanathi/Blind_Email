package com.entrepreneurship_project.dhs_2019.blind_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
 
public class GmailInbox {
 
    

//	public static void main(String[] args) {
// 
//        GmailInbox gmail = new GmailInbox();
//        gmail.read();
// 
//    }
	private File f;
    
    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
    
    public File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
    
 
    public void read() {
        Properties props = new Properties(); 
        try {
            props.load(new FileInputStream(new File(this.getClass().getResource( "/smtp.properties" ).toURI())));
            Session session = Session.getDefaultInstance(props, null);
 
            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "nothing.nothing.10.9.8.7.6.5.4.3.2.1", "&4k7nW2DBQeYrt&");
 
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
 
            System.out.println("Total Messages: " + messageCount);
 
            Message[] messages = inbox.getMessages();
             // System.out.println("------------------------------\n\n");
            
            f = new File("C:\\Users\\dhiraj56457\\Documents\\AP_CSA\\Blind_Email\\blind-email\\src\\main\\resources\\contents.txt");
            System.out.println(f);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("");
            
            for (int i = 0; i < messages.length; i++) {
                System.out.println("Mail " + (i+1) + " Subject: " + messages[i].getSubject());
                pw.println("Mail " + (i+1) + " Subject: " + messages[i].getSubject());
                
                System.out.println("Mail " + (i+1) + " Date: " + messages[i].getReceivedDate());
                pw.println("Mail " + (i+1) + " Date: " + messages[i].getReceivedDate());
                
                System.out.println("Mail " + (i+1) + " Content: \n" + getTextFromMessage(messages[i]));
                pw.println("Mail " + (i+1) + " Content: \n" + getTextFromMessage(messages[i]));
                
                pw.println("");
            }
            pw.close();
            inbox.close(true);
            store.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}