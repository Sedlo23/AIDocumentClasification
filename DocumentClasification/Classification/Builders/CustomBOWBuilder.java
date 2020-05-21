package Classification.Builders;

import Classification.Algorithms.CustomBOW;
import Parametrization.BagOfWorld.BagOfWords;

import java.util.ArrayList;

/**
 * The type Custom bow builder.
 */
public class CustomBOWBuilder {
    private ArrayList<BagOfWords> trainedClasses;

    /**
     * Sets trained classes.
     *
     * @param trainedClasses the trained classes
     * @return the trained classes
     */
    public CustomBOWBuilder setTrainedClasses(ArrayList<BagOfWords> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    /**
     * Create custom bow custom bow.
     *
     * @return the custom bow
     */
    public CustomBOW createCustomBOW() {
        return new CustomBOW(trainedClasses);
    }


}