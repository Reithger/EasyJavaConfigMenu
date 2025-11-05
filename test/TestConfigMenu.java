import main.ConfigMenu;
import page.FeatureLoader;

public class TestConfigMenu {

	public static void main(String[] args) {
		ConfigMenu cm = new ConfigMenu("./");
		cm.addConfigPage("test", "./");
		FeatureLoader fl = cm.getFeatureLoader("test");
		fl.addBasicText("basic", 0, 0, 1, "Test");
		fl.addBasicText("basic1", 1, 0, 1, "Test");
		fl.addBasicText("basic2", 0, 1, 1, "Test");
		fl.addBasicText("basic3", 1, 1, 1, "Test");
		cm.resizeConfigWindow(250, 400);
		cm.addConfigPage("other", "./");
		fl = cm.getFeatureLoader("other");
		fl.addBasicText("basic", 0, 0, 1, "other");
	}
	
}
