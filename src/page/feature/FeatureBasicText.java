package page.feature;

import java.awt.Font;

import visual.composite.HandlePanel;
import visual.panel.ElementLoader;

public class FeatureBasicText extends Feature{
	
//---  Constant Values   ----------------------------------------------------------------------
	
	private final static Font DEFAULT_FONT = new Font("Sans Serif", Font.BOLD, 12);
	
	private final static int SCROLLBAR_WIDTH = 15;
	
	private final static int X_OFFSET = 4;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private String text;
	/** boolean value set to denote whether the text elements for this feature should remove themselves to then be replaced */
	private boolean replace;
	/** boolean value denoting whether each line of text drawn should be individually centered according to the Feature's width*/
	private boolean centerX;
	
	private boolean centerY;

//---  Constructors   -------------------------------------------------------------------------
	
	public FeatureBasicText(String inTitle, int proportionHorizontal, int proportionVertical, String displayText) {
		super(inTitle, proportionHorizontal, proportionVertical);
		text = displayText == null ? "" : displayText;
		centerX = true;
		centerY = true;
	}

	public FeatureBasicText(String inTitle, int proportionHorizontal, int proportionVertical, String displayText, boolean leftAlign, boolean topAlign) {
		super(inTitle, proportionHorizontal, proportionVertical);
		text = displayText == null ? "" : displayText;
		centerX = leftAlign;
		centerY = topAlign;
	}
	
//---  Operations   ---------------------------------------------------------------------------

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		int fontHeight = hp.getTextHeight(DEFAULT_FONT);
		if(replace) {
			replace = false;
			hp.removeElementPrefixed(basicName(x, y));
		}
		ElementLoader el = hp.accessElementLoader();
		int textWid = hp.getTextWidth(text, DEFAULT_FONT);
		int rows = textWid / width;
		if(rows * fontHeight >= height) {
			el.addText(basicName(x, y), 5, "basic", x - width / 2 + X_OFFSET, y - height / 2, width - (SCROLLBAR_WIDTH + 3), height, text + "\n .", DEFAULT_FONT, false, false, false);
			hp.addElementToGroup(basicName(x, y), "group_" + x + "_" + y);
			hp.setGroupDrawOutsideWindow("group_" + x + "_" + y, false);
			hp.handleScrollbar(basicName(x, y) + "_scroll", "basic", "group_" + x + "_" + y, 2, x + width / 2 - SCROLLBAR_WIDTH, y - height / 2, SCROLLBAR_WIDTH, height, y - height / 2, height, true);
		}
		else {
			el.addText(basicName(x, y), 5, "basic", x, y, width, height, text, DEFAULT_FONT, centerX, centerY, true);	
		}
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public String getDataContent() {
		return text;
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	protected void setShowText(String in) {
		text = in;
		replace = true;
	}

	
//---  Helper Methods   -----------------------------------------------------------------------
	
	private String basicName(int x, int y) {
		return "text_display_" + x + "_" + y;
	}

}
