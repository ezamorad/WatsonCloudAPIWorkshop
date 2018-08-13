//https://github.com/watson-developer-cloud/java-sdk/tree/master/speech-to-text

package tec.watson.workshop.speechtotext;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

public class SpeechToTextMicrophone {
	public static void main(String[] args) {
	   
	   try {
		SpeechToText service = new SpeechToText();
		   service.setUsernameAndPassword("753a8c33-2672-44e4-b334-d905abc7c432", "SVR75EtyFyNC");

		   // Signed PCM AudioFormat with 16kHz, 16 bit sample size, mono
		   int sampleRate = 16000;
		   AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
		   DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		   if (!AudioSystem.isLineSupported(info)) {
		     System.out.println("Line not supported");
		     System.exit(0);
		   }

		   TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		   line.open(format);
		   line.start();

		   AudioInputStream audio = new AudioInputStream(line);

		   RecognizeOptions options = new RecognizeOptions.Builder()
		     .interimResults(true)
		   //.inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
		     .audio(audio)
		     .contentType(HttpMediaType.AUDIO_RAW + "; rate=" + sampleRate)
		     .model("es-ES_BroadbandModel")
		     .keywords(Arrays.asList("prueba"))
		     .keywordsThreshold((float) 0.5)
		     .maxAlternatives(3)
		     .build();

		   service.recognizeUsingWebSocket(options, new BaseRecognizeCallback() {
		     @Override
		     public void onTranscription(SpeechRecognitionResults speechResults) {
		       System.out.println(speechResults);
		     }
		   });

		   System.out.println("Listening to your voice for the next 30s...");
		   Thread.sleep(30 * 1000);

		   // closing the WebSockets underlying InputStream will close the WebSocket itself.
		   line.stop();
		   line.close();

		   System.out.println("Fin.");
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
   }
}
