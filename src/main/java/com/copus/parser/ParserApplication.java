package com.copus.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class ParserApplication implements CommandLineRunner {

	private final XmlReaderMain xmlReaderMain;

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		SpringApplication.run(ParserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		xmlReaderMain.read("moon-light.xml");

	}
}
