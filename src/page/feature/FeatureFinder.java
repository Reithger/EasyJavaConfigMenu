package page.feature;

public interface FeatureFinder {

	/**
	 * Within a structure containing Features, find which row the Feature
	 * matching the given String identifier is on.
	 * 
	 * @param identifier
	 * @return
	 */
	
	public abstract int findFeatureRow(String identifier);

	/**
	 * Within a structure containing Features, find which column the Feature
	 * matching the given String identifier is in (column here being the
	 * effective column position based on Features' horizontal proportions,
	 * not index position in the list of Features).
	 * 
	 * @param identifier
	 * @return
	 */
	
	public abstract int findFeatureColumn(String identifier);
	
	/**
	 * Assuming a FeatureFinder implementer contains Feature objects, it may also
	 * contain FeatureComposites which implement FeatureFinder; this function searches
	 * through the nested 'tree' of Features and FeatureComposites for whichever
	 * FeatureComposite contains the Feature denoted by the provided String identifier
	 * so that it can return that FeatureComposite as a FeatureFinder to call specific
	 * finder methods on that FeatureFinder.
	 * 
	 * Given a String name, which FeatureComposite contains the Feature for that name.
	 * 
	 * @param identifier
	 * @return
	 */
	
	public abstract FeatureFinder findPossessingComposite(String identifier);
	
}
