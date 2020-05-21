package UI.BackgroundWorkers.Generators;

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
class BackgroundWorkerBOW extends SwingWorker {

    private final GUI gui;

    /**
     * Instantiates a new Cus 2 b.
     *
     * @param gui the gui
     */
    public BackgroundWorkerBOW(GUI gui) {
        this.gui = gui;
    }

    @Override
    protected Object doInBackground() throws Exception {

        gui.generateBOW();
        return null;
    }
}
