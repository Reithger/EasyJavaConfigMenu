package page;
import java.util.ArrayList;

import page.feature.Feature;
import page.feature.FeatureComposite;

/**
 * 
 * Class to model a Configuration page that will contain various features for the
 * display of data and interactions for receiving data and assigning it to a corresponding
 * config file.
 * 
 * Structurally it's a 2D array of Feature objects; each row is a visual row of data fields/
 * properties whose horizontal space is spread across the Features in that row (with assignable
 * 'proportion' values for how much proportionally to take up relative to total proportion of that row).
 * 
 * Features will contain logic on what happens when they are interacted with; may assign data in a 
 * config file, or open a new ConfigPage, or prompt an additional Feature to be added to the ConfigPage.
 * 
 * May want an interface then for ConfigPage that Features can have reference to to add a neighboring
 * Feature or a row above/below?
 * 
 * Pre-fab ConfigPages would be defined as sub-classes of ConfigPage with a simple input structure
 * to populate certain kinds/patterns of Features; we could also have a composite Feature that takes
 * simple input for laying out a 'block' of config menu for adding to a ConfigPage.
 * 
 * How to design interaction methods to enable user to customize the page with large variety of
 * options without a glut of functions that have to pass through ConfigPage and ConfigMenu classes?
 *  - ConfigMenu has 'addPageFeature(Feature obj)' function and visible factory?
 *  - Only one function for each kind of Feature and a dynamic JSON sort of object to hold all
 *    possible properties so no glut of four functions to define one type of feature
 *  - Abstracted format of data input to reference various potential Features and their properties,
 *    requires encoding system and user hyper-knowledge not readily seen in API functions
 *  - ConfigMenu has all the functions but it sends ConfigPage as a visitor to the Factory for adding
 *    the new Feature to it, doesn't bloat ConfigPage with knowledge of specific Features but bloats
 *    ConfigMenu
 *  - User-visible 'Loader' class that is given to the user with the ConfigPage packed inside which
 *    contains all of the adder functions for Features, constrains bloat to that Loader class
 * 
 * Also needs ability to add Features in theory; if a Feature is added at row -1, we store it so
 * that a behavior can reference it by name to add to the Page (variety of places to add to).
 * 
 */

public class ConfigPage {

	private String title;

	private FeatureComposite layout;
	
	private ArrayList<Feature> sidedeck;
	
	private FeatureComposite hold;
	
	public ConfigPage(String name) {
		title = name;
		layout = new FeatureComposite(title, 0, 0);
		sidedeck = new ArrayList<Feature>();
	}

	/**
	 * 
	 * Now that the 2D list of Features is just the FeatureComposite object, details on how
	 * adding a Feature to the page are present in the FeatureComposite class.
	 * 
	 * Assume 0-indexing for row and column positions, returns false if negative value is given
	 * for row or column, if row is greater than current number of rows will append rows to reach
	 * that number of rows, if column is greater than length of that row it just appends the Feature
	 * to the end of that row.
	 * 
	 * @param newFeature
	 * @param row
	 * @param column
	 * @return
	 */
	
	public boolean addFeature(Feature newFeature, int row, int column) {
		return layout.addFeature(newFeature, row, column);
	}
	
	/**
	 * 
	 * The 'side deck' contains Feature objects that we want to be able to reference later for
	 * adding to the Page via a Behavior action. They are not part of the page, they are just
	 * pre-defined by the user for later potential integration.
	 * 
	 * @param newFeature
	 */
	
	public void sideDeckFeature(Feature newFeature) {
		sidedeck.add(newFeature);
	}
	
	/**
	 * 
	 * So that the user can custom define a FeatureComposite (a miniature page, basically, treated
	 * all as one Feature), they can add on to the 'hold' FeatureComposite object bit by bit before
	 * calling 'offloadComposedFeature' to add the 'hold' object to the Page.
	 * 
	 * This function just takes the provided Feature object and adds it to the 'hold' object.
	 * 
	 * @param newFeature
	 * @param row
	 * @param column
	 * @return
	 */
	
	public boolean composeFeature(Feature newFeature, int row, int column) {
		if(hold == null) {
			hold = new FeatureComposite("hold", 0, 0);
		}
		return hold.addFeature(newFeature, row, column);
	}
	
	/**
	 * 
	 * Finalizes the composition of the FeatureComposite 'hold' object and adds it to the Page at
	 * the specified row and column. Then resets 'hold' to a blank FeatureComposite.
	 * 
	 * @param title
	 * @param row
	 * @param column
	 * @return
	 */
	
	public boolean offloadComposedFeature(String title, int row, int column) {
		hold.setTitle(title);
		if(addFeature(hold, row, column)) {
			hold = new FeatureComposite("hold", 0, 0);
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getTitle() {
		return title;
	}
	
}
