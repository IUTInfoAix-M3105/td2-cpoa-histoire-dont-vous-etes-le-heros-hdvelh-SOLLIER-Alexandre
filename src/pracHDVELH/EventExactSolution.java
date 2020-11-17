package pracHDVELH;

public class EventExactSolution extends Event {
    public static final String MSG_WRONG_ANSWER = "Wrong answer!";

    String solution;

    public EventExactSolution(GUIManager gui, String data, String solution) {
        super(gui, data);

        this.solution = solution;
    }

    @Override
    public int interpretAnswer() {
        // Return 0 if player answer is correct, 1 otherwise
        if (getPlayerAnswer().equals(solution)) {
            setChosenPath(0);
        } else {
            getGui().outputln(MSG_WRONG_ANSWER);
            setChosenPath(1);
        }

        return getChosenPath();
    }
}
