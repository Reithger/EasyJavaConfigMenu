package page.behavior;

import java.io.File;

import file.SpecificPropertyAccessor;
import filemeta.FileChooser;

public class BehaviorOpenFileSelect extends Behavior implements PropertyAccessor{

	private SpecificPropertyAccessor property;
	
	public BehaviorOpenFileSelect(String featureReference) {
		super(featureReference);
	}

	@Override
	public boolean performAction() {
		File f = FileChooser.promptSelectFile("", true, true);
		if(f != null) {
			return property.setConfigPropertyValue(f.getAbsolutePath());
		}
		return false;
	}

	@Override
	public void assignPropertyAccessor(SpecificPropertyAccessor sfa) {
		property = sfa;
	}

}
