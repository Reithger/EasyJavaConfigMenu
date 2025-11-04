package page.feature;

import file.SpecificPropertyAccessor;
import visual.composite.HandlePanel;

/**
 * 
 * Type of Feature that is a basic text display, except the text it displays is associated
 * to the value in the config file's matching property field (this is so the user can see
 * what is in the config file and it should update to be accurate). Also should simplify
 * annoying logic of how to make sure Feature text is correct when it is supposed to
 * correspond specifically to data in a file.
 * 
 */

public class FeaturePropertyText extends FeatureBasicText{

	private SpecificPropertyAccessor property;
	
	private String lastGrabbed;
	
	private int counter;
	
	public FeaturePropertyText(String inTitle, int proportionHorizontal, int proportionVertical, SpecificPropertyAccessor propertyRef) throws Exception {
		super(inTitle, proportionHorizontal, proportionVertical, "");
		property = propertyRef;
		lastGrabbed = "";
		setShowText(property.getConfigPropertyValue());
	}
	
	/**
	 * 
	 * TODO: This is prolly gonna be an IO annoyance to do every draw operation, should 
	 * 
	 */
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		if(counter % 60 == 0) {
			try {
				setShowText(property.getConfigPropertyValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.draw(hp, x, y, width, height);
		counter++;
	}
	
	@Override
	public String getDataContent() {
		return lastGrabbed;
	}

}
