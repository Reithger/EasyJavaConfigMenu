package page.feature;

import java.awt.Color;

import file.SpecificPropertyAccessor;
import page.behavior.PropertyAccessor;
import visual.composite.HandlePanel;

/**
 * Feature subclass to provide a simple boolean toggle
 * 
 * @author Reithger
 *
 */

public class FeatureCheckbox extends Feature implements PropertyAccessor {

	private Color BORDER_COLOR = new Color(22, 22, 22);
	
	private Color INSIDE_COLOR_TRUE = new Color(22, 255, 22);
	
	private Color INSIDE_COLOR_FALSE = new Color(255, 22, 22);
	
	private SpecificPropertyAccessor propertyAccess;
	
	private int code;
	
	private boolean lastSeen;
	
	public FeatureCheckbox(String inTitle, int proportionHorizontal, int proportionVertical, int codeVal) {
		super(inTitle, proportionHorizontal, proportionVertical);
		code = codeVal;
	}

	@Override
	protected void draw(HandlePanel hp, int x, int y, int width, int height) {
		boolean condition = false;
		try {
			String val = getDataContent();
			if (val.equals("true")) {
				condition = true;
			}
			else if(!val.equals("false")) {
				System.err.println("Feature Checkbox: " + getTitle() + " associated to propert value that is not 'true' or 'false', instead is: " + val);
			}
		} catch (Exception e) {
			System.err.println("Error in accessing property by Checkbox: " + getTitle());
			e.printStackTrace();
		}
		if(condition != lastSeen) {
			lastSeen = condition;
			hp.removeElement(getTitle() + "_checkbox");
		}
		int size = width < height ? width : height;
		size /= 3;
		hp.handleRectangle(getTitle() + "_checkbox_" + x + "_" + y, "basic", 3, x, y, size, size, condition ? INSIDE_COLOR_TRUE : INSIDE_COLOR_FALSE, BORDER_COLOR);
		hp.handleButton(getTitle() + "_checkbox_button_" + x + "_" + y, "basic", 3, x, y, size, size, code);
	}

	@Override
	public String getDataContent() {
		try {
			return propertyAccess.getConfigPropertyValue().equals("true")+"";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void assignPropertyAccessor(SpecificPropertyAccessor sfa) {
		propertyAccess = sfa;
	}

}
