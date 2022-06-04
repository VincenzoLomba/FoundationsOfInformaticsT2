package edlift.model;

import java.util.OptionalInt;

public class FakeMultiFloorLift extends Lift {

	public FakeMultiFloorLift(int minFloor, int maxFloor, int initialFloor, double speed) {
		super(minFloor, maxFloor, initialFloor, speed);
	}
	
	@Override
	public boolean hasPendingFloors() {
		return false;
	}
	
	@Override
	public OptionalInt nextPendingFloor(LiftState state) {
		return OptionalInt.empty();
	}
	
	@Override
	public RequestResult goToFloor(int floor) {
		if (floor>getMaxFloor() || floor<getMinFloor()) 
			throw new IllegalArgumentException("Arrival floor out of range");
		return RequestResult.REJECTED;
	}
	
	@Override
	public String getMode() {
		return "MultiFloor";
	}
	
}
