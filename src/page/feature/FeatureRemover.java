package page.feature;

/**
 * 
 * Interface for FeatureComposite that only permits access to functions relating to feature removal
 * 
 * Can remove a Feature as:
 * 
 *  - an entire row is deleted
 *  
 *  - a feature is removed and replaced with equal horizontal width empty spacing
 *  
 *  - a feature is removed and the row's width is changed
 * 
 */

public interface FeatureRemover {

	/**
	 * 
	 * Function to instruct a FeatureComposite to remove the designated row from its layout.
	 * 
	 * @param row
	 * @return
	 */
	
	public abstract boolean removeFeatureRow(String referenceFeature, boolean substituteSpace);
	
	/**
	 * 
	 * Function to instruct a FeatureComposite to remove a particular feature found at the denoted
	 * row, column position (where row is index in layout arraylist and column is derived horizontal position
	 * based on the horizontal proportions of the features in that row; these values are likely to be
	 * obtained by a direct search and need not be derived manually).
	 * 
	 * User can decide whether the removed feature should be replaced with equivalent width spacing to maintain
	 * the same horizontal sizing of that row, or just remove the feature without adjusting the spacing.
	 * 
	 * @param row
	 * @param column
	 * @param replace
	 * @return
	 */
	
	public abstract boolean removeFeature(String referenceFeature, boolean replace);
	
}
