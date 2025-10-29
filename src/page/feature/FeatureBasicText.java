package page.feature;

import java.awt.Font;

import visual.composite.HandlePanel;

public class FeatureBasicText extends Feature{
	
	private final static Font DEFAULT_FONT = new Font("Sans Serif", 12, Font.BOLD);
	
	private String text;

	public FeatureBasicText(String inTitle, int proportionHorizontal, int proportionVertical, String displayText) {
		super(inTitle, proportionHorizontal, proportionVertical);
		text = displayText;
	}

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		hp.addText("text_display_" + x + "_" + y, 5, "basic", x, y, width, height, text, DEFAULT_FONT, false, false, true);
	}

}
