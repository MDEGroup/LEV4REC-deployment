define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "ADAM|ARFF|ASSOCIATION_RULE|AdditiveFeedback|AutomatedValidation|Bayesian|Boolean|BugTrackingSystem|CATALOG_COVERAGE|CLUSTERING|CONTENT_BASED|CONTEXT_AWARE|CONTROLLED_EXPERIMENT|COSINE_SIMILARITY|CROSSOVER|CSV|CategoricalData|Click|CodeRepository|CommunicationChannel|ContextValidation|ConvolutionalNN|CrossValidation|CustomRecommender|DEMOGRAPHIC|DUPLICATES_REMOVAL|DUPLICATION|DataMiningRS|DecisionTree|DeepNN|DependencyManager|E|EGGHOLDER_FUNCTION|ELLIOT|EPC|EUCLIDEAN_DISTANCE|EVENT_STREAM|Evaluation|F1_MEASURE|FEATURE_SCALING|FIELD_STUDIES|FIT_BIT|FLASK|FREQUENT_ITEM_SET|FeedForwardNN|FeedbackComponent|File|FilteringRS|Float|GINI|GRADIENT_DESCENT|GRAPH_BASED|GUIElement|GeneticAlgorithm|Graph|GroundTruthExtraction|HILL_CLIMBING|HYBRID|HybridFeedback|IDEIntegration|ITEM_BASED|ITEM_COVERAGE|ImplicitFeedback|Integer|JACCARD_DISTANCE|LBFGS|LEVENSHTEIN_DISTANCE|LIGHTFM|LIGHTGBM|LINEAR|MISSING_DATA_MANIPULATION|MSD|MachineLearningBasedRS|Matrix|NDCG|NEGATIVE|NLP|NORMALIZATION|NOVELTY|NUMERICAL|NUMPY|ORDINAL|PANDAS|PARTIAL_HYPER_MUTATION|POLY|POSITIVE|PRECISION|PRECOMPUTED|PYTORCH|Preprocessing|ProactiveHandler|QUALITATIVE|QUANTITATIVE|RBF|RECALL|RELU|REPACE_MUTATION|RSModel|RandomSplitting|Rating|RawOutcomes|ReactiveHandler|RecommendationContext|RecommendationHandler|RecommendationSetting|RecommendationUsage|RecommendedItem|RecurrentNN|SALE_DIVERSITY|SGD|SHRINK_MUTATION|SIGMOID|SIMULATED_ANNELING|SKLEARN|SPRING|SURPRISE|SVD|SVM|Selection|String|SupervisedDataset|TANH|TENSOR_FLOW|TEXT_MINING|TFIDF|TextualContent|Transformative|TraversableGraph|Tree|UNARY|USER_BASED|UnsupervisedDataset|UserEvent|UserStudy|VECTORIZATION|VSCodePlugin|Variable|VariableRelation|Visualization|WORD_EMBEDDINGS|WebApplication|WebIService|activationFunction|alpha|analysis|baselines|columns|condition|contents|context|cutoff|dataMiningRSAlgorithm|dataSource|dataStructure|dataset|datasetManipulationLibrary|dependatVariable|description|e|encoding|evaluation|event|eventType|expressedFeedback|false|feedback|filteringRSAlgorithm|fitnessFunction|format|generator|groundTruthExtractor|guielements|handler|host|indipendentVariables|isBuiltIn|isMissingValueAllowed|isMultiple|isProactiveSystem|item|kernel|key|learningRate|library|metadata|metrics|miniBatchSize|mutationOperator|nOfRecommendations|neighborhood|nodes|numEpochs|numHiddenLayer|numOfInsertion|numberOfFold|ordered|outcame|path|port|preprocessigTechnique|preprocessing|presentationLayer|query|randomState|recommendationSystem|recommendations|recommendedItems|recommender|relations|requiredTools|rootPath|scope|searchStrategy|settings|similarityCalculator|sizeGT|solver|source|sourcePath|splittingRules|target|targetVariable|testContext|true|type|usage|usageType|userContext|validationTechnique|value|variables|webService";
		this.$rules = {
			"start": [
				{token: "comment", regex: "\\/\\/.*$"},
				{token: "comment", regex: "\\/\\*", next : "comment"},
				{token: "string", regex: '["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'},
				{token: "string", regex: "['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"},
				{token: "constant.numeric", regex: "[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"},
				{token: "lparen", regex: "[({]"},
				{token: "rparen", regex: "[)}]"},
				{token: "keyword", regex: "\\b(?:" + keywords + ")\\b"}
			],
			"comment": [
				{token: "comment", regex: ".*?\\*\\/", next : "start"},
				{token: "comment", regex: ".+"}
			]
		};
	};
	oop.inherits(HighlightRules, mTextHighlightRules.TextHighlightRules);
	
	var Mode = function() {
		this.HighlightRules = HighlightRules;
	};
	oop.inherits(Mode, mText.Mode);
	Mode.prototype.$id = "xtext/rec";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
