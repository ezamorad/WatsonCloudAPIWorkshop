/*
 * This example was implemented taking into account the approach introduced in this blog:
 * https://developer.ibm.com/watson/blog/2016/10/17/creating-a-compassionate-conversational-agent-using-watson-tone-analyzer-and-watson-conversation-services/
 */

/*
 * Example 4: Stored reservation into Database
 */

package watson.workshop.tablereservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

import org.json.JSONObject;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneChatOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneChatScore;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Utterance;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.UtteranceAnalyses;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.UtteranceAnalysis;

import watson.workshop.dao.ReservationDao;
import watson.workshop.dao.ReservationFactory;
import watson.workshop.model.Reservation;
import watson.workshop.toneanalysis.model.Tone;
import watson.workshop.toneanalysis.model.User;

public class TableReservationWithToneAnalysis {
	public static void main(String[] args) {

		try {
			// Suppress log messages in stdout.
			LogManager.getLogManager().reset();

			// Set up Assistant service.
			Assistant assistantService = new Assistant("2018-02-16");
			assistantService.setUsernameAndPassword("b611a43b-942e-4cfe-b06e-ed296ce1a3dc", "LDp6qZVwPVKu");
			String workspaceId = "ad5271c8-df2f-452a-a99a-6f4b59bd827e";

			// Set up tone analyzer service.
			ToneAnalyzer toneAnalyzerService = new ToneAnalyzer("2017-09-21");
			toneAnalyzerService.setUsernameAndPassword("f723690b-6f85-4b63-82be-781c3d57bcba", "PmW1obyj3meX");

			// Initialize with empty value to start the conversation.
			MessageOptions options = new MessageOptions.Builder(workspaceId).build();
			Context context = new Context();
			String currentAction = "";

			// Main input/output loop
			do {
				// Send message to Assistant service.
				MessageResponse response = assistantService.message(options).execute();
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(response);
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				
				// Print the output from dialog, if any.
				List<String> responseText = response.getOutput().getText();
				for (String aResponse : responseText) {
					System.out.println(aResponse);
				}

				// Update the stored context with the latest received from the dialog.
				context = response.getContext();

				// Check for action flags sent by the dialog.
				JSONObject outputObj = new JSONObject(response.getOutput().toString());
				if (outputObj.has("action")) {
					currentAction = outputObj.getString("action");
				} else {
					currentAction = "";
				}

				// User confirms saving the reservation
				if (currentAction.equals("save")) {

					System.out.println("Actions is SAVE");

					ReservationFactory reservatioFactory = new ReservationFactory();
					ReservationDao reservationDao = reservatioFactory.getReservationDao();

					Reservation reservation = new Reservation();
					reservation.setDate((String) context.get("date"));
					reservation.setTime((String) context.get("time"));
					reservation.setGuests(((Double) context.get("guests")).intValue());

					reservationDao.save(reservation);

					// no need to clean the action since it does not return back in the context.
				}

				// If we're not done, prompt for next round of input.
				if (!currentAction.equals("quit")) {
					System.out.print(">> ");
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					String inputText = in.readLine();

					//Get tone from the user input and update the context with it
					updateConversationTone(toneAnalyzerService, context, inputText);
					

					InputData input = new InputData.Builder(inputText).build();
					options = new MessageOptions.Builder(workspaceId).input(input).context(context).build();
				}

			} while (!currentAction.equals("quit"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void updateConversationTone(ToneAnalyzer toneAnalyzerService, Context context, String inputText) {
		UtteranceAnalyses utteranceToneList;
		double maxScore = 0.0;
		String primaryTone = "neutral";
		//String primaryToneScore = "-";
		
		if (inputText != null && !inputText.isEmpty()) {
			utteranceToneList = getTone(toneAnalyzerService, inputText);
			
			for(UtteranceAnalysis utteranceTone : utteranceToneList.getUtterancesTone()) {
				for(ToneChatScore toneChatScore : utteranceTone.getTones()) {
					if(maxScore < toneChatScore.getScore()) {
						maxScore = toneChatScore.getScore();
						primaryTone = toneChatScore.getToneId();
						//primaryToneScore = toneChatScore.getToneName();
					}
				}
			}
		}
		
		
		context.put("user", new User(new Tone(primaryTone)));
	}

	private static UtteranceAnalyses getTone(ToneAnalyzer toneAnalyzerService, String inputText) {
		List<Utterance> utterances = new ArrayList<Utterance>();
		Utterance utterance = new Utterance.Builder().text(inputText).build();
		utterances.add(utterance);
		ToneChatOptions toneChatOptions = new ToneChatOptions.Builder().utterances(utterances).build();

		// Call the service
		UtteranceAnalyses utterancesTone = toneAnalyzerService.toneChat(toneChatOptions).execute();
		System.out.println(utterancesTone);
		
		return utterancesTone;
	}
}
