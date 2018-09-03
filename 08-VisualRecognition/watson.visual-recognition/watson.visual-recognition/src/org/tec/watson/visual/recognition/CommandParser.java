package org.tec.watson.visual.recognition;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import net.sourceforge.argparse4j.inf.Subparsers;

public final class CommandParser {
	private final ArgumentParser parser;

	public CommandParser() {
		this.parser = ArgumentParsers.newArgumentParser("watson-vr")
				.defaultHelp(true)
				.description("Test application for the IBM Watson Visual Recognition API");
		Subparsers subparsers = this.parser.addSubparsers()
				.help("action help:")
				.metavar("<action>")
				.dest("action");
		/**
		 * Configuration for the train subcommand
		 */
		Subparser train = subparsers.addParser("train")
				.help("Train Watson for a model");
		train.addArgument("classifier")
				.help("Id of the classifier to create")
				.required(true);
		train.addArgument("class")
				.help("Class name of the training material")
				.required(true);
		train.addArgument("training_material")
				.help("Path to a zip file with the training material")
				.required(true);
		
		/**
		 * Configuration for the test subcommand
		 */
		Subparser test = subparsers.addParser("test")
				.help("Test an image with Watson");
		test.addArgument("test_file_path")
				.help("Path of the image file to test with Watson")
				.required(true);
		test.addArgument("classifier")
				.help("Id of the classifier to test with")
				.setDefault("default")
				.nargs("?");
		
	}

	/**
	 * Parses command-line arguments.
	 * @param args The arguments passed to the program
	 * @return The parsed arguments
	 */
	public Namespace parseArgs(String[] args) throws ArgumentParserException {
		return this.parser.parseArgs(args);
	}

	/**
	 * Parses command-line arguments, handling any errors.
	 * @param args The arguments passed to the program
	 * @return The parsed arguments
	 */
	public Namespace parseArgsOrFail(String[] args) {
		return this.parser.parseArgsOrFail(args);
	}

	/**
	 * Prints usage and an error message.
	 * Note that this method does not terminate the program.
	 *
	 * @param e exception to handle
	 */
	public void handleError(ArgumentParserException e) {
		this.parser.printHelp();
	}

	public ArgumentParser getInternalParser() {
		return this.parser;
	}
}