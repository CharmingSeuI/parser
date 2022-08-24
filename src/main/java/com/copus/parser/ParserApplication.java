package com.copus.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@PropertySource("classpath:fileName.properties")
@SpringBootApplication
@RequiredArgsConstructor
public class ParserApplication implements CommandLineRunner {

	private final XmlReaderMain xmlReaderMain;
	@Value("${fileName}")
	private String fileName;

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		SpringApplication.run(ParserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		xmlReaderMain.read(fileName);
	}
}
