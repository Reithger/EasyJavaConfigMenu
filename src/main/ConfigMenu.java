package main;
import file.FileAccess;
import file.SpecificFileAccessor;
import page.FeatureLoader;

/**
 * 
 * Root class of the EasyJavaConfigMenu project; this class provides access to a suite of options and
 * methods that can configure/design a config menu simply and quickly.
 * 
 * Should include:
 *  - Pre-designed formatting for options lists/button-grids
 *  - Simple functions that take lists of strings/numeric codes to build a config screen and hook the user
 *    actions back to a programmer-context for interpretation (programmer provides a class that implements
 *    something to receive user code values)
 *  - Options to describe multiple menu screens and have them generate one another without requiring logic in
 *    the main program
 *  - Variety of 'button' types to use (just text, text with image, size variance, etc.) in a list
 *  - Menu header/footer information
 *  - For complex information display, need a way to retrieve and manage the data from the programmer source
 *    - Interface(?) wrapping around the concept of a map for labeled data; config menu contents can refer to
 *      data by its label in the map that it will look up based on what is provided to it
 *    - Needs versatility for user code values, too; encoding system for what is displayed on a config menu
 *      and how to compact the model backend data to display it in that styling
 *      - Could use an interface to give structure to how an object is converted into the JSON-style mapping;
 *        use a raw type to let implementer define the object, produces a HashMap, this library stores that object
 *        so it can just be passed the object for conversion? Requires dynamically knowing the object class, though.
 *        - Complicated but we'll figure it out. 
 * 
 *  Main Config class here
 *   - Manages a series of Page objects, each being a Config Menu page
 *   - A Page object is defined by a PlacementFormat layout and a number of Elements to fill that Format
 *   - Elements include ClickInteractive, DataEntryField, DescriptiveText/Image, SliderDataField
 *    - Pages need to be able to be constructed from this top level context for potentially complicated/wordy/image fields
 *   - Also a CompElement that is made up of many Elements for easy re-usage of an assemblage of Elements
 *    - Page buttons interact by either transitioning from one Page to another or changing Config properties in .config file
 *   - Generally also need a FileStorage class for writing to and reading from a .config file for program settings
 *   - Appropriately, Config also has getters for the various properties/fields retrievable from .config
 *   - Should be able to tie Page Elements to certain Config fields to instruct data entry to be written to memory
 *   - Oughtn't need to pass underlying data/command codes to programmer side, internally managed logic for that
 * 
 * 
 * ideal use case:
 * 
 * config.loadProperties("./path_to_config")
 * config.getProperty("...")
 * config.makeConfigPage("name of page")
 * config.setConfigPageType("preset page layout name") or config.addConfigPageFeature("keyword of input/data type", accessory data)
 * config.populateConfigPage("name of page", "reference to feature", display data, input type, "config file property name")
 * 
 */

public class ConfigMenu {

	private FileAccess configData;
	/** TODO: Should allow for multiple of these for popout windows*/
	private ConfigWindow cw;
	
	/**
	 * Assumption is there is a default, root folder location for a top level config file context
	 * as defined in this constructor; user can then get an accessor for that or other config files
	 * in other locations by requesting a SpecificFileAccessor object.
	 * 
	 * Ideally they won't need to use that for assignment, just data retrieval.
	 * 
	 * @param pathToConfigFile
	 */
	
	public ConfigMenu(String pathToConfigFile) {
		configData = new FileAccess(pathToConfigFile);
	}
	
	public void resizeConfigWindow(int width, int height) {
		if(cw != null) {
			cw.display(width, height);
		}
	}
	
	public void addConfigPage(String title, String configFolderPath) {
		if(cw == null) {
			cw = new ConfigWindow();
		}
		cw.addConfigPage(title, configData.getConfigAccessor(configFolderPath));
	}
	
	public void addConfigPage(String title, String configFolderPath, String configFileName) {
		if(cw == null) {
			cw = new ConfigWindow();
		}
		cw.addConfigPage(title, configData.getConfigAccessor(configFolderPath, configFileName));
	}
	
	public FeatureLoader getFeatureLoader(String configPageName) {
		return new FeatureLoader(cw.getConfigPage(configPageName));
	}
	
	public SpecificFileAccessor getConfigAccessor(String configPath) {
		return configData.getConfigAccessor(configPath);
	}
	
	public SpecificFileAccessor getConfigAccessor(String configPath, String configFileName) {
		return configData.getConfigAccessor(configPath, configFileName);
	}
	
}
