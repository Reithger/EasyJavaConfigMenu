package page;

public interface FeatureContentReader {
	
	/**
	 * Decided that all Feature objects can store some kind of dynamic data relevant to itself, not
	 * just those that take user input/interaction to decide a value field.
	 * 
	 * May just be the text assigned to it to display as an informational Feature, may be data submitted
	 * via user interaction
	 * 
	 */
	
	public abstract String getFeatureDataContents(String featureIdentifier);
	
}
