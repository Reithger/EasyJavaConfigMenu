package page.feature.aspect;

import visual.composite.HandlePanel;

public abstract class FeatureAspect{

//---  Instance Variables   -------------------------------------------------------------------
	
	private FeatureAspect wrap;
	
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
	
	public FeatureAspect copy() {
		FeatureAspect out = this.duplicate();
		if(wrap != null) {
			out.add(wrap.copy());
		}
		return out;
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
