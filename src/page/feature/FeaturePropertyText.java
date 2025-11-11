package page.feature;

import file.SpecificPropertyAccessor;
import page.behavior.PropertyAccessor;
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

public class FeaturePropertyText extends FeatureBasicText implements PropertyAccessor{

	private SpecificPropertyAccessor property;
	
	private String lastGrabbed;
	
	private int counter;
	
	private String basicDisplay;
	
	public FeaturePropertyText(String inTitle, int proportionHorizontal, int proportionVertical, String prefixText) {
		super(inTitle, proportionHorizontal, proportionVertical, "");
		lastGrabbed = "";
		basicDisplay = prefixText;
	}
	
	private void updateShowText(){
		if(property != null)
			try {
				setShowText(basicDisplay + property.getConfigPropertyValue());
			} catch (Exception e) {
				setShowText(basicDisplay + "null");
			}
	}
	
	/**
	 * 
	 * TODO: This is prolly gonna be an IO annoyance to do every draw operation, should 
	 * 
	 */
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		if(counter % 20 == 0) {
			try {
				updateShowText();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		counter++;
		super.draw(hp, x, y, width, height);
	}
	
	@Override
	public String getDataContent() {
		return lastGrabbed;
	}

	@Override
	public void assignPropertyAccessor(SpecificPropertyAccessor sfa) {
		property = sfa;		
	}

}
