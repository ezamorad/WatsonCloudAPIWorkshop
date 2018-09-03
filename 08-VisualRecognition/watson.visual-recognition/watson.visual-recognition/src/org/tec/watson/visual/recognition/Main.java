package org.tec.watson.visual.recognition;

import org.tec.watson.visual.recognition.commands.TestCommand;
import org.tec.watson.visual.recognition.commands.TrainCommand;

import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {
	public static void main(String[] args) {
		CommandParser options = new CommandParser();
		try {
			Namespace parsedArgs = options.parseArgs(args);
			String command = parsedArgs.getString("action");
			if (command.equalsIgnoreCase("train")) {
				TrainCommand.builder() 
					.withClassifier(parsedArgs.getString("classifier"))
					.withTrainingMaterial(parsedArgs.getString("training_material"))
					.withClassName(parsedArgs.getString("class"))
					.build()
					.execute();
			} else if (command.equalsIgnoreCase("test")) {
				TestCommand.builder()
					.withTestFile(parsedArgs.getString("test_file_path"))
					.withClassifier(parsedArgs.getString("classifier"))
					.build()
					.execute();
			}
		} catch (ArgumentParserException ex) {
			options.handleError(ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
