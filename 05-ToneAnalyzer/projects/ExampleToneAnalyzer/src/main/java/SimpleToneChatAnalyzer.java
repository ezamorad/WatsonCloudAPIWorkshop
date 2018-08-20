/*
 * Copyright 2017 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

import java.util.ArrayList;
import java.util.List;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneChatOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Utterance;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.UtteranceAnalyses;

public class SimpleToneChatAnalyzer {

  public static void main(String[] args) {
		ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
		service.setUsernameAndPassword("f723690b-6f85-4b63-82be-781c3d57bcba", "PmW1obyj3meX");

    String[] texts = {
        "My charger isn't working.",
        "Thanks for reaching out. Can you give me some more detail about the issue?",
        "I put my charger in my tablet to charge it up last night and it keeps saying it isn't"
            + " charging. The charging icon comes on, but it stays on even when I take the charger out. "
            + "Which is ridiculous, it's brand new.",
        "I'm sorry you're having issues with charging. What kind of charger are you using?"
    };

    List<Utterance> utterances = new ArrayList<Utterance>();
    for (int i = 0; i < texts.length; i++) {
      Utterance utterance = new Utterance.Builder()
          .text(texts[i])
          .build();
      utterances.add(utterance);
    }
    ToneChatOptions toneChatOptions = new ToneChatOptions.Builder()
        .utterances(utterances)
        .build();

    // Call the service
    UtteranceAnalyses utterancesTone = service.toneChat(toneChatOptions).execute();
    System.out.println(utterancesTone);
  }
}