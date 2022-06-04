package edlift.controller;

import java.util.List;

import edlift.model.Installation;

public class MyController extends BaseController  {

	public MyController(List<Installation> installations) {
		super(installations);
	}

	@Override
	public void goToFloor(int piano) {
		log("Lift call from floor " + piano + " " +
				getLift().goToFloor(piano).getMsg());
	}

}
