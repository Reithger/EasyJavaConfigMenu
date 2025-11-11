import main.ConfigMenu;
import page.FeatureLoader;

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
		 */
		FeatureLoader fl = cm.getFeatureLoader("test");
		try {
			// input format also anti-intuitive, effectively "y, x, wid, hei"
			// Though the order of "which row, which column, how large" feels more sensical
			fl.addBasicText("basic", 0, 0, 2, 1, "Property 1");
			fl.addTextInput("basic2", 0, 2, 1, 1, "Value");
			fl.addButton("property 1", 0, 3, 1, 1, "Update", 5);
			
			fl.addBehaviorUpdateConfigProperty(5, "basic2", "test_val");
			
			fl.addBasicText("basic1", 1, 0, 1, 1, "Test");
			fl.addBasicText("basic3", 1, 1, 1, 2, "Test");
			fl.addReferenceText("reference", 1, 2, 1, 1, "H: ", "test_val");
			
			fl.addBasicText("basic4", 2, 5, 1, 1, "Test");
			fl.addTextInput("basic4", 2, 2, 1, 1, "Test");
			
			cm.resizeConfigWindow(250, 400);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			cm.addConfigPage("other", "./");
			fl = cm.getFeatureLoader("other");
			fl.addBasicText("basic", 0, 0, 1, 1, "other");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
