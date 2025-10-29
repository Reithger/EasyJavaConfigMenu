package file;
import java.io.File;

import filemeta.config.Config;
import filemeta.config.ValidateFiles;

/**
 * 
 * FileAccess is an intermediary class between the main ConfigMenu logic and a pre-written
 * config file/folder structure support library built within SoftwareVisualInterface.
 * 
 * It establishes the setup of your config.txt file at a desired file path, and then has
 * a simple interface for requesting data and setting data in the config file.
 * 
 * Could add more facilities later on for further access to more complicated config structures,
 * which come to think of it is actually what the main use of this should be for ARTS. Can
 * use this as a facility for writing the data 
 * 
 * Should implement an interface that narrows the ability of this class to a particular file
 * in the config tree for passing to remote objects that affect a specific file only. Or maybe
 * it spawns a sub-class that is limited in such a way? Maybe a mapping of a remote object's identity
 * 
 * Optimal use case:
 *  instantiate object with base file for config folder structure
 *  establish locations of various config files within structure
 *   - allow variety of config file names in same folder
 *   
 * Put FileAccess into the mode of a particular file? Store all locations?
 * 
 */

public class FileAccess implements ValidateFiles, SpecificFileAccessor{

	private static final String CONFIG_FILE_NAME = "config.txt";
	
	private String baseConfigPath;
	
	private String configName;
	
	public FileAccess(String folderPath) {
		Config config = new Config(folderPath, this);
		baseConfigPath = folderPath;
		configName = CONFIG_FILE_NAME;
		config.addFile(folderPath, configName, "Default config file description");
		config.softWriteConfig();
	}
	
	public FileAccess(String folderPath, String inConfigName) {
		Config config = new Config(folderPath, this);
		baseConfigPath = folderPath;
		configName = inConfigName;
		config.addFile(folderPath, configName, "Default config file description");
		config.softWriteConfig();
	}
	
	public SpecificFileAccessor getConfigAccessor(String configPath) {
		return new FileAccess(configPath);
	}
	
	public SpecificFileAccessor getConfigAccessor(String configPath, String configFileName) {
		return new FileAccess(configPath, configFileName);
	}
	
	public String accessData(String property) throws Exception{
		String out = Config.getConfigFileEntry(configPath(), property);
		if(out == null) {
			throw new Exception("Property field: " + property + " not present in config.txt file");
		}
		return out;
	}
	
	public void assignData(String property, String newEntry) {
		Config.setConfigFileEntry(configPath(), property, newEntry);
	}

	@Override
	public int validateFile(Config c, File f) {
		return Config.CONFIG_VERIFY_SUCCESS;
	}
	
	private String configPath() {
		return baseConfigPath + "/" + configName + (configName.endsWith(".txt") ? "" : ".txt");
	}
	
}
