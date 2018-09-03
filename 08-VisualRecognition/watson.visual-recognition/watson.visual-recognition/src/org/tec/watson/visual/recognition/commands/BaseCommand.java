package org.tec.watson.visual.recognition.commands;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;

public class BaseCommand {
	protected IamOptions credentials;
	protected VisualRecognition service;
	
	public BaseCommand() {
		this.credentials = new IamOptions.Builder()
				  .apiKey("1nSx-q3bE4Lpu84eZfkE1wL6O51pXVcLzY0r0hxpxLjb")
				  .build();
		this.service = new VisualRecognition("2018-01-19", this.credentials);
	}
}
