package page.behavior;

import page.feature.Feature;

public interface SideboardAccessor {

	/**
	 * Grants the SideboardAccessor implementor a Feature object accessed from the sideboard
	 * in the ConfigPage that's title matches the String returned by the implementor's
	 * implementation of the getFeatureTitle() method.
	 * 
	 * @param grant
	 */
	
	public abstract void allocateSideboardFeature(Feature grant);
	
	public abstract String getFeatureTitle();
	
}
