package page;

/**
 * 
 * 'Loader' class that is given a ConfigPage object as reference and is then passed to the
 * user to customize the contents of the ConfigPage via Feature adding functions, then passed
 * back to the ConfigMenu (or not, object reference makes that unnecessary).
 * 
 * Will contain all of the 'addFeature' functions for ease of use via API logic for user without
 * bloating ConfigPage or ConfigMenu classes that manage other points of logic.
 * 
 * Use case:
 *  - ConfigMenu cm = new ConfigMenu("")
 *  - cm.addPage("name of page")
 *  - FeatureLoader fl = cm.getFeatureLoader("name of page")
 *  - fl.addFeature(details, of, feature, to, add)
 * 
 */

public class FeatureLoader {

	private ConfigPage page;
	
	public FeatureLoader(ConfigPage cp) {
		page = cp;
	}
	
	public void addSpacing(int row, int column, int horizontalProportion, int verticalProportion) {
		page.addFeature(new FeatureSpacing("spacer", horizontalProportion, verticalProportion), row, column);
	}
	
	public void addBasicText(String title, int row, int column, int horizontalProportion, String textDisplay) {
		page.addFeature(new FeatureBasicText(title, horizontalProportion, 1, textDisplay), row, column);
	}
	
}
