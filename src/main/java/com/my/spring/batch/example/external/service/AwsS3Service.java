package com.my.spring.batch.example.external.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.my.spring.batch.example.properties.AwsProperties;

public class AwsS3Service {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3Service.class);

	@Autowired
	private AwsProperties awsProp;

	public void upload(String uploadFile) throws JSchException, IOException {
		LOGGER.info(String.format("Uplading %s", uploadFile));

		// get just filename
		File file = new File(uploadFile);
		Path path = file.toPath();
		String filename = path.getFileName().toString();

		String cmd = String.format("aws s3 cp - s3://%s/%s/%s --sse --expected-size %d", awsProp.getS3bucket(),
				awsProp.getS3Key() + "/" + filename, filename, file.length());
		LOGGER.info("S3 cmd: " + cmd);

		JSch jSch = new JSch();

		jSch.addIdentity(awsProp.getS3Key());
		Session session = jSch.getSession(awsProp.getSshUsername(), awsProp.getSshHost());
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();

		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(cmd);
		channel.connect();
		try (OutputStream outputStream = channel.getOutputStream()) {
			LOGGER.info(String.format("Copying data from %s to stream.", filename));
			Files.copy(file.toPath(), outputStream);
			outputStream.flush();
			LOGGER.info("Data written to SSH channel.");
		}

		try (InputStream inputStream = channel.getInputStream()) {
			try (InputStream errStream = channel.getErrStream()) {
				LOGGER.info("Reading output from executed command ....");

				// wait for the execution to complete
				byte[] buffer = new byte[1024];
				while (true) {
					while (inputStream.available() > 0) {
						int bytesRead = inputStream.read(buffer, 0, buffer.length);
						if (bytesRead < 0)
							break;
						// log the response
						LOGGER.info(new String(buffer, 0, bytesRead));
					}

					if (channel.isClosed()) {
						LOGGER.info(String.format("exist-status: %d", channel.getExitStatus()));
						break;
					}

					try {
						Thread.sleep(1000);
					} catch (Exception ee) {
						LOGGER.info(ee.getMessage());
					}
				}

				if (channel.getExitStatus() != 0) {
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(errStream))) {
						String line;
						while ((line = reader.readLine()) != null) {
							LOGGER.error(line);
						}
						reader.close();
					}
				} else {
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
						String line;
						while ((line = reader.readLine()) != null) {
							LOGGER.error(line);
						}
						reader.close();
					}
				}
				errStream.close();
			}
			inputStream.close();
		}
		channel.disconnect();
		session.disconnect();
	}
}
