package page.feature;

import visual.composite.HandlePanel;

/**
 * Feature subclass that just takes up empty space to enable positioning
 * 
 */

public class FeatureSpacing extends Feature{
	
	public final static String CAN_REMOVE = "spacer";
	
	public final static String NO_REMOVE = "fixture";

	public FeatureSpacing(String inTitle, int proportionHorizontal, int proportionVertical) {
		super(inTitle, proportionHorizontal, proportionVertical);
	}

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		return;
	}

	public String getDataContent() {
		return "";
	}

}
