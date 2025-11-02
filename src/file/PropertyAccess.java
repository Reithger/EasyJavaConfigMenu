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
	public void setConfigPropertyValue(String value) {
		this.assignData(property, value);
	}

}
