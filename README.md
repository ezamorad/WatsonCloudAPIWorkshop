# WatsonCloudAPIWorkshop


## Objetivo general:
Demostrar el uso de los APIs cognitivos de Watson disponibles en IBM Cloud y la utilizacion de los lenguajes de programacion Java (principalmente), Javascript (NodeJS) y otras herramientas para crear applicaciones estos APIs.

## Objetivos especificos:
  - Demostrar el funcionamiento del API: Watson Assistant
  - Demostrar el funcionamiento del API: Discovery
  - Demostrar el funcionamiento del API: Visual Recognition
  - Demostrar el funcionamiento del API: Natural Language Understanding
  - Demostrar el funcionamiento del API: Speech to Text
  - Demostrar el funcionamiento del API: Text to Speech
  - Demostrar el funcionamiento del API: Natural Language Classifier
  - Demostrar el funcionamiento del API: Personality Insights
  - Demostrar el funcionamiento del API: Tone Analyzer
  - Demostrar el funcionamiento del API: Language Translator
  - Demostrar el funcionamiento del API: Machine Learning
  - Crear applicacion combinando los servicios Watson Assistant
  - Crear applicacion combinando los servicios Watson Assistant + Tone Analyzer
  - Crear applicacion combinando los servicios Visual Recognition
  - Crear applicacion combinando los servicios Natural Language Understanding
  - Crear applicacion combinando los servicios  Watson Assistant + Discovery


## Propuesta de agenda

Workshop Pt1:

Day 1:
  - 1 hr - Introduction to Cognitive Computing and Watson
  - 0.5 hr - Review of IBM Cloud
  - 0.5 hr - API: Watson Assistant
  - 1 hr - Develop app – Reservation ChatBot

Day 2:
  - 0.5 hr - Speech to Text
  - 0.5 hr - Text to Speech
  - 0.5 hr - Language Translator
  - 0.5 hr - Personality Insights
  - 0.5 hr - Tone Analyzer

Day 3:
  - 1.0 hr - Develop app (Empathetic chatbot)
  - 0.5 hr - Visual Recognition
  - 1.0 hr – Develop app (TBD)

Workshop Pt2:

Day 1:
  - 0.5 hr - Natural Language Classifier
  - 0.5 hr - Natural Language Understanding
  - 1.0 hr – Develop app (TBD)
  - 1.0 hr - Discovery

Day 2:
  - 0.5 hr – Develop app (Chatbot with recommendations from WEB)
  - 1.0 hr – Machine Learning
  - 0.5 hr Questions and feedback.


## Requisitos:
- Eclipse IDE instalado
- Cuenta de IBM Cloud


## Recursos generales:

- Repositorio de los materiales del Taller: https://github.com/ezamorad/WatsonCloudAPIWorkshop.git

- Acceso a IBM Cloud para estudiantes : (IBM Academy Initiative) https://onthehub.com/ibm/

- Watson Java SDK: https://github.com/watson-developer-cloud/java-sdk 

- IBM redbooks: http://www.redbooks.ibm.com/redbooks.nsf/pages/cognitiveapps?Open



### 1. Watson Assistant (formerly Conversation)
- Documentation: https://console.bluemix.net/docs/services/conversation/getting-started.html#gettingstarted
- API Reference: https://www.ibm.com/watson/developercloud/assistant/api/v1/java.html?java
- Demo: https://watson-conversation-duo-dev.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248394.html?Open

### 2. Discovery
- Documentation: https://console.bluemix.net/docs/services/discovery/getting-started.html#getting-started-with-the-api
- API Reference: ttps://www.ibm.com/watson/developercloud/discovery/api/v1/java.html?java
- Demo: https://discovery-news-demo.ng.bluemix.nhet/?cm_mc_uid=22565909282615302790198&cm_mc_sid_50200000=15890121530536433028&cm_mc_sid_52640000=55219051530307399788
- Redbook: -

### 3. Visual Recognition
- Documentation: https://console.bluemix.net/docs/services/visual-recognition/getting-started.html#getting-started-tutorial
- API Reference: https://www.ibm.com/watson/developercloud/visual-recognition/api/v3/java.html?java
- Demo: https://watson-visual-recognition-duo-dev.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248393.html?Open

### 4. Natural Language Understanding
- Documentation: https://console.bluemix.net/docs/services/natural-language-understanding/getting-started.html#getting-started-tutorial
- API Reference: https://www.ibm.com/watson/developercloud/natural-language-understanding/api/v1/?java#
- Demo: https://natural-language-understanding-demo.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248398.html?Open

### 5. Speech to Text
- Documentation: https://console.bluemix.net/docs/services/speech-to-text/getting-started.html#gettingStarted
- API Reference: https://www.ibm.com/watson/developercloud/speech-to-text/api/v1/java.html?java
- Demo: https://speech-to-text-demo.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248388.html?Open

### 6. Text to Speech
- Documentation: https://console.bluemix.net/docs/services/text-to-speech/getting-started.html#gettingStarted
- API Reference: https://www.ibm.com/watson/developercloud/text-to-speech/api/v1/java.html?java
- Demo: https://text-to-speech-demo.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248388.html?Open

### 7. Natural Language Classifier
- Documentation: https://console.bluemix.net/docs/services/natural-language-classifier/getting-started.html#natural-language-classifier
- API Reference: https://www.ibm.com/watson/developercloud/natural-language-classifier/api/v1/java.html?java
- Demo:  https://natural-language-classifier-demo.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248391.html?Open

### 8. Personality Insights
- Documentation: https://console.bluemix.net/docs/services/personality-insights/getting-started.html#getting-started-tutorial
- API Reference: https://www.ibm.com/watson/developercloud/personality-insights/api/v3/java.html?java
- Demo: https://personality-insights-demo.ng.bluemix.net/
- Redbook: -

### 9. Tone Analyzer
- Documentation: https://console.bluemix.net/docs/services/tone-analyzer/getting-started.html#getting-started-tutorial
- API Reference: https://www.ibm.com/watson/developercloud/tone-analyzer/api/v3/java.html?java
- Demo: https://tone-analyzer-demo.ng.bluemix.net/
- Demo: https://customer-engagement-demo.ng.bluemix.net/
- Redbook: -

### 10. Language Translator
- Documentation: https://console.bluemix.net/docs/services/language-translator/getting-started.html#gettingstarted
- API Reference: https://www.ibm.com/watson/developercloud/language-translator/api/v3/java.html?java
- Demo: https://language-translator-demo.ng.bluemix.net/
- Redbook: http://www.redbooks.ibm.com/redbooks.nsf/redbookabstracts/sg248392.html?Open

### 11. Machine Learning
- Documentation: https://console.bluemix.net/docs/services/PredictiveModeling/index.html#WMLgettingstarted
- API Reference: https://dataplatform.ibm.com/docs/content/analyze-data/pm_service_api_spark.html
- Demo: -
- Redbook: -
