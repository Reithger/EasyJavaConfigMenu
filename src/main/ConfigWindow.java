package main;

import java.awt.Color;
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
	
	private volatile String activePage;
	
	private volatile ConfigSelectorPanel csp;
	
	private volatile WindowFrame wf;
	
	public ConfigWindow() {
		pages = new HashMap<String, ConfigPage>();
		activePage = null;
		csp = null;
		wf = new WindowFrame(100, 100) {
			@Override
			public void repaint() {
				super.repaint();
				if(pages.size() == 0) {
					return;
				}
				int wid = wf.getWidth();
				int hei = wf.getHeight();
				if(csp != null) {
					csp.draw(wid, hei);
					pages.get(activePage).draw(0, csp.selectorBarHeight(), wid, hei - csp.selectorBarHeight());
				}
				else {
					pages.get(activePage).draw(0, 0, wid, hei);
				}
			}
		};
		wf.setName("Easy Java Config Menu");
		wf.setBackgroundColor(new Color(188, 188, 188));
		wf.showWindow();
	}
	
	public void display(int wid, int hei) {
		wf.resize(wid, hei);
		if(pages.size() == 0) {
			return;
		}
	}
	
	public void addConfigPage(ConfigPage in) {
		pages.put(in.getTitle(), in);
		wf.addPanel(in.getTitle(), in.getPanelReference());
		if(activePage == null) {
			activePage = in.getTitle();
		}
		else {
			wf.hidePanel(in.getTitle());
		}
		if(pages.size() > 1 && csp == null) {
			csp = new ConfigSelectorPanel(0, 0, 100, 100, this);
			csp.addConfigPageName(activePage);
			csp.addConfigPageName(in.getTitle());
			csp.setActiveConfig(activePage);
			wf.addPanel("';'Special Internal Selector';'", csp);
		}
		else if(csp != null){
			csp.addConfigPageName(in.getTitle());
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
			csp.removeConfigPageName(name);
		}
	}

	@Override
	public void updateActivePage(String name) {
		
		System.out.println(name);
		
		if(pages.containsKey(name) && !name.equals(activePage)) {
			wf.hidePanel(activePage);
			activePage = name;
			wf.showPanel(activePage);
			csp.setActiveConfig(name);
			getConfigPage(activePage).getPanelReference().requestFocusInWindow();
			//wf.hidePanel("';'Special Internal Selector';'");
			
			//TODO: For some reason we have to re-show this Panel so that other Panels don't lose the ability to be focused on.
			wf.showPanel("';'Special Internal Selector';'");
		}
	}
	
	public ConfigPage getConfigPage(String name) {
		return pages.get(name);
	}

}
