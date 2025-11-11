package file;
import java.io.File;

import filemeta.config.Config;
import filemeta.config.ValidateFiles;
import filemeta.config.blueprint.ConfigBlueprint;

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
	
//---  Constants   ----------------------------------------------------------------------------

	public static final String CONFIG_FILE_NAME = "config.txt";
	
	private static final String CONFIG_DESCRIPTION_DEFAULT = "Default config file description";
	
	public static final String CONFIG_FOLDER = "config";
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private String baseConfigPath;
	
	private String configName;

//---  Constructors   -------------------------------------------------------------------------
	
	public FileAccess(String folderPath) {
		baseConfigPath = folderPath;
		configName = CONFIG_FILE_NAME;
	}
	
	public FileAccess(String folderPath, String inConfigName) {
		baseConfigPath = folderPath;
		configName = inConfigName;
	}
	
//---  Operations   ---------------------------------------------------------------------------

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
	
	public void assignProperties(String path, String file, String ... properties) {
		String parentPath = new File(path).getParent();
		String folder = new File(path).getName();
		Config cb = new Config(parentPath, this);
		cb.addFilePath(folder);
		cb.addFile(folder, file, CONFIG_DESCRIPTION_DEFAULT);
		for(String s : properties) {
			cb.addFileEntry(folder, file, s, "", "null");
		}
		cb.softWriteConfig();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	/**
	 * Getter method that spawns a SpecificFileAccessor object tied to the file named "config.txt"
	 * in the folder pointed to by the argument configPath String.
	 * 
	 * SpecificFileAccessor is an interface that exposes only the 'accessData' and 'assignData' methods.
	 * 
	 * @param configPath
	 * @return
	 */
	
	public SpecificFileAccessor getConfigAccessor(String configPath) {
		return new FileAccess(configPath);
	}
	
	/**
	 * Getter method that spawns a SpecificFileAccessor object tied to the file identified
	 * in the folder pointed to by the argument configPath String and named as defined by
	 * the argument configFileName String.
	 * 
	 * SpecificFileAccessor is an interface that exposes only the 'accessData' and 'assignData' methods.
	 * 
	 * @param configPath
	 * @param configFileName
	 * @return
	 */
	
	public SpecificFileAccessor getConfigAccessor(String configPath, String configFileName) {
		return new FileAccess(configPath, configFileName);
	}
	
	/**
	 * 
	 * Generates the subclass of FileAccess, PropertyAccess, that polymorphs and is
	 * returned as the interface SpecificPropertyAccessor; this is an interface that
	 * focuses the general behavior of FileAccess down to just reading the single associated
	 * property for a particular thing.
	 * 
	 * This function is also a realization of the SpecificFileAccessor interface so that FileAccess
	 * objects focused onto a particular config file can then produce the subclass that is focused on
	 * reading a particular property. Maybe make it also write to it so that updating the property
	 * is similarly trivial for Behaviors.
	 * 
	 */
	
	public SpecificPropertyAccessor getPropertyAccessor(String property) {
		return new PropertyAccess(baseConfigPath, configName, property);
	}
	
//---  Support Methods   ----------------------------------------------------------------------
	
	@Override
	public int validateFile(Config c, File f) {
		return Config.CONFIG_VERIFY_SUCCESS;
	}
	
	private String configPath() {
		return baseConfigPath + "/" + configName + (configName.endsWith(".txt") ? "" : ".txt");
	}
	
}
