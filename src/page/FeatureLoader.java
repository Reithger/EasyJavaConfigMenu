package page;

import page.feature.FeatureBasicText;
import page.feature.FeatureSpacing;
import page.feature.Feature;

/**
 * 
 * 'Loader' class that is given a ConfigPage object as reference and is then passed to the
 * user to customize the contents of the ConfigPage via Feature adding functions, then passed
 * back to the ConfigMenu (or not, object reference makes that unnecessary).
 * 
 * Will contain all of the 'addFeature' functions for ease of use via API logic for user without
 * bloating ConfigPage or ConfigMenu classes that manage other points of logic.
 * 
 * You can set the FeatureLoader to be in 3 modes:
 *  - MODE_ADD, which directly adds the specified Feature to the page at the row/column specified
 *  - MODE_SIDE_DECK, which puts the Feature you add into a side list for referencing with Features
 *    that can add or remove Features from the page
 *  - MODE_COMPOSITE, which adds the specified Feature to a FeatureComposite that is held aside
 *    until you call 'offloadComposedFeature' at which time you name the Feature, specify its row
 *    and column, and it is then added to the page as one composite feature object.
 * 
 * By default it is in MODE_ADD.
 * 
 * Use case:
 *  - ConfigMenu cm = new ConfigMenu("")
 *  - cm.addPage("name of page")
 *  - FeatureLoader fl = cm.getFeatureLoader("name of page")
 *  - fl.addFeature(details, of, feature, to, add)
 * 
 */

public class FeatureLoader {

//---  Instance Variables   -------------------------------------------------------------------
	
	private static final int MODE_ADD = 0;
	
	private static final int MODE_SIDE_DECK = 1;
	
	private static final int MODE_COMPOSITE = 2;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private ConfigPage page;
	
	private int mode;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public FeatureLoader(ConfigPage cp) {
		page = cp;
		mode = MODE_ADD;
	}
	
//---  Mode Setting   -------------------------------------------------------------------------
	
	/**
	 * 
	 * You can set the FeatureLoader to be in 3 modes:
	 *  - MODE_ADD, which directly adds the specified Feature to the page at the row/column specified
	 *  - MODE_SIDE_DECK, which puts the Feature you add into a side list for referencing with Features
	 *    that can add or remove Features from the page
	 *  - MODE_COMPOSITE, which adds the specified Feature to a FeatureComposite that is held aside
	 *    until you call 'offloadComposedFeature' at which time you name the Feature, specify its row
	 *    and column, and it is then added to the page as one composite feature object.
	 * 
	 * @return
	 */
	
	public int getMode() {
		return mode;
	}

	public void setAddToPage() {
		mode = MODE_ADD;
	}
	
	public void setAddToSideDeck() {
		mode = MODE_SIDE_DECK;
	}
	
	public void setAddToComposite() {
		mode = MODE_COMPOSITE;
	}
	
//---  Feature Adding   -----------------------------------------------------------------------

	public void addSpacing(int row, int column, int horizontalProportion, int verticalProportion) {
		Feature f = new FeatureSpacing("spacer", horizontalProportion, verticalProportion);
		handleFeature(f, row, column);
	}
	
	public void addBasicText(String title, int row, int column, int horizontalProportion, String textDisplay) {
		Feature f = new FeatureBasicText(title, horizontalProportion, 1, textDisplay);
		handleFeature(f, row, column);
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	/**
	 * 
	 * If you have been adding new Features while in MODE_COMPOSITE, this function will take the
	 * constructed composite Feature object and add it to the Page with the specified title and
	 * row/column positions.
	 * 
	 * This will also reset the FeatureComposite you had been assembling while in MODE_COMPOSITE.
	 * 
	 * @param title
	 * @param row
	 * @param column
	 */
	
	public void offloadComposedFeature(String title, int row, int column) {
		page.offloadComposedFeature(title, row, column);
	}
	
//---  Support Methods   ----------------------------------------------------------------------

	private void handleFeature(Feature in, int row, int column) {
		switch(mode) {
			case MODE_ADD:
				page.addFeature(in, row, column);
				break;
			case MODE_SIDE_DECK:
				page.sideDeckFeature(in);
				break;
			case MODE_COMPOSITE:
				page.composeFeature(in, row, column);
				break;
			default:
				break;
		}
	}
	
}
