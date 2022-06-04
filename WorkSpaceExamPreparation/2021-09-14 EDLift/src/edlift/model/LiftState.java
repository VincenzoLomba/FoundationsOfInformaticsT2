package edlift.model;

public enum LiftState {
	
	REST("at rest"),
	UP("going up"),
	DOWN("going down");
	
	private LiftState(String msg) {
		this.msg=msg;
	}
	
	private String msg;
	public String getMsg() { return msg; }
}
