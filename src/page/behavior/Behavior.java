package page.behavior;

/**
 * 
 * Behavior objects are attached to certain Features to denote what to do when they are activated.
 * 
 * This would be a button being clicked, for example.
 * 
 * Three types of Behaviors:
 *  - causes a new Feature to be added to the page
 *   - requires reference to a Feature's name
 *   - requires access to the ConfigPage side-deck
 *   - also needs instruction on where to add that Feature
 *    - adjacent, above, below, end of page, etc.
 *    - substitute a particular Feature on the page with the new Feature
 *  - removes a Feature from the page
 *   - requires identifier of the Feature to remove, whether to buffer space or not
 *    - option to buffer only horizontal space; if last Feature of a row, remove row
 *  - update data in a config file
 *   - requires identifier of the Feature to pull data content from
 *  - generate a new ConfigPage? That breaks from the Feature identifier pattern...
 *  
 * General idea of Behavior inheritance design is that any Behavior will have an ID reference to
 * at least one Feature on the page, whether to add from side deck/remove from page/pull data to
 * write to config file.
 * 
 * Behaviors can be prompted to do their action, but will define what that entails in each subclass.
 * 
 */

public abstract class Behavior {

	private String featureIdentifier;
	
	public Behavior(String featureReference) {
		featureIdentifier = featureReference;
	}
	
	public abstract boolean performAction();
	
	public String getFeatureReference() {
		return featureIdentifier;
	}
	
}
