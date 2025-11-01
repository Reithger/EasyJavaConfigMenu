package page.feature;

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
	
	private String display;
	
	private int codeValue;

	public FeatureButton(String inTitle, int proportionHorizontal, int proportionVertical, String displayText, int code) {
		super(inTitle, proportionHorizontal, proportionVertical);
		display = displayText;
		codeValue = code;
	}

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		// Just draws a button here and associates the codeValue int to it for the SVI event handling
	}

}
