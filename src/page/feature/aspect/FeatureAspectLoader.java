package page.feature.aspect;

import page.AspectAssigner;

/**
 * 
 * Builder class to construct a wrapper pattern object which contains numerous instructions
 * to add additional effects/components to how Features draw themselves.
 * 
 * Basically you can tell a Feature to also draw boundary lines around itself when it is drawn,
 * or any other types of effects implemented via the FeatureAspect interface.
 * 
 * This Loader works by having one FeatureAspect on hand that you can 'load' aspects onto, and then
 * attach it to a Feature referenced by its String identifier. The FeatureAspect on hand stays
 * as-built until you call resetAspect(), so you can mass-apply the same aspect to multiple Features.
 * 
 * Extensions:
 *  - could have a hash-map of multiple built aspects instead of one you have to reset
 *  - have a way to auto-apply the current FeatureAspect to any Features added via the FeatureLoader
 * 
 */

public class FeatureAspectLoader {

//---  Instance Variables   -------------------------------------------------------------------
	
	private AspectAssigner asspector;
	
	private FeatureAspect hold;
	
	private boolean applyToAll;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public FeatureAspectLoader(AspectAssigner assign ) {
		asspector = assign;
		applyToAll = false;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void attachFeatureAspect(String featureName) {
		asspector.assignFeatureAspect(hold, featureName);
	}
	
	public void resetAspect() {
		hold = null;
	}
	
	public void applyAspectToAll() {
		applyToAll = true;
	}
	
	public void applyAspectManually() {
		applyToAll = false;
	}
	
//---  Aspect Generators   --------------------------------------------------------------------
	
	public void loadAspectLineSurround() {
		loadAspectLineRight();
		loadAspectLineLeft();
		loadAspectLineTop();
		loadAspectLineBottom();
	}
	
	public void loadAspectLineRight() {
		attachAspect(new AspectLine(AspectLine.LINE_RIGHT));
	}

	public void loadAspectLineLeft() {
		attachAspect(new AspectLine(AspectLine.LINE_LEFT));
	}
	
	public void loadAspectLineTop() {
		attachAspect(new AspectLine(AspectLine.LINE_TOP));
	}
	
	public void loadAspectLineBottom() {
		attachAspect(new AspectLine(AspectLine.LINE_BOTTOM));
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public boolean getApplyToAll() {
		return applyToAll;
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	private void attachAspect(FeatureAspect next) {
		if(hold == null) {
			hold = next;
		}
		else {
			hold.add(next);
		}
	}
	
}
