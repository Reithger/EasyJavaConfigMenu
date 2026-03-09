package page.feature;

import page.behavior.PropertyAccessor;
import visual.composite.HandlePanel;

/**
 * Feature subtype that provides a cyclable list of entries (think how early games
 * would let you change resolution by going through the options 1-by-1)
 * 
 * Would have called this a carousel but that's too tightly associated to images
 * 
 * @author Reithger
 *
 */

public class FeatureToggleList extends FeaturePropertyText implements PropertyAccessor {

	private final static String IMAGE_INCREMENT = "./assets/ada.png";
	
	private final static String IMAGE_DECREMENT = "./assets/ada.png";
	
	private int codeDecrement;
	
	private int codeIncrement;
	
	public FeatureToggleList(String inTitle, int proportionHorizontal, int proportionVertical, int codeDown, int codeUp) {
		super(inTitle, proportionHorizontal, proportionVertical, null);
		codeDecrement = codeDown;
		codeIncrement = codeUp;
	}
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		super.draw(hp, x, y, width, height);
		// Draw the decrement and increment buttons with the code values, have FeatureLoader add the behaviors for each
		int wid = hp.getTextWidth(getDataContent(), DEFAULT_FONT);
		int hei = hp.getTextHeight(DEFAULT_FONT);
		hp.handleImageButton(getTitle() + "_button_decr_" + x + "_" + y, "basic", 5, x - wid / 2 - hei * 2, y, hei, hei, IMAGE_DECREMENT, codeDecrement);
		hp.handleImageButton(getTitle() + "_button_incr_" + x + "_" + y, "basic", 5, x + wid / 2 + hei * 2, y, hei, hei, IMAGE_INCREMENT, codeIncrement);
	}
	

}
