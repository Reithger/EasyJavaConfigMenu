package page.feature;

/**
 * 
 * Type of Feature that is a basic text display, except the text it displays is associated
 * to the value in the config file's matching property field (this is so the user can see
 * what is in the config file and it should update to be accurate). Also should simplify
 * annoying logic of how to make sure Feature text is correct when it is supposed to
 * correspond specifically to data in a file.
 * 
 */

public class FeaturePropertyText extends FeatureBasicText{

	public FeaturePropertyText(String inTitle, int proportionHorizontal, int proportionVertical, String displayText) {
		super(inTitle, proportionHorizontal, proportionVertical, displayText);
	}

}
