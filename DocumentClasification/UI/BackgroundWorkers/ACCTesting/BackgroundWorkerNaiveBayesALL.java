package UI.BackgroundWorkers.ACCTesting;

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
class BackgroundWorkerNaiveBayesALL extends SwingWorker {

    private final GUI gui;

    /**
     * Instantiates a new Nav 2 b.
     *
     * @param gui the gui
     */
    public BackgroundWorkerNaiveBayesALL(GUI gui) {
        this.gui = gui;
    }

    @Override
    protected Object doInBackground() throws Exception {

        gui.naiveBayesAccuracyExecute(gui.loadTestFiles());
        return null;
    }
}
