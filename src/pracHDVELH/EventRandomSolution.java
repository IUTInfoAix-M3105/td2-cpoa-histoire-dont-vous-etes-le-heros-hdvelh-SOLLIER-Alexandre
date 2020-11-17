package pracHDVELH;

import myUtils.ErrorNaiveHandler;

import java.util.Random;

public class EventRandomSolution extends Event {
    private static final int ERROR_STATUS_BAD_SETTINGS = -1;
    private static final int DEFAULT_RANDOM_SOLUTION = 1;
    private static final String ERROR_MSG_BAD_SETTINGS = "Bad settings were entered";

    private final int dice;
    private int randomSolution;
    private final Random randomGenerator;
    private final int[] partition;
    private final String waitingMsg;
    private final String solutionAnnouncement;

    public EventRandomSolution(GUIManager gui, String data, int[] partition, String waitingMsg, String solutionAnnouncement) {
        super(gui, data);

        this.randomGenerator = new Random();
        this.partition = partition;
        this.waitingMsg = waitingMsg;
        this.solutionAnnouncement = solutionAnnouncement;

        // Check that partition has at least one member
        if (partition.length == 0)
            ErrorNaiveHandler.abort(ERROR_STATUS_BAD_SETTINGS, ERROR_MSG_BAD_SETTINGS);

        // Check that partition is sorted in ascending order, with unique numbers
        for (int i = 1; i < partition.length; ++i) {
            if (partition[i - 1] >= partition[i])
                ErrorNaiveHandler.abort(ERROR_STATUS_BAD_SETTINGS, ERROR_MSG_BAD_SETTINGS);
        }

        // Check that partition starts with a positive number
        if (partition[0] < 0)
            ErrorNaiveHandler.abort(ERROR_STATUS_BAD_SETTINGS, ERROR_MSG_BAD_SETTINGS);

        // We can figure out dice face count now
        this.dice = partition[partition.length - 1];
    }

    @Override
    public int interpretAnswer() {
        // Reset chosen path
        setChosenPath(0);

        // Check what partition we got
        while (getChosenPath() < partition.length && partition[getChosenPath()] < randomSolution) {
            setChosenPath(getChosenPath() + 1);
        }

        return getChosenPath();
    }

    @Override
    public Event run() {
        // Output current situation
        getGui().outputln(getData());

        // Draw a random number
        randomSolution = randomGenerator.nextInt(dice - 1) + 1;
        getGui().outputln(waitingMsg + randomSolution);

        // Interpret answer, and return next event
        interpretAnswer();
        getGui().outputln(solutionAnnouncement + getChosenPath());

        // Return next event
        return getDaughter(getChosenPath());
    }
}
