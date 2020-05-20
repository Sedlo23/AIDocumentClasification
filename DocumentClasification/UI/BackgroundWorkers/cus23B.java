package UI.BackgroundWorkers;

import UI.GUI;

import javax.swing.*;
import java.util.List;

/**
 * UI.BackgroundWorkers
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 18.05.20
 */
public
class cus23B extends SwingWorker {

    private final GUI gui;

    /**
     * Instantiates a new Cus 23 b.
     *
     * @param gui the gui
     */
    public cus23B(GUI gui) {
        this.gui = gui;
    }


    @Override
    protected Object doInBackground() throws Exception {
        gui.customAccuracyTFIDFExecute(gui.loadTestFiles());
        return null;
    }
}
