package page.behavior;

import file.SpecificFileAccessor;

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

public class BehaviorConfigUpdate extends Behavior implements PropertyChanger{
	
	private String updateProperty;
	
	private SpecificFileAccessor fileManip;

	public BehaviorConfigUpdate(String featureReference, String configProperty) {
		super(featureReference);
		updateProperty = configProperty;
	}

	@Override
	public boolean performAction() {
		fileManip.assignData(updateProperty, getFeatureData());
		return false;
	}
	
	private String getFeatureData() {
		return null;
	}

	@Override
	public void assignFileAccessor(SpecificFileAccessor sfa) {
		fileManip = sfa;
	}

}
