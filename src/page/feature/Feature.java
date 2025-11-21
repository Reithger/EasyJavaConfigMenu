package page.feature;

import page.feature.aspect.FeatureAspect;
import visual.composite.HandlePanel;

/**
 * A feature is anything displayed on a page, be it uninteractible text/data or a variety
 * of entry fields.
 * 
 * Features have a drawing instruction given a HandlePanel and their relative width/height to draw in.
 * 
 * Features also have a proportion value for how much space they should take up relative to the total
 * proportion of all Features in that row or column for divying up available space (albeit for vertical
 * we could scrollwheel for infinite height so maybe there's a given cap of 10 proportion vertically
 * so we can know how much space we're reserving).
 * 
 * For verticality, proportion is just how many rows beneath that Feature it extends into.
 * 
 * Features also have a 'do something' component that can reference data stored in other Features to
 * write to a config file or access config data for display.
 *  - Should the Feature just be built with certain data, or with a reference to auto-access that data?
 *  - The reference approach lets me make another config file with metadata to populate large text bodies.
 * 
 * Options include:
 *  - simple text display
 *  - paragraph text display
 *  - simple data (string) entry
 *  - slider bar numeric value entry (0 - 100 setting e.g.)
 *  - simple image display
 *  - file selector (with popup file navigator)
 *  - composite of other features (effectively a sub-page)
 *  - blank space as filler for formatting
 * 
 * So, a Feature has:
 *  - a title for referencing it
 *  - horizontal proportion
 *  - vertical proportion
 *  - an overriden drawing function that takes HandlePanel and calculated width/height
 *  - conditionally, a 'behavior' object for what happens if interacted with (if a button e.g.)
 *  - conditionally, reference to the ConfigPage interface for accessing data stored in other Features
 *  - its own constructor for relevant data specific to itself
 * 
 * Behaviors:
 *  - add a new Feature to the page, either:
 *   - next to it (ideally identify a blank space Feature to take over)
 *   - immediately below it (add a new list of Features to page)
 *   - at end of current page
 *  - remove a Feature(s) from the page via reference (if row becomes empty delete it)
 *  - update config file data with data referenced from another Feature
 *   - prefab composites of 'label' - 'data entry' - 'submit button' simplify this
 * 
 */

public abstract class Feature {

	private FeatureAspect wrap;
	
	private String title;
	
	private int horzProportion;
	
	private int vertProportion;
	
	public Feature(String inTitle, int proportionHorizontal, int proportionVertical) {
		title = inTitle;
		horzProportion = proportionHorizontal;
		vertProportion = proportionVertical;
	}
	
	public void handleDraw(HandlePanel hp, int x, int y, int width, int height) { 
		if(wrap != null) {
			wrap.draw(hp, x, y, width, height);
		}
		draw(hp, x, y, width, height);
	}
	
	public void attachAspect(FeatureAspect in) {
		if(wrap == null) {
			wrap = in;
		}
		else {
			wrap.add(in);
		}
	}
	
	protected abstract void draw(HandlePanel hp, int x, int y, int width, int height);
	
	/**
	 * Basic getter function to retrieve any data content of a Feature as a String object;
	 * this is highly variable in its format as some Features don't store dynamic data but
	 * may have some relevant information if auto-populated from a file.
	 * 
	 * @return
	 */
	
	public abstract String getDataContent();
	
	/**
	 * Variation of getDataContent which take an additional String identifier; its a redundant
	 * check for most Features, as it will just ensure that the getTitle() of a Feature matches
	 * the String identifier provided.
	 * 
	 * However, for a FeatureComposite, it will prompt it to check all of its composite Features
	 * for one that matches the given identifier and return its data, potentially filtering down
	 * into another FeatureComposite's Features.
	 * 
	 * Default implemented as re-directing to the regular getDataContent but overriden by
	 * FeatureComposite.
	 * 
	 * @param identifier
	 * @return
	 */
	
	public String getDataContent(String identifier) {
		return getDataContent();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String in) {
		title = in;
	}
	
	public int getHorizontalProportion() {
		return horzProportion;
	}
	
	public int getVerticalProportion() {
		return vertProportion;
	}
	
}
