package page.feature.aspect;

import visual.composite.HandlePanel;

public abstract class FeatureAspect{

//---  Instance Variables   -------------------------------------------------------------------
	
	private FeatureAspect wrap;
	
	private boolean removeOnFocusReset;
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void draw(HandlePanel p, int x, int y, int width, int height) {
		aspectDraw(p, x, y, width, height);
		if(wrap != null) {
			wrap.draw(p, x, y, width, height);
		}
	}
	
	public void add(FeatureAspect feat) {
		if(wrap == null) {
			wrap = feat;
		}
		else {
			wrap.add(feat);
		}
	}
	
	/**
	 * 
	 * Recursive function to remove all Aspects that are set to be removed when
	 * a Focus Reset happens (so hover-text or a drop-down menu of options, e.g.)
	 * 
	 * Returns a FeatureAspect so that if the root changes, you have the new root
	 * reference.
	 * 
	 * I think this is just having each one either return itself or the next Aspect
	 * in line if it needs to remove itself (after calling this on the next Aspect)
	 * 
	 * @return - Returns the new root FeatureAspect after removal has occurred
	 */
	
	public FeatureAspect removeFocusReset() {
		wrap = wrap.removeFocusReset();
		if(getFocusReset()) {
			return wrap;
		}
		else {
			return this;
		}
	}
	
	public FeatureAspect copy() {
		FeatureAspect out = this.duplicate();
		if(wrap != null) {
			out.add(wrap.copy());
		}
		return out;
	}
	
	public void setFocusReset(boolean in) {
		removeOnFocusReset = in;
	}
	
	public boolean getFocusReset() {
		return removeOnFocusReset;
	}
	
	protected abstract FeatureAspect duplicate();
	
	public abstract void aspectDraw(HandlePanel p, int x, int y, int width, int height);
	
	public void print() {
		aspectPrint();
		if(wrap != null)
			wrap.print();
	}
	
	public abstract void aspectPrint();
	
}
