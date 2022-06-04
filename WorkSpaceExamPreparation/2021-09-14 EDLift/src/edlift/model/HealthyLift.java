package edlift.model;

import java.util.OptionalInt;

public class HealthyLift extends Lift {

	public HealthyLift(int minFloor, int maxFloor, int initialFloor, double speed) {
		super(minFloor, maxFloor, initialFloor, speed);
	}

	@Override
	public String getMode() { return "Healthy"; }

	@Override
	public RequestResult goToFloor(int floor) {
		
		if (floor>getMaxFloor() || floor<getMinFloor()) 
			throw new IllegalArgumentException("Arrival floor out of range");
		if (getCurrentState() == LiftState.REST) {
			int difference = getCurrentFloor() - floor;
			if (Math.abs(difference) <= 1) return RequestResult.REJECTED;
			setCallingFloor(difference > 0 ? floor + 1 : floor - 1);
			// setCurrentState(difference > 0 ? LiftState.DOWN : LiftState.UP);
			RequestResult response = RequestResult.MODIFIED;
			response.setMsg("modified");
			response.setFloor(OptionalInt.of(getCallingFloor()));
			return response;
		}
		return RequestResult.REJECTED;
	}

}
