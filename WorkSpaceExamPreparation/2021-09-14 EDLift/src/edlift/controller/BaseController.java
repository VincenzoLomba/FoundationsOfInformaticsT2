package edlift.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.OptionalInt;

import edlift.model.Lift;
import edlift.model.LiftState;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.control.TextArea;
import edlift.model.Installation;

public abstract class BaseController implements Controller {

	private TextArea view;
	private Lift lift;
	private List<Installation> installations;
	private Installation currentInstallation;
	private Timeline timer;
	private int ticks;
	private PrintWriter pw;
	
	public BaseController(List<Installation> installations) {
		if (installations.isEmpty()) throw new IllegalArgumentException("installation list is empty");
		this.installations = installations;
		this.currentInstallation = installations.get(0);
		this.lift = currentInstallation.getLift();
		ticks = 0;
		//
		timer = new Timeline(
				new KeyFrame(javafx.util.Duration.seconds(1), 
				e -> tick()));
        timer.setCycleCount(Timeline.INDEFINITE);
	}

	@Override
	public List<Installation> getInstallations() {
		return installations;
	}
		
	@Override
	public void setLift(Lift lift) {
		this.lift = lift;
	}
	
	@Override
	public Lift getLift(){
		return lift;
	}

	@Override
	public void setLogger(TextArea view, PrintWriter pw) {
		this.view = view; 
		this.pw=pw;
	}
	
	public void log(String msg){
		view.appendText(msg + "\n");
		pw.println("Controller log: " + msg);
		//System.out.print("Controller log: " + msg);
	}

	@Override
	public void startSimulation() {
        ticks = 0;
		timer.play();
		log("simulation started");
	}

	@Override
	public void stopSimulation() {
		timer.stop();
		log("simulation stopped");
	}
	
	/** Il metodo tick() incorpora la logica di avanzamento della simulazione:
	 *  è invocato automaticamente dalla GUI a intervalli regolari.
	 *  Non toccare!
	 */
	public void tick() {
		ticks++;
		//log("\tsimulation clock: " + ticks);
		log("Clock: " + ticks);
		
		switch (lift.getCurrentState()) {
			case REST:
				log("lift at rest at floor " + lift.getCurrentFloor());
				//log("calling floor " + lift.getCallingFloor() + "\n");
				if(lift.hasPendingFloors()) {
					// System.out.println("controller:rest:WITH pending requests");
					OptionalInt nextFloor = lift.nextPendingFloor(LiftState.REST);
					// System.out.println("controller:rest:next floor: " + nextFloor);
					if (lift.getCurrentFloor() < nextFloor.getAsInt()) {
						lift.setCurrentState(LiftState.UP); 
						log("lift going up towards " + lift.getCallingFloor());
					}
					else if (lift.getCurrentFloor() > nextFloor.getAsInt()) {
						lift.setCurrentState(LiftState.DOWN); 
						log("lift going down towards " + lift.getCallingFloor());
					}
				}
				else {
					// System.out.println("controller:rest:NO pending requests");
					if (lift.getCurrentFloor() < lift.getCallingFloor()) {
						lift.setCurrentState(LiftState.UP); 
						log("lift going up towards " + lift.getCallingFloor());
					}
					else if (lift.getCurrentFloor() > lift.getCallingFloor()) {
						lift.setCurrentState(LiftState.DOWN); 
						log("lift going down towards " + lift.getCallingFloor());
					}
				}
				break;
			case UP:
				//log("lift going up\n");
				if (lift.getCurrentFloor() < lift.getCallingFloor()) {
					lift.setCurrentFloor(lift.getCurrentFloor() + 1);
					if (lift.getCurrentFloor() < lift.getCallingFloor())
						log("lift up passing floor " + lift.getCurrentFloor());
				}
				else {
					lift.setCurrentState(LiftState.REST);
					log("lift up arrived at floor " + lift.getCurrentFloor());
					if(lift.hasPendingFloors()) {
						OptionalInt nextFloor = lift.nextPendingFloor(LiftState.UP);
						if (lift.getCurrentFloor() != nextFloor.getAsInt()) {
							log("will proceed to floor " + nextFloor.getAsInt());
							lift.setCallingFloor(nextFloor.getAsInt());
						}
					}
				}
				break;
			case DOWN: 
				//log("lift going down\n");
				if (lift.getCurrentFloor() > lift.getCallingFloor()) {
					lift.setCurrentFloor(lift.getCurrentFloor() - 1);
					if (lift.getCurrentFloor() > lift.getCallingFloor())
						log("lift down passing floor " + lift.getCurrentFloor());
				}
				else {
					lift.setCurrentState(LiftState.REST);
					log("lift down arrived at floor " + lift.getCurrentFloor());
					if(lift.hasPendingFloors()) {
						OptionalInt nextFloor = lift.nextPendingFloor(LiftState.DOWN);
						if (lift.getCurrentFloor() != nextFloor.getAsInt()) {
							log("will proceed to floor " + nextFloor.getAsInt());
							lift.setCallingFloor(nextFloor.getAsInt());
						}
					}
				}
				break;
		}

	}


}
