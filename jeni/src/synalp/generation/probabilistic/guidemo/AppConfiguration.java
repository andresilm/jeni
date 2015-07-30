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
	int userInputType; 
	String userInput;


	AppConfiguration()
	{
		this.resourcesFiles = new HashMap();
		this.infoToOutput = new HashMap();
		this.isBuiltinTest = false;
		generationConfig = null;

	}
	
	void setBeamSize(int beam) {
		generationConfig.setOption("beam_size",Integer.toString(beam));
	}


	void createConfigFromTestsuite(String configName) {
		this.generationConfig = GeneratorConfigurations.getConfig(configName);
		this.resourcesFiles.put("grammar", generationConfig.getGrammarFile().getName());
		this.resourcesFiles.put("lexicon", generationConfig.getSyntacticLexiconFile().getName());
		this.resourcesFiles.put("testsuite", generationConfig.getTestsuiteFile().getName());
		this.isBuiltinTest = true;
		this.builtinTestsuiteName = configName;
		
	}

	void createConfigFrom(String grammarFilename, String lexiconFilename) {
		generationConfig.setGrammarFile(new File(grammarFilename));
		this.resourcesFiles.put("grammar", grammarFilename);
		
		generationConfig.setSyntacticLexiconFile(new File(lexiconFilename));
		this.resourcesFiles.put("lexicon", grammarFilename);
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
	
	void setLexiconSource(String filename) {
		this.resourcesFiles.put("lexicon", filename);
	}
	
	void setGrammarSource(String filename) {
		this.resourcesFiles.put("grammar", filename);
	}
	
	

}