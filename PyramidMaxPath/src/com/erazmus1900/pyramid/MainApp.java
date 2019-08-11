package com.erazmus1900.pyramid;

import com.erazmus1900.pyramid.model.PathCounterFacadeBean;

public class MainApp {

	public static void main(String args[]) {

		new MainApp().mainCycle();

	}

	public void mainCycle() {

		PathCounterFacadeBean pcFacade = new PathCounterFacadeBean();
		int maxNumber = pcFacade.getMaxPathAmount();

		if (maxNumber > 0) {
			System.out.println("Path found.");
		} else {
			System.out.println("No appropirate path with described conditions was found.");
		}

	}
}
