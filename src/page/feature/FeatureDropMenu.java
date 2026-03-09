package page.feature;

import visual.composite.HandlePanel;

/**
 * Feature subtype that provides a pop-up sublist of options to pick from to replace the
 * current option.
 * 
 * Sub-classes from PropertyText
 *  - needs visual indicator that it's a drop-down selection (tight box, down-turned chevron)
 *  - Behavior associated to respond to it being clicked on
 * 
 * @author Reithger
 *
 */

public class FeatureDropMenu extends FeaturePropertyText{

	public FeatureDropMenu(String inTitle, int proportionHorizontal, int proportionVertical) {
		super(inTitle, proportionHorizontal, proportionVertical, null);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int wid, int hei) {
		
	}


}
