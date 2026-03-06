package page.feature;

import java.awt.Font;
import java.io.File;

import file.SpecificPropertyAccessor;
import page.behavior.PropertyAccessor;
import visual.composite.HandlePanel;
import visual.panel.ElementLoader;

public class FeatureFileSelect extends Feature implements PropertyAccessor{
	
	private final static String BUTTON_IMAGE = "./assets/file_pick.png";

	private final static Font DEFAULT_FONT = new Font("Sans Serif", Font.BOLD, 12);
	
	private SpecificPropertyAccessor spa;
	
	private boolean isAnImage;
	
	private String lastSeen;
	
	private int codeVal;
	
	public FeatureFileSelect(String inTitle, int proportionHorizontal, int proportionVertical, boolean isImageDisplay, int codeValue) {
		super(inTitle, proportionHorizontal, proportionVertical);
		isAnImage = isImageDisplay;
		codeVal = codeValue;
		lastSeen = "";
	}

	@Override
	protected void draw(HandlePanel hp, int x, int y, int width, int height) {
		String path = getDataContent();
		int useX = (int)(x - width * .1);
		int useWid = (int)(width * .8);
		if(!lastSeen.equals(path)) {
			hp.removeElementPrefixed(getTitle() + "_" + x + "_" + y);
			lastSeen = path;
		}
		if(isAnImage && (path.contains(".png") || path.contains(".jpg"))) {
			hp.handleImage(this.getTitle() + "_" + x + "_" + y, "basic", 5, useX, y, useWid, height, true, path);
		}
		else {
			ElementLoader el = hp.accessElementLoader();
			int wid = hp.getTextWidth(path, DEFAULT_FONT);
			while(wid > (.8 * width)) {
				path = path.substring(2, path.length());
				wid = hp.getTextWidth("..." + path, DEFAULT_FONT);
			}
			if(!path.equals(lastSeen)) {
				path = "..." + path;
			}
			el.addText(getTitle() + "_" + x + "_" + y, 5, "basic", useX, y, useWid, height, path, DEFAULT_FONT, true, true, true);
		}
		int size = (int)(width * .1) < height ? (int)(width * .1) : height;
		hp.handleImageButton(getTitle() + "_button_" + x + "_" + y, "basic", 5, x + (int)(width * .4), y, size, size, BUTTON_IMAGE, codeVal);
	}

	@Override
	public String getDataContent() {
		try {
			return spa.getConfigPropertyValue();
		} catch (Exception e) {
			System.err.println("Feature File Select: " + getTitle() + " failed to retrieve file path associated to it");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void assignPropertyAccessor(SpecificPropertyAccessor sfa) {
		spa = sfa;
	}

}
