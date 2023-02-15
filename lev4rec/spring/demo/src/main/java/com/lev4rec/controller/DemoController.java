package com.lev4rec.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.xtext.lev4recgrammar.first.lowcoders.RSModel;
//import lowcoders.RSModel;;

import com.lev4rec.business.FeatureHandler;
import com.lev4rec.dto.RSConfiguration;

@Controller
public class DemoController {
	@Autowired
	FeatureHandler fh = new FeatureHandler();

	@RequestMapping("/")
	public String index(Model model) {
		RSConfiguration config = new RSConfiguration();
		model.addAttribute("config", config);
		return "home.html";
	}

	@RequestMapping(value = "/dsl", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("config") RSConfiguration config) {
		String s = "";
		try {
			s = fh.getXtexString(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("xtext", s);
		return "dsl.html";
	}

	@RequestMapping(path = "/generate", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> download(@RequestParam("user_string") String dsl_string)
			throws IOException {
		/*
		 * RSModel coarse_model= FeatureHandler.loadModel("generated/demo.xmi");
		 * coarse_model.setName("KNN recsys");
		 * FeatureHandler.serializeModel(coarse_model, "generated/demo.xmi");
		 * FeatureHandler.generateFromTML("generated/demo.xmi", "generated");
		 */
		RSModel fineGrainModel = FeatureHandler.parseUserString(dsl_string);
		System.out.println("User string parsed");
		FeatureHandler.serializeModel(fineGrainModel, "demo.xmi");
		System.out.println("Model serialized");
		String filePath = FeatureHandler.generateFromTML("demo.xmi", "./temp/");
		Path path = Paths.get(filePath);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Baeldung-Example-Header", "Value-ResponseEntityBuilderWithHttpHeaders");
		return ResponseEntity.ok().headers(headers).contentLength(new File(filePath).length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}