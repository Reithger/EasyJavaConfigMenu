package page;

import java.awt.Color;
import java.util.ArrayList;

import input.CustomEventReceiver;
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

//---  Constants   ----------------------------------------------------------------------------
	
	private final static int START_CODE = 50;
	
	private final static int MIN_TAB_SPACE = 120;
	
	public final static int TAB_HEIGHT = 25;

	private final static Color OUTLINE_COLOR = new Color(122, 122, 122);
	
	private final static Color NON_SELECT_COLOR = new Color(188, 188, 188);
	
	private final static Color SELECTED_COLOR = new Color(55, 202, 77);
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private ArrayList<String> configNames;
	
	private String currActive;
	
	private volatile PromptConfigSwap pcs;
	
	private boolean redraw;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public ConfigSelectorPanel(int x, int y, int width, int height, PromptConfigSwap configSwap) {
		super(x, y, width, height);
		configNames = new ArrayList<String>();
		pcs = configSwap;
		this.setPriority(1);
		CustomEventReceiver cer = new CustomEventReceiver() {
			
			@Override
			public void clickEvent(int code, int x, int y, int type) {
				int select = code - START_CODE;
				
				if(select >= 0 && select < configNames.size()) {
					pcs.updateActivePage(configNames.get(select));
					removeAllElements();
				}
				setAttention(false);
				super.clickEvent(code, x, y, type);
				
			}
			
			@Override
			public void keyEvent(char key) {
				System.out.println(key);
			}
		};
		this.setEventReceiver(cer);
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void addConfigPageName(String name) {
		configNames.add(name);
		redraw = true;
	}
	
	public void setActiveConfig(String in) {
		currActive = in;
		redraw = true;
	}
	
	public void removeConfigPageName(String name) {
		configNames.remove(name);
		redraw = true;
	}
	
	public void draw(int wid, int hei) {
		//System.out.println("CSP: " + getFocusElement());
		if(getWidth() != wid || getHeight() != hei) {
			resize(wid, hei);
			removeAllElements();
		}
		if(redraw) {
			removeAllElements();
			redraw = false;
		}
		int widSpace = this.getWidth() / configNames.size();
		boolean needScroll = widSpace < MIN_TAB_SPACE;
		widSpace = needScroll ? MIN_TAB_SPACE : widSpace;
		if(needScroll)
			handleScrollbar("scrollbar", "default", "tabs", 5, 0, TAB_HEIGHT, getWidth(), TAB_HEIGHT / 2, 0, getWidth(), false);
		
		int currX = 0;
		int currCode = START_CODE;
		for(String s : configNames) {
			this.handleTextButton("button_" + s, "tabs", 5, currX + widSpace / 2, TAB_HEIGHT / 2, widSpace, TAB_HEIGHT, null, s, currCode++, s.equals(currActive) ? SELECTED_COLOR : NON_SELECT_COLOR, OUTLINE_COLOR);
			currX += widSpace;
		}
	}
	
	public int selectorBarHeight() {
		return getWidth() / configNames.size() < MIN_TAB_SPACE ? 2 * TAB_HEIGHT : TAB_HEIGHT;
	}


}
