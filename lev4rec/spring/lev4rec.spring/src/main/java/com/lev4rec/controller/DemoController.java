package com.lev4rec.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
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
	private String knn = "RSModel  CrossRec {\r\n"
			+ "dataset : SupervisedDataset datasetName {\r\n"
			+ "datasetManipulationLibrary [ PANDAS ]\r\n"
			+ "path '/crossRecData.csv'\r\n"
			+ "dataStructure Matrix data { } dependatVariable Variable user { type String dataSource File file { } }\r\n"
			+ "}\r\n"
			+ "presentationLayer :  WebApplication flaskApplication {\r\n"
			+ "port 5000\r\n"
			+ "host 0.0.0.0\r\n"
			+ " } evaluation : Evaluation eval {\r\n"
			+ "validationTechnique [ CrossValidation cross { \r\n"
			+ "nOfRecommendations 10 \r\n"
			+ "numberOfFold 10 } ]\r\n"
			+ "} recommendationSystem : FilteringRS recsys {\r\n"
			+ "generator SURPRISE filteringRSAlgorithm ITEM_BASED\r\n"
			+ "cutoff 5\r\n"
			+ "neighborhood 3\r\n"
			+ " }"
			+ "";
	
	private String ml = "RSModel Aurora {\r\n"
			+ "    dataset : SupervisedDataset datasetName {\r\n"
			+ "        preprocessing [ VECTORIZATION ]\r\n"
			+ "        datasetManipulationLibrary [ PANDAS ]\r\n"
			+ "        path '/SocialNetwork.csv'\r\n"
			+ "        dataStructure Matrix data { } \r\n"
			+ "        dependatVariable Variable user { \r\n"
			+ "            type String dataSource File file { } \r\n"
			+ "        }\r\n"
			+ "    }\r\n"
			+ "    presentationLayer : WebApplication flaskApplication {\r\n"
			+ "    port 5000\r\n"
			+ "    host 0.0.0.0\r\n"
			+ "     } \r\n"
			+ "    evaluation : Evaluation eval {\r\n"
			+ "        validationTechnique [ CrossValidation cross { \r\n"
			+ "        nOfRecommendations 10\r\n"
			+ "        numberOfFold 10\r\n"
			+ "         } ]\r\n"
			+ "    } \r\n"
			+ "    recommendationSystem : FeedForwardNN recsys {\r\n"
			+ "        solver ADAM\r\n"
			+ "        alpha 0.5\r\n"
			+ "        randomState 1\r\n"
			+ "    }\r\n"
			+ "}";
	private static final String TEMP_ARCHIVE_PATH = "archive.zip";
	private static final String TEMP_FOLDER_PATH = "./temp";
	private static final String TEMP_MODEL_PATH = "model.xmi";

	@Autowired
	FeatureHandler fh = new FeatureHandler();

	@RequestMapping("/")
	public String index(Model model) {
		RSConfiguration config = new RSConfiguration();
		model.addAttribute("config", config);
		return "home.html";
	}
	
	
	@RequestMapping("/knn/")
	public String indexKnn(Model model) {
		RSConfiguration config = new RSConfiguration();
		config.setCollaborativeFiltering(true);
		config.setItemBased(true);
		/*TODO Claudio qui setta l'ogetto config.*/
		model.addAttribute("config", config);
		return "homeKNN.html";
	}
	
	
	@RequestMapping("/ml/")
	public String indexML(Model model) {
		RSConfiguration config = new RSConfiguration();
		config.setCollaborativeFiltering(true);
		config.setItemBased(true);
		/*TODO Claudio qui setta l'ogetto config.*/
		model.addAttribute("config", config);
		return "homeML.html";
	}


	@RequestMapping(value = "/dsl", method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute("config") RSConfiguration config) {
		String s = "";
		try {
			s = fh.getXtexString(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("xtext", s);
		return "dsl.html";
	}
	
	
	@RequestMapping(value = "/dslKNN", method = RequestMethod.POST)
	public String saveKNN(Model model, @ModelAttribute("config") RSConfiguration config) {
		String s = "";
		try {
			s = fh.getXtexString(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("xtext", knn);
		return "dslKNN.html";
	}
	
	@RequestMapping(value = "/dslML", method = RequestMethod.POST)
	public String saveML(Model model, @ModelAttribute("config") RSConfiguration config) {
		String s = "";
		try {
			s = fh.getXtexString(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("xtext", ml);
		return "dslML.html";
	}

	@RequestMapping(path = "/generate", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> download(@RequestParam("user_string") String dsl_string)
			throws IOException {
		/*
		 * RSModel coarse_model= FeatureHandler.loadModel(TEMP_MODEL_PATH);
		 * coarse_model.setName("KNN recsys");
		 * FeatureHandler.serializeModel(coarse_model, TEMP_MODEL_PATH);
		 * FeatureHandler.generateFromTML("generated/demo.xmi", "generated");
		 */
		FileUtils.cleanDirectory(new File(TEMP_FOLDER_PATH));
		fh.generateDocker(dsl_string, TEMP_MODEL_PATH, TEMP_FOLDER_PATH, TEMP_ARCHIVE_PATH);
		Path path = Paths.get(TEMP_ARCHIVE_PATH);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Baeldung-Example-Header", "Value-ResponseEntityBuilderWithHttpHeaders");
		return ResponseEntity.ok().headers(headers).contentLength(new File(TEMP_ARCHIVE_PATH).length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}