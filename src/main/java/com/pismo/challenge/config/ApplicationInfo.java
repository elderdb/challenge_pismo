package com.pismo.challenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ApplicationInfo {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInfo.class);

  private static final String TOP_MESSAGE = "Pismo is running!";
  private static final String LINE = "----------------------------------------------------------";
  @Autowired
  private BuildProperties buildProperties;
  @Value("${server.port}")
  private String serverPort;
  @Value("${server.servlet.context-path}")
  private String contextPath;
  @Value("${spring.application.name}")
  private String applicationName;

  @EventListener(ApplicationReadyEvent.class)
  public void showApplicationInfo() throws UnknownHostException {
    StringBuilder infoMessage = new StringBuilder();

    infoMessage.append("\n")
        .append(LINE)
        .append("\n\t")
        .append(TOP_MESSAGE)
        .append("\n\t")
        .append("Application: http://").append(InetAddress.getLocalHost().getHostAddress())
        .append(":").append(serverPort).append(contextPath)
        .append("/").append(applicationName)
        .append("\n\t")
        .append("Swagger: http://").append(InetAddress.getLocalHost().getHostAddress())
        .append(":").append(serverPort).append(contextPath)
        .append("/").append(applicationName)
        .append("/swagger-ui.html")
        .append("\n")
        .append(LINE);

    LOGGER.info("{}", infoMessage);
  }

  @Bean
  public CommandLineRunner runner() {
    final StringBuilder message = new StringBuilder();

    message.append("\n").append(LINE).append("\n")
        .append("build.name= ").append(buildProperties.getName())
        .append("\n")
        .append("build.version= ").append(buildProperties.getVersion())
        .append("\n")
        .append("build.time= ").append(buildProperties.getTime())
        .append("\n")
        .append("build.artifact= ").append(buildProperties.getArtifact())
        .append("\n")
        .append("build.group= ").append(buildProperties.getGroup())
        .append("\n").append(LINE);

    return args -> LOGGER.info("{}", message);
  }

}
