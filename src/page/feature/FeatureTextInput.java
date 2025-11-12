package page.feature;

import java.awt.Color;

import visual.composite.HandlePanel;

/**
 * 
 * Type of feature that provides a text input box for the user to enter text into;
 * other Features may refer to the data content of this Feature to update config properties.
 * 
 */

public class FeatureTextInput extends Feature{
	
	private final static int CODE_START_TEXT_INPUT = 34523;
	
	private static volatile int nextCodeValue;
	
	private int codeValue;
	
	private String storedValue;
	
	public FeatureTextInput(String inTitle, int proportionHorizontal, int proportionVertical, String defaultText) {
		super(inTitle, proportionHorizontal, proportionVertical);
		if(nextCodeValue == 0) {
			nextCodeValue = CODE_START_TEXT_INPUT;
		}
		codeValue = nextCodeValue++;
		storedValue = defaultText;
	}
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		try {
			if(hp.containsElement(getElementName())) {
				storedValue = hp.getElementStoredText(getElementName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		hp.handleTextEntry(getElementName(), "basic", 6, x, y, width, height, codeValue, null, storedValue);
		hp.handleRectangle(getElementName() + "_rect", "basic", 3, x, y, width, height * 2 / 3, new Color(188, 188, 188), new Color(22, 22, 22));
	}

	private String getElementName() {
		return "text_entry_" + this.getTitle();
	}
	
	@Override
	public String getDataContent() {
		return storedValue;
	}

}
