package file;

/**
 * 
 * Idea is we can pass an interfaced version of FileAccess to specific areas of other programs
 * and ensure it has access to only one particular config file for reading/writing.
 * 
 */

public interface SpecificFileAccessor {

	public abstract String accessData(String property) throws Exception;
	
	public abstract void assignData(String property, String value);
	
}
