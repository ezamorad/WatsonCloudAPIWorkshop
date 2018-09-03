package org.tec.watson.visual.recognition.commands;

import java.io.File;
import java.io.FileNotFoundException;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

public final class TestCommand extends BaseCommand {
	private File fileToTest;
	private String classifier;
	
	private TestCommand(String filePath, String classifier) {
		this.fileToTest = new File(filePath);
		this.classifier = classifier;
	}
	
	public void execute() throws FileNotFoundException {
		ClassifyOptions requestOptions = new ClassifyOptions.Builder()
				.imagesFile(this.fileToTest)
				.addClassifierId(this.classifier)
				.build();
		ServiceCall<ClassifiedImages> serviceCall = service.classify(requestOptions);
		ClassifiedImages result = serviceCall.execute();
		for (ClassifiedImage testedImage : result.getImages()) {
			for (ClassifierResult classifierResult : testedImage.getClassifiers()) {
				for (ClassResult classResult : classifierResult.getClasses()) {
					System.out.println(String.format("For class %s Watson scores it to %f", classResult.getClassName(), classResult.getScore())); 
				}
			}
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String testFile;
		private String classifier;
		
		public Builder withTestFile(String fileName) {
			this.testFile = fileName;
			return this;
		}

		public Builder withClassifier(String classifier) {
			this.classifier = classifier;
			return this;
		}

		public TestCommand build() {
			return new TestCommand(this.testFile, this.classifier);
		}
	}
}
