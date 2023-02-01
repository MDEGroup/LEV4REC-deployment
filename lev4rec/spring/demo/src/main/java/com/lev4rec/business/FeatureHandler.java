package com.lev4rec.business;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.parsetree.reconstr.impl.KeywordSerializer;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
//import org.springframework.ui.Model;
import org.xml.sax.SAXException;
import org.xtext.lev4recgrammar.first.RsDslStandaloneSetup;
//import org.xtext.example.mydsl.MyDslStandaloneSetup;

import org.xtext.lev4recgrammar.first.lowcoders.RSModel;
import org.xtext.lev4recgrammar.first.lowcoders.RandomSplitting;
import org.xtext.lev4recgrammar.first.lowcoders.UnsupervisedDataset;
import org.xtext.lev4recgrammar.first.lowcoders.FilteringRS;
import org.xtext.lev4recgrammar.first.lowcoders.LowcodersFactory;
import org.xtext.lev4recgrammar.first.lowcoders.LowcodersPackage;
import org.xtext.lev4recgrammar.first.lowcoders.AutomatedValidation;

import org.xtext.lev4recgrammar.first.lowcoders.Bayesian;
import org.xtext.lev4recgrammar.first.lowcoders.CrossValidation;
import org.xtext.lev4recgrammar.first.lowcoders.DataMiningRS;
import org.xtext.lev4recgrammar.first.lowcoders.DataMiningRSAlgorithm;
import org.xtext.lev4recgrammar.first.lowcoders.DataSource;
import org.xtext.lev4recgrammar.first.lowcoders.DataStructure;
import org.xtext.lev4recgrammar.first.lowcoders.Dataset;
import org.xtext.lev4recgrammar.first.lowcoders.DatasetManipulationLibrary;
import org.xtext.lev4recgrammar.first.lowcoders.DecisionTree;
import org.xtext.lev4recgrammar.first.lowcoders.DeepNN;
import org.xtext.lev4recgrammar.first.lowcoders.Evaluation;
import org.xtext.lev4recgrammar.first.lowcoders.FeedForwardNN;

import org.xtext.lev4recgrammar.first.lowcoders.FilteringRSAlgorithm;

import org.xtext.lev4recgrammar.first.lowcoders.PreprocessingTechnique;
import org.xtext.lev4recgrammar.first.lowcoders.PresentationLayer;
import org.xtext.lev4recgrammar.first.lowcoders.PyLibType;

import org.xtext.lev4recgrammar.first.lowcoders.RecommendationSystem;
import org.xtext.lev4recgrammar.first.lowcoders.RecurrentNN;
import org.xtext.lev4recgrammar.first.lowcoders.SupervisedDataset;
import org.xtext.lev4recgrammar.first.lowcoders.ValidationLibrary;
import org.xtext.lev4recgrammar.first.lowcoders.Variable;
import org.xtext.lev4recgrammar.first.lowcoders.WebIService;
import org.xtext.lev4recgrammar.first.lowcoders.WebInterfaceLibrary;

import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.lev4rec.dto.RSConfiguration;

import lev4rec.code.template.main.Generate;

public class FeatureHandler {

	public static RSModel loadModel(String modelPath) {

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(LowcodersPackage.eINSTANCE.getNsURI(), LowcodersPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource resource = resourceSet.getResource(URI.createURI(modelPath), true);
		RSModel model = (RSModel) resource.getContents().get(0);
		return model;
	}

	public static void generateFromTML(String modelUri, String folderS) {

		try {
			//Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put("*", new XMIResourceFactoryImpl());
			EPackage.Registry.INSTANCE.put(LowcodersPackage.eNS_URI, LowcodersPackage.eINSTANCE);
	        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(".xmi", new XMIResourceFactoryImpl());
	        

			List<String> arguments = new ArrayList<String>();
			System.out.print("\t" + "Generate all the files from the template...");
			File folder = new File(folderS);
			Generate generator = new Generate(loadModel(modelUri), folder, arguments);
			generator.doGenerate(new BasicMonitor());
			System.out.println("Generated!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public FeatureHandler() {

	}

	public RSModel generate(RSConfiguration config) throws ParserConfigurationException, SAXException, IOException {

		RSModel model = LowcodersFactory.eINSTANCE.createRSModel();
		model.setName("myRsModel");
		Dataset dataset = getDataSet(config);
		if (dataset != null)
			model.setDataset(dataset);
		RecommendationSystem recommendationSystem = getRecommendationSystem(config);
		if (recommendationSystem != null)
			model.setRecommendationSystem(recommendationSystem);

		Evaluation evaluation = getEvaluation(config);
		model.setEvaluation(evaluation);
		PresentationLayer presentationLayer = getPresentationLayer(config);
		if (presentationLayer != null)
			model.setPresentationLayer(presentationLayer);

		return model;
	}
	

	
	

	public Dataset getDataSet(RSConfiguration config) {

		Dataset dataset = null;

		if (config.isUnsupervisedDataset()) {
			dataset = LowcodersFactory.eINSTANCE.createUnsupervisedDataset();			

		}
		if (config.isSupervisedDataset()) {
			dataset =  LowcodersFactory.eINSTANCE.createSupervisedDataset();
		}
		
		if (dataset instanceof SupervisedDataset) {
			Variable var = LowcodersFactory.eINSTANCE.createVariable();	
			
			var.setName("user");
			
			DataSource data = LowcodersFactory.eINSTANCE.createFile();
			
			data.setName("file");
			
			var.setDataSource(data);
			
			//label.setDataSource(LowcodersFactory.eINSTANCE.createDataSource());
			
			((SupervisedDataset) dataset).setDependatVariable(var);
			
		}

		dataset.setName("datasetName");

		dataset.setPath("'/mypath'");
		// DataStructure
		if (dataset != null) {
			DataStructure dataStructure = null;
			if (config.isMatrix())
				dataStructure = LowcodersFactory.eINSTANCE.createMatrix();

			if (config.isGraphData())
				dataStructure = LowcodersFactory.eINSTANCE.createGraph();

			if (config.isARFF())
				dataStructure = LowcodersFactory.eINSTANCE.createARFF();

			if (config.isTree())
				dataStructure = LowcodersFactory.eINSTANCE.createTree();

			if (config.isTextualData())
				dataStructure = LowcodersFactory.eINSTANCE.createMatrix();

			if (dataStructure != null) {
				dataset.setDataStructure(dataStructure);
			}

			dataStructure.setName("data");
			dataset.setDataStructure(dataStructure);

			// Preprocessing
			List<PreprocessingTechnique> preprocessingTechniques = Lists.newArrayList();
			if (config.isNLP())
				preprocessingTechniques.add(PreprocessingTechnique.NLP);
			if (config.isFeatureScaling())
				preprocessingTechniques.add(PreprocessingTechnique.FEATURE_SCALING);
			if (config.isVectorization())
				preprocessingTechniques.add(PreprocessingTechnique.VECTORIZATION);
			if (config.isDuplicatesRemoval())
				preprocessingTechniques.add(PreprocessingTechnique.DUPLICATES_REMOVAL);
			if (config.isMissingDataManipulation())
				preprocessingTechniques.add(PreprocessingTechnique.MISSING_DATA_MANIPULATION);
			if (config.isNormalization())
				preprocessingTechniques.add(PreprocessingTechnique.NORMALIZATION);
			dataset.getPreprocessing().addAll(preprocessingTechniques);
			if (config.isPandas())
				dataset.getDatasetManipulationLibrary().add(DatasetManipulationLibrary.PANDAS);
			if (config.isNumpy())
				dataset.getDatasetManipulationLibrary().add(DatasetManipulationLibrary.NUMPY);

		}

		return dataset;

	}

	public RecommendationSystem getRecommendationSystem(RSConfiguration config) {
		RecommendationSystem recommendationSystem = null;

		// COLLABORATIVE FILTERING

		if (config.isItemBased()) {
			FilteringRS filtering = LowcodersFactory.eINSTANCE.createFilteringRS();
			filtering.setFilteringRSAlgorithm(FilteringRSAlgorithm.ITEM_BASED);
			recommendationSystem = filtering;
		}
		if (config.isUserBased()) {
			FilteringRS filtering = LowcodersFactory.eINSTANCE.createFilteringRS();
			filtering.setFilteringRSAlgorithm(FilteringRSAlgorithm.USER_BASED);
			recommendationSystem = filtering;
		}

		if (config.isContentBased()) {
			FilteringRS filtering = LowcodersFactory.eINSTANCE.createFilteringRS();
			filtering.setFilteringRSAlgorithm(FilteringRSAlgorithm.CONTENT_BASED);
			recommendationSystem = filtering;
		}

		// CLASSIFICATION
		/*
		 * if (config.isSVM()) { MachineLearningBasedRS machineLearning =
		 * LowcodersFactory.eINSTANCE.createMachineLearningBasedRS();
		 * machineLearning.setMachineLearningRSAlgoithm(MachineLearningBasedRS.SVM);
		 * recommendationSystem = machineLearning; } if (config.isMNB()) {
		 * MachineLearningBasedRS machineLearning =
		 * LowcodersFactory.eINSTANCE.createMachineLearningBasedRS();
		 * machineLearning.setMachineLearningRSAlgoithm(MachineLearningBasedRS.MNB);
		 * recommendationSystem = machineLearning; // }
		 */

		if (config.isSupervisedRNN() || config.isUnsupervisedRNN()) {
			RecurrentNN machineLearningBasedRS = LowcodersFactory.eINSTANCE.createRecurrentNN();
			recommendationSystem = machineLearningBasedRS;
		}
		if (config.isSupervisedDNN() || config.isUnsupervisedDNN()) {

			DeepNN machineLearningBasedRS = LowcodersFactory.eINSTANCE.createDeepNN();

			recommendationSystem = machineLearningBasedRS;
		}

		if (config.isSupervisedFeedForwardNN() || config.isUnsupervisedFeedForwardNN()) {
			FeedForwardNN machineLearningBasedRS = LowcodersFactory.eINSTANCE.createFeedForwardNN();
			recommendationSystem = machineLearningBasedRS;
		}

		if (config.isBayesianNN()) {
			Bayesian machineLearningBasedRS = LowcodersFactory.eINSTANCE.createBayesian();
			recommendationSystem = machineLearningBasedRS;
		}
		if (config.isDecisionTree()) {
			DecisionTree machineLearningBasedRS = LowcodersFactory.eINSTANCE.createDecisionTree();
			recommendationSystem = machineLearningBasedRS;
		}

//		if (isSelected(configuration, "KNeighborhood")) {
//			MachineLearningBasedRS machineLearningBasedRS = LowcodersFactory.eINSTANCE.createMachineLearningBasedRS();
//			machineLearningBasedRS.setMachineLearningRSAlgoithm(MachineLearningRSAlgorithm.);
//			recommendationSystem = machineLearningBasedRS;
//		}
		if (config.isMiningAlgorithm()) {
			DataMiningRS dataMiningRS = LowcodersFactory.eINSTANCE.createDataMiningRS();
			dataMiningRS.setDataMiningRSAlgorithm(DataMiningRSAlgorithm.CLUSTERING);
			recommendationSystem = dataMiningRS;
		}

		recommendationSystem.setName("recsys");
		if (config.isSklearn()) {
			recommendationSystem.setGenerator(PyLibType.SKLEARN);
		}
		if (config.isSurprise()) {
			recommendationSystem.setGenerator(PyLibType.SURPRISE);
		}
		if (config.isTensorFlow()) {
			recommendationSystem.setGenerator(PyLibType.TENSOR_FLOW);
		}
		if (config.isPyTorch()) {
			recommendationSystem.setGenerator(PyLibType.PYTORCH);
		}
		// recommendationSystem.setGenerator(value);
		return recommendationSystem;
	}

	public Evaluation getEvaluation(RSConfiguration config) {
		Evaluation evaluation = LowcodersFactory.eINSTANCE.createEvaluation();
		evaluation.setName("eval");
		// AutomatedValidation automatedValidation = null;
		if (config.isSplittingKfold()) {

			CrossValidation crossValidation = LowcodersFactory.eINSTANCE.createCrossValidation();
			if (config.isSKCrossFold() || config.isSKRandomSplit())
				crossValidation.setLibrary(ValidationLibrary.SKLEARN);
			if (config.isSurpriseCrossFold() || config.isSurpriseRandomSplit())
				crossValidation.setLibrary(ValidationLibrary.SURPRISE);
			crossValidation.setName("cross");
			evaluation.getValidationTechnique().add(crossValidation);
		}
		if (config.isRandomSpltting()) {
			RandomSplitting randomValidation = LowcodersFactory.eINSTANCE.createRandomSplitting();
			if (config.isSKCrossFold() || config.isSKRandomSplit())
				randomValidation.setLibrary(ValidationLibrary.SKLEARN);
			if (config.isSurpriseCrossFold() || config.isSurpriseRandomSplit())
				randomValidation.setLibrary(ValidationLibrary.SURPRISE);
			randomValidation.setName("random");
			evaluation.getValidationTechnique().add(randomValidation);
		}
		// if (isSelected(configuration, "UserStudy"))
		// evaluation.getValidationTechnique().add(LowcodersFactory.eINSTANCE.createUserStudy());
		return evaluation;
	}

	public PresentationLayer getPresentationLayer(RSConfiguration config) {
		PresentationLayer presentationLayer = null;
		if (config.isWebInterface()) {
			WebIService webInterface = LowcodersFactory.eINSTANCE.createWebIService();
			if (config.isFlask())
				webInterface.setLibrary(WebInterfaceLibrary.FLASK);
			if (config.isSpring())
				webInterface.setLibrary(WebInterfaceLibrary.SPRING);
			presentationLayer = webInterface;
		}

		if (config.isIDEPlugin()) {
			presentationLayer = LowcodersFactory.eINSTANCE.createIDEIntegration();
		}
		if (config.isRawOutcomes())
			presentationLayer = LowcodersFactory.eINSTANCE.createRawOutcomes();

		presentationLayer.setName("presentation_layer");
		return presentationLayer;
	}

	public static void serializeModel(RSModel wm, String fileName) {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		Resource resource = resSet.createResource(URI.createURI(fileName));
		resource.getContents().add(wm);

		// now save the content.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Resource writeXtextString(RSConfiguration rsConf, String path) {

		Injector injector = new RsDslStandaloneSetup().createInjectorAndDoEMFRegistration();

		ResourceSet rs = injector.getInstance(ResourceSet.class);
		Resource r = rs.createResource(URI.createURI(path));
		FeatureHandler fh = new FeatureHandler();
		RSModel m = LowcodersFactory.eINSTANCE.createRSModel();

		try {
			m = fh.generate(rsConf);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		r.getContents().add(m);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return r;

	}

	public String getXtexString(RSConfiguration config) throws IOException {
		// TODO Auto-generated method stub
		Resource r = writeXtextString(config, "lev4rec/generated/demo.rec"); // estensione dsl
		// System.out.println(r.getURI());

		File file = new File(r.getURI().toString());
		System.out.println(file.getAbsolutePath());
		String content = FileUtils.readFileToString(file, "UTF-8");

		return content;
	}

	public static RSModel fromStringToModel(String user) {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(LowcodersPackage.eINSTANCE.getNsURI(), LowcodersPackage.eINSTANCE);

		Resource resource = resourceSet.createResource(URI.createURI("lev4rec/generated/demo.rec"));
		InputStream in = new ByteArrayInputStream(user.getBytes());
		try {
			resource.load(in, resourceSet.getLoadOptions());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RSModel model = (RSModel) resource.getContents().get(0);

		return model;
	}

	public static RSModel parseUserString(String dslString) {

		String cleanSplitted = dslString.replace("X", "");
		RSModel model = fromStringToModel(cleanSplitted);

		return model;

	}

}
