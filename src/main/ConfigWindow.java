package main;

import java.util.HashMap;

import file.SpecificFileAccessor;
import page.ConfigPage;
import page.ConfigSelectorPanel;
import page.PromptConfigSwap;
import visual.frame.WindowFrame;

/**
 * 
 * ConfigWindow is a manager for having multiple ConfigPages share a single WindowFrame using
 * a tab system for switching between them, but with the option that any page can be 'popped' out
 * as a solo page (which would then just be a ConfigWindow with a single page in it, no tab if
 * only one page).
 * 
 * ConfigWindow manages its one WindowFrame and will request the reference to the particular ConfigPage's
 * HandlePanel for showing/hiding it; otherwise will just call ConfigPage.draw(....) and ConfigPage
 * can handle its own Panel.
 * 
 * So:
 *  - map of page names to ConfigPage objects
 *  - string to identify current active ConfigPage
 *  
 * Will draw the current active ConfigPage and optionally display a tab select bar at the top of the Window
 * for choosing between config pages (will need another Panel for that which displaces the page to draw
 * slightly lower in the Window).
 * 
 */

public class ConfigWindow implements PromptConfigSwap{
	
	private HashMap<String, ConfigPage> pages;
	
	private String activePage;
	
	private ConfigSelectorPanel csp;
	
	private WindowFrame wf;
	
	public ConfigWindow() {
		pages = new HashMap<String, ConfigPage>();
		activePage = null;
		csp = null;
		wf = new WindowFrame(100, 100) {
			@Override
			public void repaint() {
				if(pages.size() == 0) {
					return;
				}
				int wid = wf.getWidth();
				int hei = wf.getHeight();
				if(csp != null) {
					csp.draw(wid, hei);
					pages.get(activePage).draw(0, ConfigSelectorPanel.TAB_HEIGHT, wid, hei - ConfigSelectorPanel.TAB_HEIGHT);
				}
				else {
					pages.get(activePage).draw(0, 0, wid, hei);
				}
			}
		};
		
	}
	
	public void display(int wid, int hei) {
		wf.resize(wid, hei);
		if(pages.size() == 0) {
			return;
		}
		ConfigPage cf = pages.get(activePage);
		wf.addPanel(activePage, cf.getPanelReference());
	}
	
	public void addConfigPage(ConfigPage in) {
		pages.put(in.getTitle(), in);
		wf.addPanel(activePage, in.getPanelReference());
		if(activePage == null) {
			activePage = in.getTitle();
			wf.showPanel(activePage);
		}
		if(pages.size() > 1 && csp == null) {
			csp = new ConfigSelectorPanel(0, 0, 100, 100, this);
		}
	}
	
	public void addConfigPage(String title, SpecificFileAccessor sfa) {
		addConfigPage(new ConfigPage(title, sfa));
	}
	
	public void removeConfigPage(String name) {
		if(pages.containsKey(name)) {
			boolean isActive = activePage.equals(name);
			pages.remove(name);
			if(isActive) {
				updateActivePage(pages.keySet().iterator().next());
			}
			wf.removePanel(name);
		}
	}

	@Override
	public void updateActivePage(String name) {
		if(pages.containsKey(name)) {
			wf.hidePanel(activePage);
			activePage = name;
			wf.showPanel(activePage);
		}
	}
	
	public ConfigPage getConfigPage(String name) {
		return pages.get(name);
	}

}
