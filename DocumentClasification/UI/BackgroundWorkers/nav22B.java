package UI.BackgroundWorkers;

import UI.GUI;

import javax.swing.*;

/**
 * UI.BackgroundWorkers
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 18.05.20
 */
public
class nav22B extends SwingWorker {


    private final GUI gui;

    /**
     * Instantiates a new Nav 22 b.
     *
     * @param gui the gui
     */
    public nav22B(GUI gui) {
        this.gui = gui;
    }

    @Override
    protected Object doInBackground() throws Exception {

        gui.naiveBayesAccuracyIDFExecute(gui.loadTestFiles());
        return null;
    }
}
