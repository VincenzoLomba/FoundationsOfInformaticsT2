package edlift.model;

import java.util.OptionalInt;

public enum RequestResult {
	
	ACCEPTED("accepted"),
	REJECTED("rejected"),
	MODIFIED("modified");
	
	private RequestResult(String msg) {
		this.msg=msg;
		this.floor = OptionalInt.empty();
	}
	
	private String msg;
	private OptionalInt floor;
	
	public String getMsg() { return msg; }
	public void setMsg(String msg) { this.msg = msg; }
	
	public OptionalInt getFloor() { return floor; }
	public void setFloor(OptionalInt f) { floor = f; }
}
