package page.behavior;

/**
 * Sub-class of Behavior that provides the ability to add a Feature as defined by string identifier
 * to the ConfigPage this Behavior has been associated to (and accordingly the code value as well).
 * 
 * The Feature this Behavior references will be in the ConfigPage's side deck, and in most cases should
 * be a more complicated FeatureComposite object for a bulk-adding operation.
 * 
 *  The question of where the Feature should be added to is complicated, and has a few options:
 *   (as the Behavior is associated to a code value and not a specific Feature...)
 *   - to a specified row,column position where it will insert and (ideally) eat up designated
 *     empty space that can be replaced by a real Feature (so, FeatureSpacing)
 *   - insert as a new row between existing rows (the Feature being added should thusly be
 *     a FeatureComposite with lots of content defined within it).
 *   - AND, as the position of Features will likely change due to adding/removing features and
 *     rows, also relative to the placement of another Feature which may be embedded several
 *     levels deep in Feature Composites.
 *     - this may imply that it's better to always do it by reference to a Feature rather than
 *       exact row, column values as that is likely to be inconsistent
 *     - just have both options, there'll be a use case
 *     
 *  This implies the removal behavior should be to replace a row, column position with FeatureSpacing,
 *  or to delete an entire row.
 *  
 *  How would we have a side-decked FeatureComposite receive instructions to add or remove Features from it?
 * 
 */

public class BehaviorAddFeature extends Behavior {
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static int CHOICE_INSERT = 0;
	
	public final static int CHOICE_REPLACE = 1;
	
	/** Choosing to insert above/below will assume a new row is being added entirely as column positions won't line up*/
	public final static int RELATIVE_ABOVE = 0;
	/** Choosing to insert above/below will assume a new row is being added entirely as column positions won't line up*/
	public final static int RELATIVE_BELOW = 1;
	
	public final static int RELATIVE_RIGHT = 2;
	
	public final static int RELATIVE_LEFT = 3;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	/** Mode-select for this Behavior of whether to add the new Feature by replacing a FeatureSpacing or insert and change the row width*/
	private int howAddChoice;
	/** Mode-select for this Behavior of where, relative to the referenced Feature, the new Feature should be added*/
	private int wherePlace;
	/** 
	 * Behavior already stores a Feature name; that one is the one in the side-board we are adding.
	 * The one stored in BehaviorAddFeature is the Feature that we want to add relative to its position.
	 */
	private String relativePositionFeature;

//---  Constructors   -------------------------------------------------------------------------
	
	/**
	 * Constructor for BehaviorAddFeature that takes the name of the Feature we are adding, the
	 * name of the Feature whose position we are referencing, and code values from the exported
	 * constants of this class for whether we add by replacing a FeatureSpacing or insert into
	 * the row as well as what the relative positioning is for where to add the new Feature.
	 * 
	 * @param sideboardReference - String name of the Feature in the sideboard you want to add
	 * @param relativeReference - String name of the Feature whose position will be used to add relative to
	 * @param choiceAdd - int value referring to the constants CHOICE_INSERT and CHOICE_REPLACE
	 * @param choiceRelative - int value referring to the constants RELATIVE_ABOVE/BELOW/RIGHT/LEFT
	 */
	
	public BehaviorAddFeature(String sideboardReference, String relativeReference, int choiceAdd, int choiceRelative) {
		super(sideboardReference);
		relativePositionFeature = relativeReference;
		howAddChoice = choiceAdd;
		wherePlace = choiceRelative;
	}

	@Override
	public boolean performAction() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getRelativeFeatureReference() {
		return relativePositionFeature;
	}
	
	public int getModeAdd() {
		return howAddChoice;
	}
	
	public int getModePlacement() {
		return wherePlace;
	}

}
