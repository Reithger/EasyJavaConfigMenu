package page.feature;

import visual.composite.HandlePanel;

/**
 * 
 * Feature subclass 
 * 
 */

public class FeatureImage extends Feature {
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private String imgPath;
	
	private boolean stretchFill;

//---  Constructors   -------------------------------------------------------------------------
	
	public FeatureImage(String inTitle, int proportionHorizontal, int proportionVertical, String imagePath, boolean fill) {
		super(inTitle, proportionHorizontal, proportionVertical);
		imgPath = imagePath;
		stretchFill = fill;
	}
	
//---  Operations   ---------------------------------------------------------------------------

	@Override
	protected void draw(HandlePanel hp, int x, int y, int width, int height) {
		hp.handleImage(this.getTitle() + "_" + x + "_" + y, "basic", 5, x, y, width, height, !stretchFill, imgPath);
	}

	@Override
	public String getDataContent() {
		return imgPath;
	}

}
