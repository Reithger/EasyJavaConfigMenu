package page.feature;

import java.awt.Color;
import java.awt.Font;

import visual.composite.HandlePanel;

public class FeatureBasicText extends Feature{
	
	private final static Font DEFAULT_FONT = new Font("Sans Serif", Font.BOLD, 12);
	
	private String text;

	public FeatureBasicText(String inTitle, int proportionHorizontal, int proportionVertical, String displayText) {
		super(inTitle, proportionHorizontal, proportionVertical);
		text = displayText;
	}
	
	public String getDataContent() {
		return text;
	}
	
	protected void setShowText(String in) {
		text = in;
	}

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		hp.handleText("text_display_" + x + "_" + y, "basic", 5, x, y, width, height, DEFAULT_FONT, text, new Color(0, 0, 0));
	}

}
