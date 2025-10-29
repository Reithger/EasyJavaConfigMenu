package page;
import java.util.ArrayList;

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
 */

public class ConfigPage {

	private String title;
	
	private ArrayList<ArrayList<Feature>> layout;
	
	public ConfigPage(String name) {
		title = name;
		layout = new ArrayList<ArrayList<Feature>>();
	}
	
	/**
	 * Assuming 0-indexing for counting the row and column numbers
	 * 
	 * If row value exceeds size of Feature list, add empty rows to reach that row.
	 * 
	 * If column value exceeds size of Feature row list, append Feature to end of list instead
	 * of using the requested column value. If column value is an existing index, pushes Features
	 * aside to insert at that index.
	 * 
	 * Design note to self:
	 * 
	 * Concept of using column position as stand-in for horizontal proportion introduces some
	 * awkward complexity. Row is obvious way to assign position, but do we allow column to be
	 * defined like this or imply it's a stack structure with no editing after the fact?
	 * 
	 * This isn't being drawn live so the user could fix incorrect ordering, but enforcing
	 * that style of structural design via order of operation feels flimsy. How to make it hard
	 * for the user to mis-use custom column assignation.
	 * 
	 * Basic problem is user adds a Feature at column 3, we buffer spacing for indices 0-2, then
	 * they add a Feature at column 1 with horizontal proportion 1. We can just substitute the spacing
	 * Feature at column 1 with the new Feature. Horizontal proportion 2, we still have space to substitute
	 * both spacing Features, but at proportion 3 it now pushes into column 4.
	 * 
	 * Albeit, proportion is not a mapping onto array index position, but if we buffer space in an empty
	 * array when adding a Feature to not the end of the list, translating that buffer space via index
	 * position - 
	 * 
	 * Okay, allow placement at a particular column but shorten the column value to end of list if it
	 * exceeds the actual length of the list as we cannot tell how the user wants this handled.
	 * 
	 * @param newFeature
	 * @param row
	 * @param column
	 * @return
	 */
	
	public boolean addFeature(Feature newFeature, int row, int column) {
		if(row < 0) {
			return false;
		}
		while(layout.size() < row) {
			layout.add(new ArrayList<Feature>());
		}
		if(column < 0) {
			return false;
		}
		if(layout.get(row).size() <= column) {
			layout.get(row).add(newFeature);
			return true;
		}
		else {
			layout.get(row).add(column, newFeature);
			return true;
		}
	}
	
	public String getTitle() {
		return title;
	}
	
}
