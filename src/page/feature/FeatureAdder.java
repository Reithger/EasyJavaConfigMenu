package page.feature;

/**
 * 
 * Interface for providing visible functions that add features to a page
 * 
 * Can add a Feature as a new row in the middle of the layout 2d arraylist
 *  - done above or below an identified Feature
 * 
 * Can add a Feature within a row at a defined row/column assuming blank space has been left there
 * 
 * Can add a Feature within a row... wherein no space has been left and we will change the row's width
 * 
 */

public interface FeatureAdder {

	/**
	 * 
	 * Function to add a Feature into a new row either above or below the indicated numeric row.
	 * 
	 * @param feat - The new Feature being added (assumedly a FeatureComposite)
	 * @param row - int value representing the index in the layout array of rows of features
	 * @param above - boolean denoting whether to add the new row above or below the row indicated by 'row'
	 * @return - returns a boolean denoting whether an error occurred with the new formatting from adding this
	 */
	
	public abstract boolean addFeatureNewRow(String referenceFeature, Feature feat, boolean above);
	
	/**
	 * 
	 * Function to add a Feature inside an existing row at a defined row/column position, with the choice
	 * for whether the new Feature should replace existing Features in that spot or push Features aside
	 * and change the overall width of that row.
	 * 
	 * Implementation should check what the new Feature's width is and eat/take up that much space.
	 * 
	 * @param feat
	 * @param row
	 * @param column
	 * @param insert - boolean; if true, new feature will push aside existing features to be added. If false, new feature will replace empty space (can return false if this fails)
	 * @param after - boolean; if true, new feature will be positioned to the right of the referenced feature; if false, to the left
	 * @return
	 */
	
	public abstract boolean addFeatureInRow(String referenceFeature, Feature feat, boolean insert, boolean after);

}
