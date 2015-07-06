package synalp.generation;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.kohsuke.args4j.*;

import synalp.commons.input.TestSuiteEntry;
import synalp.commons.output.*;
import synalp.commons.utils.*;
import synalp.commons.utils.ResourceBundle;
import synalp.commons.utils.configuration.*;
import synalp.commons.utils.exceptions.TimeoutException;
import synalp.generation.configuration.*;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.jeni.JeniGenerator;
import synalp.generation.ranker.NgramRanker;

import static org.kohsuke.args4j.ExampleMode.ALL;

/**
 * Main command line generator. This class enables to run the Jeni generator by writing the results
 * of each test in an output directory. It only works for the Jeni generator for now, and we may
 * change this class to use the GeneratorApplication which is meant to be more generic.
 * @author Alexandre Denis
 */
public class GeneratorMain
{
	private static Logger logger = Logger.getLogger(GeneratorMain.class);
	
	@Option(name = "-g", usage = "grammar file", metaVar = "<xml file>", required = false)
	private File grammarFile = null;

	@Option(name = "-l", usage = "lexicon file", metaVar = "<xml|lex file>", required = false)
	private File synLexiconFile = null;

	@Option(name = "-s", usage = "testsuite file in jeni format", metaVar = "<jeni file>", required = false)
	private File testSuiteFile = null;

	@Option(name = "-m", usage = "morph file", metaVar = "<xml|mph file>")
	private File morphLexiconFile = null;

	@Option(name = "-o", usage = "output directory", metaVar = "<output dir>", required = false)
	private File outputDirectory = null;

	@Option(name = "-lm", usage = "language model", metaVar = "<lm file>", required = false)
	private File lmFile = null;

	@Option(name = "-bs", usage = "beam size (default 5)", metaVar = "<integer>", required = false)
	private int beamSize = 5;

	@Option(name = "-c", usage = "configuration name, if set overrides all other parameters", metaVar = "<string>", required = false)
	private String configName = null;

	@Argument(usage = "other options, see format in default.options.properties", metaVar = "option=value")
	private List<String> options = new ArrayList<String>();


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new GeneratorMain().run(args);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}


	/**
	 * Runs the generator.
	 * @param args
	 * @throws Exception
	 */
	private void run(String[] args) throws Exception
	{
		// sets the stderr to stdout to avoid interleaved outputs in console mode
		// we may disable it in the future
		System.setErr(System.out);

		CmdLineParser parser = new CmdLineParser(this);
		parser.setUsageWidth(80);

		try
		{
			parser.parseArgument(args);
			if (configName == null)
				runNoConfig(parser);
			else runWithConfig();
		}
		catch (CmdLineException e)
		{
			displayUsage(parser, e);
			return;
		}

	}


	private void runWithConfig() throws IOException
	{
		GeneratorConfigurations allConfigs = GeneratorConfigurations.createConfigurations();
		if (!allConfigs.containsKey(configName))
			throw new ConfigurationException("Error: unable to load configuration '" + configName + "', it is not found in existing configurations (" + allConfigs.keySet() + ")");

		GeneratorConfiguration config = allConfigs.get(configName);
		System.out.println(config.printConfiguration());
		JeniGenerator generator = new JeniGenerator(config);
		
		if (logger.isInfoEnabled())
			logger.info(GeneratorOption.getStatus());
		
		int index = 1;
		for(TestSuiteEntry entry : config.getTestSuite())
		{
			System.out.println("Processing " + (index++) + "/" + config.getTestSuite().size());
			run(generator, entry);
		}
	}


	private void runNoConfig(CmdLineParser parser) throws Exception
	{
		if (outputDirectory == null || grammarFile == null || testSuiteFile == null)
			throw new CmdLineException(parser, "Error: missing output directory, grammar or testsuite");

		if (outputDirectory.isFile() && !outputDirectory.isDirectory())
			throw new Exception("Unable to write to output directory " + outputDirectory + ", it is not a directory");

		System.out.println("grammar: " + grammarFile);
		System.out.println("test suite: " + testSuiteFile);
		System.out.println("syn lexicon: " + synLexiconFile);
		System.out.println("morph lexicon: " + morphLexiconFile);
		System.out.println("lm file: " + lmFile);
		System.out.println("output dir: " + outputDirectory);
		System.out.println("options: " + options);
		System.out.println("lib: " + System.getProperty("java.library.path"));

		setOptions();

		ResourceBundle bundle = new ResourceBundle(grammarFile, synLexiconFile, testSuiteFile, morphLexiconFile);
		bundle.load();

		Utils.delete(outputDirectory);
		outputDirectory.mkdirs();

		JeniGenerator generator = lmFile == null ? new JeniGenerator(bundle) : new JeniGenerator(bundle, new NgramRanker(lmFile.getAbsolutePath(), beamSize,
																															"jni"));
		int index = 1;
		for(TestSuiteEntry entry : bundle.getTestSuite())
		{
			System.out.println("Processing " + (index++) + "/" + bundle.getTestSuite().size());
			run(generator, entry);
		}

	}


	/**
	 * Runs the generator on the given entry and writes the result to a file in the output
	 * directory.
	 * @param generator the generator
	 * @param entry
	 * @throws IOException
	 */
	private void run(Generator generator, TestSuiteEntry entry) throws IOException
	{
		String name = entry.getId();
		if (name.length() > 255)
			name = name.substring(0, 255);

		File outputDir = new File(outputDirectory + File.separator + name);
		Utils.delete(outputDir);
		outputDir.mkdirs();

		RandomAccessFile output = new RandomAccessFile(outputDir + File.separator + "responses", "rw");
		List<? extends SyntacticRealization> results;

		Perf.logStart("generation");
		try
		{
			results = generator.generate(entry.getSemantics());
			System.out.println("\t" + results.size() + " results (" + Perf.formatTime(Perf.logEnd("generation")) + ")");
		}
		catch (TimeoutException e)
		{
			results = new ArrayList<SyntacticRealization>();
			System.out.println("\tTimeout (" + Perf.formatTime(Perf.logEnd("generation")) + ")");
		}

		for(SyntacticRealization result : results)
			for(MorphRealization sentence : result.getMorphRealizations())
				output.writeBytes(sentence.asString() + "\n");
		output.close();
	}


	/**
	 * Sets the options in GeneratorOptions from the options field.
	 * @throws Exception
	 */
	private void setOptions() throws Exception
	{
		StringBuilder str = new StringBuilder();
		for(String option : options)
			str.append(option).append("\n");
		ByteArrayInputStream stream = new ByteArrayInputStream(str.toString().getBytes());

		Properties props = new Properties();
		props.load(stream);
		stream.close();
		System.err.println("!Loading options like that is deprecated and will be removed!");
		GeneratorOptions.initialize(props);
	}


	/**
	 * Shows usage.
	 * @param parser
	 * @param e
	 */
	private void displayUsage(CmdLineParser parser, CmdLineException e)
	{
		System.err.println(e.getMessage());
		System.err.println("./run.sh [options...] arguments...");
		parser.printUsage(System.err);
		System.err.println();
		System.err.println("  Example: java GeneratorMain" + parser.printExample(ALL) + " TIMEOUT=1000 CASE_DEPENDENT=true");
	}
}
