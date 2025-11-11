package page.feature;

import java.awt.Color;

import visual.composite.HandlePanel;

/**
 * 
 * FeatureButton is a very important Feature as it is where most (if not all) Behavior actions
 * will occur (when the button is clicked). The underlying SVI system will make it so that a code
 * value is generated when the button is clicked and it will end up in the overrided clickEvent
 * function of the HandlePanel.
 * 
 * Thus, while a Button would be associated with a Behavior, the FeatureButton is not responsible
 * for containing or knowing any logic associated with what happens when it is clicked, it just
 * passes along the related code value that can be assigned while setting up the ConfigPage.
 * 
 * Ideally the approach, then, is that a user would add a FeatureButton via the FeatureLoader, assign
 * it a code value, and then separately add a Behavior via the FeatureLoader and associate the code
 * value from earlier with the new Behavior it generated; ConfigPage then stores the Behaviors as a
 * mapping when given the code value from the ConfigWindow.
 * 
 */

public class FeatureButton extends Feature{
	
	private final static Color COLOR_OUTLINE = new Color(66, 66, 66);
	
	private final static Color COLOR_BACKING = new Color(122, 122, 122);
	
	private String display;
	
	private int codeValue;
	
	//TODO: Probably want some consistent sizing for this that's not proportional to given wid/hei

	public FeatureButton(String inTitle, int proportionHorizontal, int proportionVertical, String displayText, int code) {
		super(inTitle, proportionHorizontal, proportionVertical);
		display = displayText;
		codeValue = code;
	}

	public String getDataContent() {
		return display;
	}

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		// Just draws a button here and associates the codeValue int to it for the SVI event handling
		hp.handleTextButton("button_" + display + "_" + codeValue, "basic", 5, x, y, width, height, null, display, codeValue, COLOR_BACKING, COLOR_OUTLINE);
	}

}
