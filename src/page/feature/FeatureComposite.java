package page.feature;

import java.awt.Color;
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
 */

public class FeatureComposite extends Feature {
	
	private ArrayList<ArrayList<Feature>> layout;
	
	public FeatureComposite(String inTitle, int proportionHorizontal, int proportionVertical) {
		super(inTitle, proportionHorizontal, proportionVertical);
		layout = new ArrayList<ArrayList<Feature>>();
	}

	@Override
	public void draw(HandlePanel hp, int x, int y, int width, int height) {
		// TODO This has all of the logic for drawing the Features within the Composite;
		//      calculating positions based on proportion values and whatnot.

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
				f.draw(hp, currX + widUse / 2, currY + heiUse / 2, widUse, heiUse);
				currX += widAlloc * f.getHorizontalProportion();
			}
			currY += heiAlloc;
		}
	}
	
	public boolean detectCollision() {
		// TODO: Detect if any Features overlap
		return false;
	}

	/**
	 * 
	 * This is kinda complicated for this context, will consider
	 * TODO
	 * 
	 */
	
	public String getDataContent() {
		return null;
	}

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
		if(row < 0) {
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
			if(column - rowSize > 0)
				layout.get(row).add(new FeatureSpacing(FeatureSpacing.CAN_REMOVE, column - rowSize, 1));
			layout.get(row).add(newFeature);
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
		}
		return true;
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
		ArrayList<Feature> list = layout.get(row);
		int currCol = 0;
		int ind = 0;
		for(int i = 0; i < list.size(); i++) {
			Feature f = list.get(i);
			// If we have found the index at which point we are at or over the desired column position...
			if(desiredColumn <= currCol) {
				// If we have gone over the desired column position, trim the preceding Feature if able to
				if(desiredColumn < currCol) {
					int over = currCol - desiredColumn;
					Feature l = list.get(ind - 1);

					// If we are able to manipulate the size of the Feature behind us...
					if(l.getTitle().equals(FeatureSpacing.CAN_REMOVE)) {
						// Check if the preceding feature is so large that the new Feature splits it in two
						if(over > desiredColumn + newFeature.getHorizontalProportion()) {
							// Effectively, just break the FeatureSpacing in half and let eatSpacing resolve it
							int upWid = currCol - desiredColumn;
							int lowWid = l.getHorizontalProportion() - upWid;
							list.remove(ind - 1);
							list.add(ind - 1, new FeatureSpacing(FeatureSpacing.CAN_REMOVE, lowWid, 1));
							list.add(ind, new FeatureSpacing(FeatureSpacing.CAN_REMOVE, upWid, 1));
						}
						// Otherwise, we just have to trim the front of the preceding feature
						else {
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
			currCol += f.getHorizontalProportion();
			ind += 1;
		}
		return false;
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
		ArrayList<Feature> list = layout.get(row);
		for(int i = index + 1; i < list.size(); i++) {
			if(list.get(i).getTitle().equals(FeatureSpacing.CAN_REMOVE)) {
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
	
}
