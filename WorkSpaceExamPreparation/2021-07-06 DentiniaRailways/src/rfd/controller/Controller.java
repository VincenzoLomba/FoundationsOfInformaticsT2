package rfd.controller;

import java.util.List;

import rfd.model.Route;

public interface Controller {
	List<String> getStationNames();
	List<Route>  getDirectRoutes(String from, String to);
	List<Route>  getIndirectRoutes(String from, String to);
}
