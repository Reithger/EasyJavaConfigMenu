package page.feature;

import visual.composite.HandlePanel;

/**
 * Feature subtype that provides a pop-up sublist of options to pick from to replace the
 * current option.
 * 
 * Sub-classes from PropertyText
 *  - needs visual indicator that it's a drop-down selection (tight box, down-turned chevron)
 *  - Behavior associated to respond to it being clicked on
 * 
 * This works by:
 *  - adding a button UI for initiating the drop-down menu
 *    - when code value is activated, the Behavior adds Aspects to this Feature for the drop-down
 *    - Aspects have code values for updating the config file data for that property
 *    - on clicking away from that Feature, Aspects are removed
 *    - will need a scroll option for the list of Aspects if too long
 * 
 * @author Reithger
 *
 */

public class FeatureDropMenu extends FeaturePropertyText{

	private int code;
	
	public FeatureDropMenu(String inTitle, int proportionHorizontal, int proportionVertical, int keyCode) {
		super(inTitle, proportionHorizontal, proportionVertical, null);
		code = keyCode;
	}
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int wid, int hei) {
		super.draw(hp, x, y, wid, hei);
		// Could use the Aspect System to add the drop-down options on this...
		/*
		 * The issue is, we need a way for it to be dynamic so that the Aspect can be added and
		 * removed rapidly by the Behavior.
		 * 
		 * Have a removal step on input that goes through all Aspects and removes those of a certain type?
		 * 
		 * Likely to be more pop-up things that could be handled via the Aspect system that we would want removed
		 * 
		 * Aspects now have a 'remove on loss of focus' boolean, Behavior can add option select aspects to this Feature
		 * when we add that new behavior
		 * 
		 */
	}


}
