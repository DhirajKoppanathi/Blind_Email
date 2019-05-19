package com.entrepreneurship_project.dhs_2019.blind_email;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextVoiceReader {
	private static final String VOICENAME_kevin = "kevin16";
	private String speechText;
	
	public TextVoiceReader(String s) {
		setSpeechText(s);
	}
	
	public String getSpeechText() {
		return speechText;
	}

	public void setSpeechText(String speechText) {
		this.speechText = speechText;
	}

	public void speak() {
	   Voice voice;
	   System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
	   VoiceManager voiceManager = VoiceManager.getInstance();
	   voice = voiceManager.getVoice(VOICENAME_kevin);
	   voice.allocate();
	   voice.speak(getSpeechText());
	   }
}
	



