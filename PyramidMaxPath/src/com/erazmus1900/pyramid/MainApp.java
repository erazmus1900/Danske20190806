package com.erazmus1900.pyramid;

import com.erazmus1900.pyramid.model.PathCounterFacadeBean;

public class MainApp {

	public static void main(String args[]) {

		new MainApp().mainCycle();

	}

	public void mainCycle() {

		PathCounterFacadeBean pcFacade = new PathCounterFacadeBean();
		pcFacade.getMaxPathAmount();

		System.out.println("Path found.");

	}
}
