package file;

public class PropertyAccess extends FileAccess implements SpecificPropertyAccessor{

	private String property;

	public PropertyAccess(String folderPath, String configFile, String targetProperty) {
		super(folderPath, configFile);
		property = targetProperty;
	}
	
	@Override
	public String getConfigPropertyValue() throws Exception {
		return this.accessData(property);
	}

	@Override
	public boolean setConfigPropertyValue(String value) {
		return this.assignData(property, value);
	}

}
