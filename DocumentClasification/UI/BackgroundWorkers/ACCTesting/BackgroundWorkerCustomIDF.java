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
class BackgroundWorkerCustomIDF extends SwingWorker {

    private final GUI gui;

    /**
     * Instantiates a new Cus 22 b.
     *
     * @param gui the gui
     */
    public BackgroundWorkerCustomIDF(GUI gui) {
        this.gui = gui;
    }

    @Override
    protected Object doInBackground() throws Exception {

        gui.customAccuracyIDFExecute(gui.loadTestFiles());
        return null;
    }
}
