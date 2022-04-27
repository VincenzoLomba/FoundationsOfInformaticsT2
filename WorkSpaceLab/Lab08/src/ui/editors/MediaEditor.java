package ui.editors;

import media.*;
import utils.StdInput;

public abstract class MediaEditor {
	private StdInput input = null;

	public MediaEditor() {
		input = new StdInput();
	}

	protected StdInput getInput() {
		return input;
	}

	public abstract Media create();
}
