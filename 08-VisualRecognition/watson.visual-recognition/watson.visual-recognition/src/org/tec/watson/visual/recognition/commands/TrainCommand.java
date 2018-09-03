package org.tec.watson.visual.recognition.commands;

import java.io.File;
import java.io.FileNotFoundException;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifier;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.CreateClassifierOptions;

public final class TrainCommand extends BaseCommand {
	private String classifier;
	private String className;
	private File trainingMaterial; 
	
	private TrainCommand(String classifier, String className, String trainingMaterial) {
		this.classifier = classifier;
		this.className = className;
		this.trainingMaterial = new File(trainingMaterial);
	}
	
	public void execute() throws FileNotFoundException {
		CreateClassifierOptions requestOptions = new CreateClassifierOptions.Builder()
				.name(this.classifier)
				.addClass(this.className, this.trainingMaterial)
				.build();
		ServiceCall<Classifier> serviceCall = service.createClassifier(requestOptions);
		serviceCall.enqueue(new ServiceCallback<Classifier>() {
			@Override
			public void onFailure(Exception e) {
				System.out.println("Error while creating the classifier " + e.getMessage());
				
			}
			@Override
			public void onResponse(Classifier arg0) {
				System.out.println("classifier created");
				
			}
		});
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String classifier;
		private String className;
		private String trainingMaterial;

		public Builder withClassifier(String classifier) {
			this.classifier = classifier;
			return this;
		}
		
		public Builder withTrainingMaterial(String trainingMaterial) {
			this.trainingMaterial = trainingMaterial;
			return this;
		}
		
		public Builder withClassName(String name) {
			this.className = name;
			return this;
		}

		public TrainCommand build() {
			return new TrainCommand(classifier, this.className, this.trainingMaterial);
		}
	}
}
