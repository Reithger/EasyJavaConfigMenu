package page.behavior;

import page.feature.FeatureFinder;

public interface LayoutAccessor {
	
	/**
	 * Grants the FeatureFinder implementor access to the top-level FeatureComposite
	 * of the ConfigPage as its implementation of the FeatureFinder interface.
	 * 
	 * @param find
	 */

	public abstract void assignLayoutAccessor(FeatureFinder find);
	
}
