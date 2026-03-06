package page.behavior;

public class BehaviorConfigToggle extends BehaviorConfigUpdate{

	private String[] toggleValues;
	
	private int index;
	
	private boolean advancing;
	
	public BehaviorConfigToggle(String featureReference, boolean increment, String ... toggleList) throws Exception {
		super(featureReference);
		toggleValues = toggleList;
		advancing = increment;
	}
	
	public void initialize() throws Exception {
		String currVal = fileManip.getConfigPropertyValue();
		index = indexOf(toggleValues, currVal);
	}
	
	private int indexOf(String[] vals, String key) {
		for(int i = 0; i < vals.length; i++) {
			if(vals[i].equals(key)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean performAction() {
		index += advancing ? 1 : -1;
		index = index < 0 ? toggleValues.length - 1 : index;
		return fileManip.setConfigPropertyValue(toggleValues[index % toggleValues.length]);
	}

}
