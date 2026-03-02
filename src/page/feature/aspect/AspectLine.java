package page.feature.aspect;

import java.awt.Color;

import visual.composite.HandlePanel;

public class AspectLine extends FeatureAspect{

	public final static int LINE_RIGHT = 0;
	public final static int LINE_TOP = 1;
	public final static int LINE_LEFT = 2;
	public final static int LINE_BOTTOM = 3;
	
	private int choice;
	
	public AspectLine(int mode) {
		choice = mode;
	}
	
	@Override
	public void aspectDraw(HandlePanel p, int x, int y, int width, int height) {
		switch(choice) {
			case LINE_RIGHT:
				p.handleLine("line:" + choice + "_" + x + "_" + y, "basic", 6, x + width / 2, y - height / 2, x + width / 2, y + height / 2, 2, Color.black);
				break;
			case LINE_TOP:
				p.handleLine("line:" + choice + "_" + x + "_" + y, "basic", 6, x - width / 2, y - height / 2, x + width / 2, y - height / 2, 2, Color.black);
				break;
			case LINE_LEFT:
				p.handleLine("line:" + choice + "_" + x + "_" + y, "basic", 6, x - width / 2, y - height / 2, x - width / 2, y + height / 2, 2, Color.black);
				break;
			case LINE_BOTTOM:
				p.handleLine("line:" + choice + "_" + x + "_" + y, "basic", 6, x - width / 2, y + height / 2, x + width / 2, y + height / 2, 2, Color.black);
				break;
			default:
				break;
		}
	}

	public FeatureAspect duplicate() {
		return new AspectLine(choice);
	}
	
	@Override
	public void aspectPrint() {
		System.out.println("Aspect Line: " + choice);
	}
	
}
