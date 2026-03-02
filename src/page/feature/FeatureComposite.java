package page.feature;

import java.util.ArrayList;

import visual.composite.HandlePanel;

/**
 * 
 * A FeatureComposite is a type of Feature that contains other Features; it's basically
 * a miniature page that can be added whole-hog to the actual Page after definition.
 * 
 * This could work as a miniature environment such that we have a super level of proportions
 * for horizontal and vertical page space and then divies up the space inside itself according
 * to its component Features.
 * 
 * Or it could inherit the proportion values via calculation of its component pieces so it's purely
 * an organizational tool that effectively deconstructs to base Features upon drawing. 
 * 
 * Given that this would behave the exact same way as the ConfigPage and I could just have the
 * 2D list be in this class and give ConfigPage a FeatureComposite as the entire page layout, 
 * it will intuit its own proportions based on what is given to it.
 * 
 * That can be a setting sometime, maybe?
 * 
 * TODO: Non duplicate adding so AddBehavior can't do redundant feature adding.
 * 
 */

public class FeatureComposite extends Feature implements FeatureAdder, FeatureRemover{
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private ArrayList<ArrayList<Feature>> layout;
	/** boolean to denote whether 'populateRows' was called to create initial row sizes*/
	private boolean initiated;
	
	private ArrayList<FeatureComposite> subLayouts;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public FeatureComposite(String inTitle, int proportionHorizontal, int proportionVertical) {
		super(inTitle, proportionHorizontal, proportionVertical);
		layout = new ArrayList<ArrayList<Feature>>();
		subLayouts = new ArrayList<FeatureComposite>();
		initiated = false;
	}

//---  Operations   ---------------------------------------------------------------------------
	
	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {

		int currY = 0;
		for(ArrayList<Feature> arr : layout) {
			int totHorizProp = 0;
			int maxVertProp = 1;
			for(Feature f : arr) {
				totHorizProp += f.getHorizontalProportion();
				maxVertProp = f.getVerticalProportion() > maxVertProp ? f.getVerticalProportion() : maxVertProp;
			}
			int widAlloc = width / totHorizProp;
			int heiAlloc = 40; //TODO: oh, there is a config file for this re: how it should draw(fonts, sizing, etc.)
			int currX = 0;
			for(Feature f : arr) {
				int heiUse = heiAlloc * f.getVerticalProportion();
				int widUse = widAlloc * f.getHorizontalProportion();
				f.handleDraw(hp, currX + widUse / 2, currY + heiUse / 2, widUse, heiUse);
				currX += widAlloc * f.getHorizontalProportion();
			}
			currY += heiAlloc;
		}
	}

	/**
	 * Initiation function that takes an array of integers and uses each as an indicator
	 * of what each row's horizontal proportion amount should be; that is, how many segments
	 * each row should be split up into so that a Feature with horizontal proportion of 1
	 * will have 1 / [amount in the array for that row].
	 * 
	 * This is to simplify backend logic for automated formatting.
	 * 
	 * @param rowHorizontalProps
	 */
	
	public void populateRows(int[] rowHorizontalProps) {
		initiated = true;
		for(int i = 0; i < rowHorizontalProps.length; i++) {
			this.addFeature(new FeatureSpacing(FeatureSpacing.CAN_REMOVE, rowHorizontalProps[i], 1), i, 0);
		}
	}
	
	//-- Positioning Logic  -----------------------------------
	
	/**
	 * Assuming 0-indexing for counting the row and column numbers
	 * 
	 * If row value exceeds size of Feature list, add empty rows to reach that row.
	 * 
	 * If column value exceeds size of Feature row list, append Feature to end of list instead
	 * of using the requested column value. If column value is an existing index, pushes Features
	 * aside to insert at that index.
	 * 
	 * Design note to self:
	 * 
	 * Concept of using column position as stand-in for horizontal proportion introduces some
	 * awkward complexity. Row is obvious way to assign position, but do we allow column to be
	 * defined like this or imply it's a stack structure with no editing after the fact?
	 * 
	 * This isn't being drawn live so the user could fix incorrect ordering, but enforcing
	 * that style of structural design via order of operation feels flimsy. How to make it hard
	 * for the user to mis-use custom column assignation.
	 * 
	 * Basic problem is user adds a Feature at column 3, we buffer spacing for indices 0-2, then
	 * they add a Feature at column 1 with horizontal proportion 1. We can just substitute the spacing
	 * Feature at column 1 with the new Feature. Horizontal proportion 2, we still have space to substitute
	 * both spacing Features, but at proportion 3 it now pushes into column 4.
	 * 
	 * Albeit, proportion is not a mapping onto array index position, but if we buffer space in an empty
	 * array when adding a Feature to not the end of the list, translating that buffer space via index
	 * position - 
	 * 
	 * Okay, allow placement at a particular column but shorten the column value to end of list if it
	 * exceeds the actual length of the list as we cannot tell how the user wants this handled.
	 * 
	 * If vertical proportion of the newFeature is greater than 1, adds spacing in the row below it to
	 * buffer that space for it to take up.
	 * 
	 * row maps directly to an array list of the 2D Feature structure
	 * 
	 * column does not map to an index, it's a derived position
	 * 
	 * @param newFeature
	 * @param row
	 * @param column
	 * @return
	 */
	
	public boolean addFeature(Feature newFeature, int row, int column) {
		//System.out.println("---add feature: " + newFeature.getTitle() + ", " + row + ", " + column);
		if(row < 0 || !initiated) {
			return false;
		}
		if(checkDuplicate(newFeature.getTitle())) {
			return false;
		}
		while(layout.size() <= row) {
			layout.add(new ArrayList<Feature>());
		}
		if(column < 0) {
			return false;
		}
		int rowSize = effectiveRowWidth(row);
		int vertProp = newFeature.getVerticalProportion();
		// If we're adding to a row and our position is past the end of it, buffer space before for effective positioning
		if(rowSize <= column) {
			if(column - rowSize > 0) {
				Feature sp = new FeatureSpacing(FeatureSpacing.CAN_REMOVE, column - rowSize, 1);
				layout.get(row).add(sp);
			}
			layout.get(row).add(newFeature);
			if(newFeature.getDataContent() == null) {
				//TODO: This is bad and I don't like doing it but not sure what a better way is
				//To clarify, casting to a specific type while using generics everywhere else
				subLayouts.add((FeatureComposite)newFeature);
			}
		}
		// Otherwise, just add it at that spot; need to check for overlap with a spacing, should replace any blank spacing
		else {
			if(!insertFeature(row, column, newFeature)) {
				return false;
			}
		}
		// If takes up multiple vertical rows, needs to buffer space beneath itself to avoid collisions
		if(vertProp > 1) {
			addFeature(new FeatureSpacing(FeatureSpacing.NO_REMOVE, newFeature.getHorizontalProportion(), vertProp - 1), row + 1, column);
			if(effectiveRowWidth(row + 1) < effectiveRowWidth(row)) {
				addFeature(new FeatureSpacing(FeatureSpacing.CAN_REMOVE, effectiveRowWidth(row) - effectiveRowWidth(row + 1), 1), row + 1, effectiveRowWidth(row));
			}
		}
		return true;
	}

	/**
	 * Local method to find where in a row to actually add a feature object based on effective
	 * column positioning from each feature's horizontal proportion values.
	 * 
	 * Once that position is found, it then checks that the new feature can 'fit' into that spot
	 * (i.e., there is removable spacing in front of it that it can extend into and replace).
	 * 
	 * Returns false if the eatSpacing function returns false or if we didn't find where to
	 * insert the new feature within the list iteration, which whouldn't 
	 * 
	 * @param row
	 * @param desiredColumn
	 * @param newFeature
	 * @return
	 */
	
 	private boolean insertFeature(int row, int desiredColumn, Feature newFeature) {
		//System.out.println("Row: " + layout.get(row));
		ArrayList<Feature> list = layout.get(row);
		/*
		String use = "";
		for(Feature f : list) {
			use = use + " " + f.getHorizontalProportion();
		}
		System.out.println(use);
		*/
		int currCol = 0;
		int ind = 0;
		for(int i = 0; i < list.size(); i++) {
			Feature f = list.get(i);
			//System.out.println(" " + currCol + " " + ind);
			// If we have found the index at which point we are at or over the desired column position...
			if(desiredColumn <= currCol) {
				//  Call function for identifying the feature at this index and splitting it apart if able to
				return splitSpacer(desiredColumn, currCol, ind, row, newFeature);
			}
			currCol += f.getHorizontalProportion();
			ind += 1;
		}
		return splitSpacer(desiredColumn, currCol, ind, row, newFeature);
	}
	
	/**
	 * Local helper function that takes a new Feature, the desired column location of it, and the
	 * calculated actual column position of the first Feature that's column goes past or equal to
	 * the desired position of the new Feature.
	 * 
	 * Basically, we want to add something at position x. If a removable/editable feature overlaps
	 * position x, we want to trim or split that feature to make space for our new Feature to fit
	 * inside of there. We check if the Feature is large enough to need to be split in two (as
	 * we expect to call 'eatSpacing' afterwards so would there still be some spacing leftover
	 * in front of the new Feature once we insert the new Feature) and then either trim the
	 * spacer behind the new Feature or split the n-length spacing into two spacers of l and n
	 * lengths where l + m = n.
	 * 
	 * TODO: spacing added in here should probably be assigned this composite as a parent
	 * 
	 * @param desiredColumn
	 * @param currCol
	 * @param ind
	 * @param row
	 * @param newFeature
	 * @return
	 */
	
	private boolean splitSpacer(int desiredColumn, int currCol, int ind, int row, Feature newFeature) {
		// If we have gone over the desired column position, trim the preceding Feature if able to
		ArrayList<Feature> list = layout.get(row);
		if(desiredColumn < currCol) {
			int over = currCol - desiredColumn;
			Feature l = list.get(ind - 1);

			// If we are able to manipulate the size of the Feature behind us...
			if(l.getTitle().equals(FeatureSpacing.CAN_REMOVE)) {
				//System.out.println("Can remove prior feature");
				// Check if the preceding feature is so large that the new Feature splits it in two
				if(over >= newFeature.getHorizontalProportion()) {
					//System.out.println("Will split prior feature");
					// Effectively, just break the FeatureSpacing in half and let eatSpacing resolve it
					int upWid = currCol - desiredColumn;
					int lowWid = l.getHorizontalProportion() - upWid;
					list.remove(ind - 1);
					list.add(ind - 1, new FeatureSpacing(FeatureSpacing.CAN_REMOVE, lowWid, 1));
					list.add(ind, new FeatureSpacing(FeatureSpacing.CAN_REMOVE, upWid, 1));
				}
				// Otherwise, we just have to trim the front of the preceding feature
				else {
					//System.out.println("Trim");
					int newHorz = l.getHorizontalProportion() - over;
					list.remove(ind - 1);
					list.add(ind - 1, new FeatureSpacing(FeatureSpacing.CAN_REMOVE, newHorz, 1));
				}
			}
		}
		// Then add the Feature and trim any following Features to reserve that space if able to
		list.add(ind, newFeature);
		int horz = newFeature.getHorizontalProportion();
		return eatSpacing(row, ind, horz);
	}
	
	/**
	 * 
	 * Idea is that, if you add a new Feature into the middle of a row, you have to
	 * account for the space it will take up while maintaining other Features' set
	 * column positions.
	 * 
	 * Some auto-added Spacing is allowed to be absorbed by other Features, so this
	 * will remove any Spacing in the way of the Feature being inserted, either
	 * removing it outright or substituting it for a smaller Spacing feature.
	 * 
	 * If the space the new Feature needs is taken up by non-removable Features, this
	 * returns false and should prompt an exception to be thrown alerting of the collision.
	 * 
	 * @param row
	 * @param index
	 * @param removalAmount
	 * @return
	 */
	
	private boolean eatSpacing(int row, int index, int removalAmount) {
		//System.out.println("Row: " + layout.get(row));
		//System.out.println("Eat: " + row + ", " + index + ", " + removalAmount);
		ArrayList<Feature> list = layout.get(row);
		for(int i = index + 1; i < list.size(); i++) {
			if(list.get(i).getTitle().equals(FeatureSpacing.CAN_REMOVE)) {
				//System.out.println("Can remove");
				int horz = list.get(i).getHorizontalProportion();
				int deduct = removalAmount - horz;
				if(deduct == 0) {
					list.remove(i);
					return true;
				}
				else if(deduct < 0) {
					list.remove(i);
					list.add(i, new FeatureSpacing(FeatureSpacing.CAN_REMOVE, 0 - deduct, 1));
					return true;
				}
				else {
					removalAmount -= horz;
					i--;
					list.remove(i);
				}
			}
			else {
				return false;
			}
		}
		return false;
	}

	//-- Feature Finding  -------------------------------------
	
	public int findFeatureRow(String identifier) {
		int row = 0;
		for(ArrayList<Feature> arr : layout) {
			for(Feature f : arr) {
				if(f.getTitle().equals(identifier)) {
					return row;
				}
			}
			row++;
		}
		return -1;	
	}
	
	public int findFeatureColumn(String identifier) {
		for(ArrayList<Feature> arr : layout) {
			int col = 0;
			for(Feature f : arr) {
				if(f.getTitle().equals(identifier)) {
					return col;
				}
				col += f.getHorizontalProportion();
			}
		}
		return -1;	
	}
	
	public FeatureComposite findPossessingComposite(String identifier) {
		if(findFeature(identifier) != null) {
			return this;
		}
		for(FeatureComposite fc : subLayouts) {
			FeatureComposite out = fc.findPossessingComposite(identifier);
			if(out != null) {
				return out;
			}
		}
		return null;
	}

	//-- Feature Adding  --------------------------------------
	
	@Override
	public boolean addFeatureNewRow(String referenceFeature, Feature feat, boolean above) {
		if(checkDuplicate(feat.getTitle())) {
			return false;
		}
		
		if(this.findFeature(referenceFeature) == null) {
			return findPossessingComposite(referenceFeature).addFeatureNewRow(referenceFeature, feat, above);
		}

		ArrayList<Feature> featRow = new ArrayList<Feature>();
		featRow.add(feat);
		int newInd = findFeatureRow(referenceFeature) + (above ? 0 : 1);
		layout.add(newInd, featRow);
		return true;
	}
	
	@Override
	public boolean addFeatureInRow(String referenceFeature, Feature feat, boolean insert, boolean after) {
		if(checkDuplicate(feat.getTitle())) {
			return false;
		}
		
		if(this.findFeature(referenceFeature) == null) {
			return findPossessingComposite(referenceFeature).addFeatureInRow(referenceFeature, feat, insert, after);
		}		
		
		int column = findFeatureColumn(referenceFeature);
		int row = findFeatureRow(referenceFeature);
		if(after) {
			column += layout.get(row).get(findFeatureIndex(row, column)).getHorizontalProportion();
		}
		if(!insert) {
			return addFeature(feat, row, column);
		}
		else {
			int posit = findFeatureIndex(row, column);
			if(posit == -1) {
				layout.get(row).add(feat);
			}
			else {
				layout.get(row).add(posit, feat);
			}
			return true;
		}
	}
	
	//-- Feature Removing  ------------------------------------
	
	@Override
	public boolean removeFeatureRow(String referenceFeature, boolean insertSpacing) {
		if(this.findFeature(referenceFeature) == null) {
			return findPossessingComposite(referenceFeature).removeFeatureRow(referenceFeature, insertSpacing);
		}
		int row = findFeatureRow(referenceFeature);
		try {
			if(!insertSpacing) {
				if(row >= 0 && row < layout.size()) {
					layout.remove(row);
					return true;
				}
				return false;
			}
			else {
				int wid = effectiveRowWidth(row);
				layout.get(row).clear();
				addFeature(new FeatureSpacing(FeatureSpacing.CAN_REMOVE, wid, 1), row, 0);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean removeFeature(String referenceFeature, boolean replace) {
		if(this.findFeature(referenceFeature) == null) {
			FeatureComposite parent = findPossessingComposite(referenceFeature);
			if(parent == null) {
				return false;
			}
			return parent.removeFeature(referenceFeature, replace);
		}
		int row = findFeatureRow(referenceFeature);
		int column = findFeatureColumn(referenceFeature);
		ArrayList<Feature> list = layout.get(row);
		int posit = findFeatureIndex(row, column);
		if(posit == -1) {
			return false;
		}
		if(!replace) {
			list.remove(findFeatureIndex(row, column));
			return true;
		}
		else {
			int wid = list.get(posit).getHorizontalProportion();
			list.remove(posit);
			return addFeature(new FeatureSpacing(FeatureSpacing.CAN_REMOVE, wid, 1), row, column);
		}
	}
	
	private int findFeatureIndex(int row, int column) {
		ArrayList<Feature> list = layout.get(row);
		int posit = 0;
		int index = 0;
		for(Feature f : list) {
			if(posit >= column) {
				return index;
			}
			posit += f.getHorizontalProportion();
			index += 1;
		}
		return -1;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------

	/**
	 * 
	 * This returns null so that we can identify that it's a FeatureComposite in other contexts,
	 * not a good system and should be changed but make sure you update getDataContent(String)
	 * if you change this from null.
	 * 
	 */
	
	public String getDataContent() {
		return null;
	}
	
	@Override
	public String getDataContent(String identifier) {
		if(getTitle().equals(identifier)) {
			return getDataContent();
		}
		if(findFeature(identifier) != null) {
			return findFeature(identifier).getDataContent();
		}
		for(ArrayList<Feature> row : layout) {
			for(Feature f : row) {
				if(f.getDataContent() == null) {
					String out = f.getDataContent(identifier);
					if(out != null) {
						return out;
					}
				}
			}
		}
		return null;
	}

	public boolean getInitiatedStatus() {
		return initiated;
	}
	
	public Feature findFeature(String identifier){
		for(ArrayList<Feature> arr : layout) {
			for(Feature f : arr) {
				if(f.getTitle().equals(identifier)) {
					return f;
				}
			}
		}
		return null;		
	}
	
//---  Support Methods   ----------------------------------------------------------------------

	public void printRowWidths() {
		System.out.println("Current Row Widths for # rows: " + layout.size());
		for(ArrayList<Feature> arr : layout) {
			String use= "";
			for(Feature f : arr) {
				use = use + " " + f.getHorizontalProportion();
			}
			System.out.println(use);
		}
	}

	/**
	 * Calculates what the effective width of a row is based on each Feature's horizontal
	 * proportion values.
	 * 
	 * @param row
	 * @return
	 */
	
	private int effectiveRowWidth(int row) {
		ArrayList<Feature> list = layout.get(row);
		int out = 0;
		for(Feature f : list) {
			out += f.getHorizontalProportion();
		}
		return out;
	}
	
	/**
	 * Function to detect whether a particular identifier has already been used for a Feature
	 * currently in this FeatureComposite.
	 * 
	 * @param identifier
	 * @return
	 */
	
	private boolean checkDuplicate(String identifier) {
		boolean out =identifier != null && findFeature(identifier) != null && !identifier.equals(FeatureSpacing.CAN_REMOVE) && !identifier.equals(FeatureSpacing.CAN_REMOVE);
		if(out) {
			System.err.println("Error: Attempt to add identical Feature: " + identifier);
		}
		return out;
	}

}
