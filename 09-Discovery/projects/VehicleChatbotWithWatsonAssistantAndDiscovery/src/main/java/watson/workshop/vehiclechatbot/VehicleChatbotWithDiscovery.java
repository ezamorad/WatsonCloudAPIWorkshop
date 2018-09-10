/*
 * This example was implemented taking into account the approach introduced in this blog:
 * https://developer.ibm.com/watson/blog/2016/10/17/creating-a-compassionate-conversational-agent-using-watson-tone-analyzer-and-watson-conversation-services/
 */

package watson.workshop.vehiclechatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResult;

public class VehicleChatbotWithDiscovery {
	public static void main(String[] args) {

		try {
			// Suppress log messages in stdout.
			LogManager.getLogManager().reset();

			// Set up Assistant service.
			Assistant assistantService = new Assistant("2018-02-16");
			assistantService.setUsernameAndPassword("b611a43b-942e-4cfe-b06e-ed296ce1a3dc", "LDp6qZVwPVKu");
			String workspaceId = "b9a4d360-5f9c-4fd4-a2a5-f25b7016f703";

			// Set up discovery service.
			Discovery discovery = new Discovery("2017-11-07");
		    discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
		    discovery.setUsernameAndPassword("a1b21ffb-b1c4-4a87-ab69-971e1ec09322", "crJrfj08frbP");
		    String environmentId = "82c6a584-c5f2-45f8-9cbc-6e30179900a2";
		    String configurationId = "bd29e476-3b52-4297-8ada-b2db93d44716";
		    String collectionId = "9367752b-5630-4d5a-abd6-a9eda6dc10e0";

			// Initialize with empty value to start the conversation.
			MessageOptions options = new MessageOptions.Builder(workspaceId).build();
			Context context = new Context();
			String currentAction = "";

			// Main input/output loop
			do {
				// Send message to Assistant service.
				MessageResponse response = assistantService.message(options).execute();
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				//System.out.println(response);
				//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

				// Copy the current context to update it, if necessary, and append it back to the Assistant 
				context = response.getContext();

				// Check for discovery action flag sent by the dialog.
				if (response.getOutput().containsKey("action")
				        && (response.getOutput().get("action").toString().indexOf("call_discovery") != -1)) {

					String queryStr = response.getInput().getText();
					
					System.out.println("Querying the collection...");
				    QueryOptions.Builder queryBuilder = new QueryOptions.Builder(environmentId, collectionId);
				    queryBuilder.query(queryStr);
				    QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute();
				    List<QueryResult> queryrResultList = queryResponse.getResults();
				    
				    List<DocumentPayload> payload = new ArrayList<DocumentPayload>();
				    int resultsToReturn = 3;
				    
				    DocumentPayload documentPayload;
					for (QueryResult queryResult : queryrResultList) {
						documentPayload = new DocumentPayload();
						documentPayload.setId(queryResult.get("id").toString());
						documentPayload.setTitle(queryResult.get("title").toString().replaceAll("\"", ""));

						if (queryResult.get("body") != null) {
							String body = queryResult.get("body").toString().replaceAll("\"", "");
							String bodyTwoParagraph = limitParagraph(body);
							documentPayload.setBody(bodyTwoParagraph);
							documentPayload.setBodySnippet(getSniplet(body));
						} else {
							documentPayload.setBody("empty");
						}
						
						if (queryResult.get("sourceURL") == null) {
							documentPayload.setSourceUrl("empty");
						} else {
							documentPayload.setSourceUrl(queryResult.get("sourceUrl").toString().replaceAll("\"", ""));
						}
						
						if (queryResult.get("score") != null) {
							documentPayload.setConfidence(queryResult.get("score").toString().replaceAll("\"", ""));
						} else {
							documentPayload.setConfidence("0.0");
						}
						
						payload.add(documentPayload);

						if (resultsToReturn-- == 0)
							break;
					}

				    response.put("DiscoveryPayload", payload);
				}
				
				printResponseFromTheChatbot(response);

				// If we're not done, prompt for next round of input.
				if (!currentAction.equals("quit")) {
					System.out.print(">> ");
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					String inputText = in.readLine();

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
	
	private static void printResponseFromTheChatbot(MessageResponse response) {
		// Print the output from dialog, if any.
		List<String> responseText = response.getOutput().getText();
		for (String aResponse : responseText) {
			System.out.println(aResponse);
		}
		
		if(response.get("DiscoveryPayload") != null) {
			System.out.println("Recommendations obtained from Watson Discovery");
			List<DocumentPayload> payload = (List<DocumentPayload>)response.get("DiscoveryPayload");
			for(DocumentPayload documentPayload : payload) {
				System.out.println("Id:" + documentPayload.getId());
				System.out.println("Title: " + documentPayload.getTitle());
				System.out.println("Information:" + System.lineSeparator() + documentPayload.getBody());
				System.out.println("Source URL: " + documentPayload.getSourceUrl());
				System.out.println("-----------------------");
			}
		}
		
	}
	
	private static String getSniplet(String body) {
		if (body == null) {
			return "";
		}

		int len = body.length();
		if (len > 100) {
			body = body.substring(0, 100 - 3) + "...";
		}
		return body;
	}
	
	private static String limitParagraph(String body) {
		String returnString = body;
		Pattern pattern = Pattern.compile("((.+?)</p>){1,2}");

		Matcher matcher = pattern.matcher(body);
		if (matcher.find()) {
			returnString = matcher.group(0);
		}

		return returnString;
	}
}
