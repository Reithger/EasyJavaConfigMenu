package page;

import java.awt.Color;
import java.util.ArrayList;

import visual.composite.HandlePanel;

/**
 * 
 * Class to represent and model a tab bar in the ConfigWindow for selecting between available
 * ConfigPages as the active page.
 * 
 * Basically just a row of buttons with text on them, each codes to return the corresponding page
 * title to let ConfigWindow know which one to swap to.
 * 
 */

public class ConfigSelectorPanel extends HandlePanel{

	private final static int START_CODE = 50;
	
	private final static int MIN_TAB_SPACE = 120;
	
	public final static int TAB_HEIGHT = 25;

	private final static Color OUTLINE_COLOR = new Color(122, 122, 122);
	
	private final static Color NON_SELECT_COLOR = new Color(188, 188, 188);
	
	private final static Color SELECTED_COLOR = new Color(55, 202, 77);
	
	private ArrayList<String> configNames;
	
	private String currActive;
	
	private PromptConfigSwap pcs;
	
	public ConfigSelectorPanel(int x, int y, int width, int height, PromptConfigSwap configSwap) {
		super(x, y, width, height);
		configNames = new ArrayList<String>();
		pcs = configSwap;
	}
	
	public void setConfigPageNames(ArrayList<String> names, String active) {
		configNames = names;
		currActive = active;
	}
	
	public void draw(int wid, int hei) {
		if(getWidth() != wid || getHeight() != hei) {
			resize(wid, hei);
			removeAllElements();
		}
		int widSpace = this.getWidth() / configNames.size();
		boolean needScroll = widSpace < MIN_TAB_SPACE;
		widSpace = needScroll ? MIN_TAB_SPACE : widSpace;
		
		this.handleScrollbar("scrollbar", "default", "tabs", 5, 0, MIN_TAB_SPACE, getWidth(), MIN_TAB_SPACE / 2, 0, getWidth(), false);
		
		int currX = 0;
		int currCode = START_CODE;
		for(String s : configNames) {
			this.handleTextButton("button_" + s, "tabs", 5, currX, 0, widSpace, TAB_HEIGHT, null, s, currCode++, s.equals(currActive) ? SELECTED_COLOR : NON_SELECT_COLOR, OUTLINE_COLOR);
			currX += widSpace;
		}
	}
	
	@Override
	public void clickEvent(int code, int x, int y, int type) {
		//TODO: This is where button code associates to a config page
		int select = code - START_CODE;
		
		if(select >= 0 && select < configNames.size()) {
			pcs.updateActivePage(configNames.get(select));
		}
	}

}
