package page.feature.aspect;

import visual.composite.HandlePanel;

/**
 * Aspect sub-type to add a Drop-down hover menu underneath a Feature; each option
 * box is another AspectDropdownBlock that has an incrementing count to know how
 * far below it needs to be drawn.
 * 
 * Needs a code value as well so that a Behavior can be procced by this being clicked
 * on to update the property in the config file.
 * 
 */

public class AspectDropdownBlock extends FeatureAspect {

	private String option;
	
	private int drawCount;
	
	public AspectDropdownBlock(String optionText, int depth) {
		option = optionText;
		this.setFocusReset(true);
		drawCount = depth;
	}
	
	@Override
	protected FeatureAspect duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aspectDraw(HandlePanel p, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aspectPrint() {
		// TODO Auto-generated method stub
		
	}

}
