/**
 * File: ScenarioDG.java
 * Creation: 7 nov. 2020, Jean-Philippe.Prost@univ-amu.fr
 * Template étudiants
 */
package pracHDVELH;

import myUtils.ErrorNaiveHandler;

import java.util.Objects;

/**
 * @author prost
 *
 */
public class Scenario {
	private static final String MSG_EMPTY_SCENARIO = "Sorry, no scenario was found.";
	private static final String MSG_FINALE = "That's all folks!";
	private final Event head;
	private final GUIManager gui;

	public Scenario(GUIManager gui, Event startEvent) {
		this.head = startEvent;
		this.gui = gui;
	}

	public Event getHead() {
		return head;
	}

	public GUIManager getGui() {
		return gui;
	}

	public String run() {
		// Check if there's a head
		if (Objects.isNull(head))
			return MSG_EMPTY_SCENARIO;

		// Set current event as head
		Event currentEvent = head;

		// Loop while the current event is not the final one
		while (!currentEvent.isFinal()) {
			Event nextEvent;
			if (!Objects.isNull(nextEvent = currentEvent.run()))
				currentEvent = nextEvent;
			else
				ErrorNaiveHandler.warning(MSG_EMPTY_SCENARIO);
		}

		return MSG_FINALE;
	}

	/* MAIN */
	public static void main(String[] args) {
		Scenario scenario;
		GUIManager gui = new GUIManager(System.in, System.out, System.err);

		// S
		Event startEvent = new Event(gui, "Go!\n" + "(1)1 (2)2");

		// 1
		Event event1 = new Event(gui, "event1:\n" + "(1)1.1 (2)1.2");

		// 2
		Event event2 = new Event(gui, "event2:\n" + "(1)2.1 (2)2.2");

		// E
		Event endEvent = new Event(gui, "End event: that's it :-)");

		/*
		 * S => 1
		 * S => 2
		 */
		startEvent.addDaughter(event1);
		startEvent.setDaughter(event2, 1);

		/*
		 * 1 (=> 1.1) => S
		 * 1 (=> 1.2) => E
		 */
		event1.addDaughter(startEvent);
		event1.addDaughter(endEvent);

		/*
		 * 2 (=> 2.1) => 1
		 * 2 (=> 2.2) => S
		 */
		event2.addDaughter(event1);
		event2.addDaughter(startEvent);

		scenario = new Scenario(gui, startEvent);

//		// *2
//		// ...
//		// **2.3:event3
//		// ***E
//		// ***event3
//
//		Event event3 = new EventExactSolution(gui, "Wizard: how much is worth pi?", "3.14159");
//		event2.setData(event2.getData() + " (3)2.3");
//		event2.addDaughter(event3);
//		event3.addDaughter(endEvent);
//		event3.addDaughter(event3);
//
//		/* ******* */
//		// **2.3
//		// ***event4
//		// ****event2
//		// ****E
//		// ****event3
//		// ...
//
//		int[] mask = { 3, 6, 7 };
//		Event event4 = new EventRandomSolution(gui, "Random choice of the next event...", mask, "Dice rolling... Roll=",
//				"\nNext event is ");
//		event3.setDaughter(event4, 0);
//		event4.addDaughter(event2);
//		event4.addDaughter(endEvent);
//		event4.addDaughter(event3);

		System.out.println(scenario.run());
	}
}

// eof