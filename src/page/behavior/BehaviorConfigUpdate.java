package page.behavior;

import file.SpecificPropertyAccessor;
import page.FeatureContentReader;

/**
 * 
 * This type of Behavior updates the data stored in a Config File.
 * 
 * Its constructor takes a reference to a Feature to pull its stored data (assuming it's a text input
 * box e.g.) and the config property to update.
 * 
 * This class needs access to a resource puller for its ConfigPage (get data via Feature Identifier)
 * and a SpecificFileAccessor for the correct config file whose property this updates.
 * 
 */

public class BehaviorConfigUpdate extends Behavior implements PropertyAccessor, FeatureReader{
	
	private SpecificPropertyAccessor fileManip;
	
	private FeatureContentReader fcr;

	public BehaviorConfigUpdate(String featureReference) {
		super(featureReference);
	}

	@Override
	public boolean performAction() {
		fileManip.setConfigPropertyValue(getFeatureData());
		return false;
	}
	
	private String getFeatureData() {
		return fcr.getFeatureDataContents(getFeatureReference());
	}

	@Override
	public void assignPropertyAccessor(SpecificPropertyAccessor sfa) {
		fileManip = sfa;
	}

	@Override
	public void assignFeatureContentReader(FeatureContentReader featureReader) {
		fcr = featureReader;		
	}

}
