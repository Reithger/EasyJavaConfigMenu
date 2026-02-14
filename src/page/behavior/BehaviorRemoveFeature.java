package page.behavior;

import page.feature.FeatureRemover;

/**
 * 
 * Behavior subclass for removing a particular Feature from the ConfigPage as identified by the
 * featureReference in this class' constructor.
 * 
 * User options are to remove the entire row that that Feature is on or just the Feature itself.
 * 
 * Can also decide if, upon removal, empty spacing should be inserted to replace the removed Feature
 * or if that row's horizontal spacing should be reduced by the Feature removal.
 * 
 */

public class BehaviorRemoveFeature extends Behavior implements FeatureRemoverAccessor{

	private FeatureRemover remover;
	
	private boolean removeRow;
	
	private boolean insertSpacing;
	
	/**
	 * 
	 * 
	 * @param featureReference
	 * @param killRow
	 * @param replaceSpace
	 */
	
	public BehaviorRemoveFeature(String featureReference, boolean killRow, boolean replaceSpace) {
		super(featureReference);
		removeRow = killRow;
		insertSpacing = replaceSpace;
	}

	@Override
	public boolean performAction() {
		if(removeRow) {
			return remover.removeFeatureRow(getFeatureReference(), insertSpacing);
		}
		else {
			return remover.removeFeature(getFeatureReference(), insertSpacing);
		}
	}

	@Override
	public void allocateFeatureRemoverAccess(FeatureRemover remove) {
		remover = remove;
	}

}
