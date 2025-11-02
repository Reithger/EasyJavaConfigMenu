package file;

/**
 * 
 * Idea with this is that, for certain Features that need to be consistent with data currently in
 * a config file, this interface allows for a Feature to have an object that just accesses the
 * current up-to-date data with no further access.
 * 
 * Who implements this?
 * 
 */

public interface SpecificPropertyAccessor {

	public abstract String getConfigPropertyValue() throws Exception;
	
	public abstract void setConfigPropertyValue(String value);
	
}
