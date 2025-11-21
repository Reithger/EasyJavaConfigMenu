import main.ConfigMenu;
import page.FeatureLoader;
import page.feature.aspect.FeatureAspectLoader;

public class TestConfigMenu {

	public static void main(String[] args) {
		ConfigMenu cm = new ConfigMenu("./config/");
		cm.establishConfigProperties("./config/", "test_val", "other_val");
		cm.addConfigPage("test", "./config/");
		
		/*
		 * 
		 * Awkward to have to manually add spacing to account for vertically overlapping Features,
		 * tricky to interpret how to auto-space with varying amounts of horizontal proportionality
		 * on each row.
		 * 
		 * Goal is to not have the user have to rely deeply on exactly correct usage and mapping things
		 * out, but auto-fixing could override something they are trying to do purposefully.
		 * 
		 * Add spacing below any Feature with more than 1 vert prop, have auto-detection of collisions
		 * to inform the user they fucked up and need to fix the positioning (or set an ignore collision
		 * flag).
		 * 
		 * Auto-fixing behavior:
		 *  - if vertProportion > 1, adds spacing underneath that Feature to reserve the space (will likely add
		 *    a lot of FeatureSpacing to appropriately buffer the locations).
		 *  - if chosen column placement is not adjacent to pre-existing features, buffers spacing before that
		 *    Feature's placement to put it effectively at that column
		 *  - when adding a feature to a spot taken up by spacing, substitutes itself into the place of those
		 *    spacing features so that auto-added buffering is not causing collisions with intended placements
		 * 
		 * This gets complicated by auto-adding Features setting what a row's width/placement is in a way that
		 * is very hard to retrofit if they later add other Features to it; this is primarily the case if
		 * a Feature has more than 1 vertical proportion so it's placement needs to be relative to the above
		 * row's total width and amount of horizontal proportions but the below rows may have that change
		 * as they are defined; how do you know a row of [1, 1, 1] proportions should actually be a [2, 2, 2]
		 * to incorporate placing something at position 1 or 5?
		 * 
		 * So the user needs to define row widths ahead of time (total row horizontal proportion) to save me
		 * from this dire fate.
		 * 
		 */
		FeatureLoader fl = cm.getFeatureLoader("test");
		try {
			cm.resizeConfigWindow(350, 500);
			
			fl.allocateRowSpacing(new int[] {4, 6, 6});

			FeatureAspectLoader fal = fl.getAspectMaker();
			fal.applyAspectToAll();
			fal.loadAspectLineSurround();
			
			// input format also anti-intuitive, effectively "y, x, wid, hei"
			// Though the order of "which row, which column, how large" feels more sensical
			
			fl.addBasicText("basic", 0, 0, 2, 1, "Property 1");
			
			fl.addTextInput("basic2", 0, 2, 1, 1, "Value");
			
			fl.addButton("property 1", 0, 3, 1, 1, "Update", 5);
			
			fl.addBehaviorUpdateConfigProperty(5, "basic2", "test_val");
			
			fl.addBasicText("basic1", 1, 0, 2, 1, "Test1");
			fl.addBasicText("basic3", 1, 2, 2, 2, "Test2");
			
			fl.addReferenceText("reference", 1, 4, 2, 1, "H: ", "test_val");

			
			fl.addBasicText("basic4", 2, 0, 1, 1, "Test3");
			
			
			fl.addTextInput("basic5", 2, 4, 1, 1, "Test4");
			fl.addTextInput("basic6", 2, 5, 1, 1, "Test5");


			fl.checkRowWidths();
			
			
			//fal.attachFeatureAspect("basic1");
			//fal.attachFeatureAspect("basic2");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			cm.addConfigPage("other", "./");
			fl = cm.getFeatureLoader("other");
			fl.allocateRowSpacing(new int[] {2});
			fl.addBasicText("basic", 0, 0, 1, 1, "other");
			fl.addTextInput("basic2", 0, 2, 1, 1, "Value");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
