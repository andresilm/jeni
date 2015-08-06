package synalp.generation.probabilistic.guidemo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.configuration.GeneratorConfigurations;

class AppConfiguration
{

	String builtinTestsuiteName;
	boolean isBuiltinTest;

	private GeneratorConfiguration generationConfig;
	Map<String, String> resourcesFiles;
	Map<String, Boolean> infoToOutput;
	private int userInputType=0;//userInput or testsuite
	String userInput;
	private int beamSize;

	private boolean verboseOutput;
	AppConfiguration()
	{
		this.resourcesFiles = new HashMap();
		//load bundled sources for having basic configuration, then replace input files with the desired ones
		this.setGenerationConfig(GeneratorConfigurations.getConfig("probabilistic"));
		this.resourcesFiles.put("grammar", getGenerationConfig().getGrammarFile().getAbsolutePath());
		this.resourcesFiles.put("lexicon", getGenerationConfig().getSyntacticLexiconFile().getAbsolutePath());
		this.resourcesFiles.put("testsuite", getGenerationConfig().getTestsuiteFile().getAbsolutePath());
		
		this.infoToOutput = new HashMap();
		this.isBuiltinTest = false;
		this.setVerboseOutput(false);

	}


	void setBeamSize(String beam)
	{
		getGenerationConfig().setOption("beam_size", beam);
	}


	void createConfigFromTestsuite(String configName)
	{

		this.resourcesFiles.put("grammar", getGenerationConfig().getGrammarFile().getName());
		this.resourcesFiles.put("lexicon", getGenerationConfig().getSyntacticLexiconFile().getName());
		this.resourcesFiles.put("testsuite", getGenerationConfig().getTestsuiteFile().getName());
		this.isBuiltinTest = true;
		this.builtinTestsuiteName = configName;

	}


	void setConfiguration(String grammarFilename, String lexiconFilename)
	{

		getGenerationConfig().setGrammarFile(new File(grammarFilename));
		this.resourcesFiles.put("grammar", grammarFilename);

		getGenerationConfig().setSyntacticLexiconFile(new File(lexiconFilename));
		this.resourcesFiles.put("lexicon", lexiconFilename);
	}


	void setConfiguration(String grammarFilename, String lexiconFilename, String testsuiteFilename)
	{
		File grammarFile = new File(grammarFilename);
		getGenerationConfig().setGrammarFile(grammarFile);
		this.resourcesFiles.put("grammar", grammarFilename);

		getGenerationConfig().setSyntacticLexiconFile(new File(lexiconFilename));
		this.resourcesFiles.put("lexicon", lexiconFilename);

		getGenerationConfig().setTestSuiteFile(new File(testsuiteFilename));
		this.resourcesFiles.put("testsuite", testsuiteFilename);
	}


	void setUserInput(int type, String theInput) throws IOException
	{
		this.setUserInputType(type);
		this.userInput = theInput;
		createAndConfigureInputFileForSemantics(theInput);
	}
	
	void createAndConfigureInputFileForSemantics(String inputSemantics) throws IOException
	{
		BufferedWriter buffOutput = new BufferedWriter(new FileWriter(new File("customSemanticsTmpFile.geni")));
		buffOutput.write("test-user-input\n");
		buffOutput.write("semantics:[" + inputSemantics + "]\n");
		buffOutput.close();
		this.setTestsuiteSource("customSemanticsTmpFile.geni");
		
	}

	String getGrammarSource()
	{

		return this.resourcesFiles.get("grammar");

	}


	String getLexiconSource()
	{

		return this.resourcesFiles.get("lexicon");

	}


	void setTestsuiteSource(String filename)
	{
		this.resourcesFiles.put("testsuite", filename);
		getGenerationConfig().setTestSuiteFile(new File(filename));
	}


	String getTestsuiteSource()
	{

		return this.resourcesFiles.get("testsuite");
	}


	void setLexiconSource(String filename)
	{
		this.resourcesFiles.put("lexicon", filename);
	}


	void setGrammarSource(String filename)
	{
		this.resourcesFiles.put("grammar", filename);
	}


	/**
	 * @return the verboseOutput
	 */
	boolean isVerboseOutput()
	{
		return verboseOutput;
	}


	/**
	 * @param verboseOutput the verboseOutput to set
	 */
	void setVerboseOutput(boolean verboseOutput)
	{
		this.verboseOutput = verboseOutput;
	}


	/**
	 * @return the userInputType
	 */
	int getUserInputType()
	{
		return userInputType;
	}


	/**
	 * @param userInputType the userInputType to set
	 */
	void setUserInputType(int userInputType)
	{
		this.userInputType = userInputType;
	}


	GeneratorConfiguration getGenerationConfig()
	{
		return generationConfig;
	}


	void setGenerationConfig(GeneratorConfiguration generationConfig)
	{
		this.generationConfig = generationConfig;
	}


	int getBeamSize()
	{
		return beamSize;
	}


	void setBeamSize(int beamSize)
	{
		this.beamSize = beamSize;
	}

}