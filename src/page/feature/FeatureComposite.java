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
				f.draw(hp, currX + widAlloc / 2, currY + heiUse / 2, widAlloc, heiUse);
				currX += widAlloc * f.getHorizontalProportion();
			}
			currY += maxVertProp * heiAlloc;
		}
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
		if(layout.get(row).size() <= column) {
			layout.get(row).add(newFeature);
			return true;
		}
		else {
			layout.get(row).add(column, newFeature);
			return true;
		}
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
