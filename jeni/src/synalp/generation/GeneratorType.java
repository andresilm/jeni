package synalp.generation;

/**
 * @author C�line Moro
 */
public enum GeneratorType
{
	/**
	 * Tree combining algorithm used in the Haskell version of Geni
	 */
	JENI_DEFAULT("jeni"),
	/**
	 * Tree combining algorithm used in RTGen (prolog)
	 */
	RTGEN("rtgen");

	private String value;    

	private GeneratorType(String value) {
		this.value = value;
	}

	/**
	 * Return the value of a GeneratorType
	 * @return the value of a GeneratorType
	 */
	public String toString() {
		return value;
	}


	/**
	 * Return the GeneratorType corresponding to the given name
	 * @param name of the algorithm
	 * @return the GeneratorType corresponding to the given name
	 */
	public static GeneratorType getGeneratorTypeByString(String name){
		for(GeneratorType e : GeneratorType.values()){
			if(name.equals(e.value)) return e;
		}
		return null;
	}
}
