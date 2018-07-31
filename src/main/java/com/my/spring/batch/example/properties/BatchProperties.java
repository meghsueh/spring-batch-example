package com.my.spring.batch.example.properties;

public class BatchProperties {
	private String fileSeperator;
	private String fileNameExtension;
	private String fileOutputPath;

	public String getFileSeperator() {
		return fileSeperator;
	}

	public void setFileSeperator(String fileSeperator) {
		this.fileSeperator = fileSeperator;
	}

	public String getFileNameExtension() {
		return fileNameExtension;
	}

	public void setFileNameExtension(String fileNameExtension) {
		this.fileNameExtension = fileNameExtension;
	}

	public String getFileOutputPath() {
		return fileOutputPath;
	}

	public void setFileOutputPath(String fileOutputPath) {
		this.fileOutputPath = fileOutputPath;
	}
}
