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
class cus21B extends SwingWorker {

    private final GUI gui;

    /**
     * Instantiates a new Cus 21 b.
     *
     * @param gui the gui
     */
    public cus21B(GUI gui) {
        this.gui = gui;
    }

    @Override
    protected Object doInBackground() throws Exception {

        gui.customAccuracyBOWExecute(gui.loadTestFiles());
        return null;
    }
}
