package synalp.generation.probabilistic.guidemo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.configuration.GeneratorConfigurations;

class AppConfiguration
{

	String builtinTestsuiteName;
	boolean isBuiltinTest;

	GeneratorConfiguration generationConfig;
	Map<String, String> resourcesFiles;
	Map<String, Boolean> infoToOutput;
	int userInputType;//userInput or testsuite
	String userInput;

	private boolean verboseOutput;
	AppConfiguration()
	{
		this.resourcesFiles = new HashMap();
		//load bundled sources for having basic configuration, then replace input files with the desired ones
		this.generationConfig = GeneratorConfigurations.getConfig("probabilistic");
		this.resourcesFiles.put("grammar", generationConfig.getGrammarFile().getAbsolutePath());
		this.resourcesFiles.put("lexicon", generationConfig.getSyntacticLexiconFile().getAbsolutePath());
		this.resourcesFiles.put("testsuite", generationConfig.getTestsuiteFile().getAbsolutePath());
		
		this.infoToOutput = new HashMap();
		this.isBuiltinTest = false;
		this.setVerboseOutput(false);
		
		

	}


	void setBeamSize(int beam)
	{
		generationConfig.setOption("beam_size", Integer.toString(beam));
	}


	void createConfigFromTestsuite(String configName)
	{

		this.resourcesFiles.put("grammar", generationConfig.getGrammarFile().getName());
		this.resourcesFiles.put("lexicon", generationConfig.getSyntacticLexiconFile().getName());
		this.resourcesFiles.put("testsuite", generationConfig.getTestsuiteFile().getName());
		this.isBuiltinTest = true;
		this.builtinTestsuiteName = configName;

	}


	void setConfiguration(String grammarFilename, String lexiconFilename)
	{

		generationConfig.setGrammarFile(new File(grammarFilename));
		this.resourcesFiles.put("grammar", grammarFilename);

		generationConfig.setSyntacticLexiconFile(new File(lexiconFilename));
		this.resourcesFiles.put("lexicon", lexiconFilename);
	}


	void setConfiguration(String grammarFilename, String lexiconFilename, String testsuiteFilename)
	{
		File grammarFile = new File(grammarFilename);
		generationConfig.setGrammarFile(grammarFile);
		this.resourcesFiles.put("grammar", grammarFilename);

		generationConfig.setSyntacticLexiconFile(new File(lexiconFilename));
		this.resourcesFiles.put("lexicon", lexiconFilename);

		generationConfig.setTestSuiteFile(new File(testsuiteFilename));
		this.resourcesFiles.put("testsuite", testsuiteFilename);
	}


	void setUserInput(int type, String theInput)
	{
		this.userInputType = type;
		this.userInput = theInput;
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

}