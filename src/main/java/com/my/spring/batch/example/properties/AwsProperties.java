package com.my.spring.batch.example.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
@PropertySource("${batch.config.location}external.${spring.profiles.active}.properties")
public class AwsProperties {
	private String sshUsername;
	private String sshHost;
	private String sshKeyFile;
	private String s3bucket;
	private String s3Key;

	public String getSshUsername() {
		return sshUsername;
	}

	public void setSshUsername(String username) {
		this.sshUsername = username;
	}

	public String getS3bucket() {
		return s3bucket;
	}

	public void setS3bucket(String s3bucket) {
		this.s3bucket = s3bucket;
	}

	public String getSshHost() {
		return sshHost;
	}

	public void setSshHost(String sshHost) {
		this.sshHost = sshHost;
	}

	public String getSshKeyFile() {
		return sshKeyFile;
	}

	public void setSshKeyFile(String sshKeyFile) {
		this.sshKeyFile = sshKeyFile;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}
}
