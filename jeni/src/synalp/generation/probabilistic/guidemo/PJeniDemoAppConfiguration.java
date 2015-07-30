package synalp.generation.probabilistic.guidemo;

import java.util.HashMap;
import java.util.Map;

import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.configuration.GeneratorConfigurations;

class PJeniDemoAppConfiguration
{
	String builtinTestsuiteName;
	boolean fromTestsuite;
	GeneratorConfiguration generationConfig;
	Map<String, String> resourcesFiles;
	Map<String, Boolean> infoToOutput;
	int userInputType;
	String userInput;


	PJeniDemoAppConfiguration()
	{
		this.resourcesFiles = new HashMap();
		this.infoToOutput = new HashMap();

	}


	void getConfigFromTestsuite(String configName) {
		this.generationConfig = GeneratorConfigurations.getConfig(configName);
		this.fromTestsuite = true;
		this.builtinTestsuiteName = configName;
		
	}

	void setGrammarSource(String filename)
	{
		this.resourcesFiles.put("grammar", filename);
	}


	void setLexiconSource(String filename)
	{
		this.resourcesFiles.put("lexicon", filename);
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

}