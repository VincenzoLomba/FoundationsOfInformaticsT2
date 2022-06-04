package edlift.model;

public class BasicLift extends Lift {

	public BasicLift(int minFloor, int maxFloor, int initialFloor, double speed) {
		super(minFloor, maxFloor, initialFloor, speed);
	}

	@Override
	public String getMode() { return "Basic"; }

	@Override
	public RequestResult goToFloor(int floor) {
		
		if (floor>getMaxFloor() || floor<getMinFloor()) 
			throw new IllegalArgumentException("Arrival floor out of range");
		if (getCurrentState() == LiftState.REST) {
			if (getCurrentFloor() == floor) return RequestResult.ACCEPTED;
			// setCurrentState(floor > getCurrentFloor() ? LiftState.UP : LiftState.DOWN);
			setCallingFloor(floor);
			return RequestResult.ACCEPTED;
		}
		return RequestResult.REJECTED;
	}
}
