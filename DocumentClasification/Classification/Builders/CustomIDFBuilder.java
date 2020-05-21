package Classification.Builders;

import Classification.Algorithms.CustomIDF;
import Parametrization.IDF.IDF;

import java.util.ArrayList;

/**
 * The type Custom idf builder.
 */
public class CustomIDFBuilder {
    private ArrayList<IDF> trainedClasses;

    /**
     * Sets trained classes.
     *
     * @param trainedClasses the trained classes
     * @return the trained classes
     */
    public CustomIDFBuilder setTrainedClasses(ArrayList<IDF> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    /**
     * Create custom idf custom idf.
     *
     * @return the custom idf
     */
    public CustomIDF createCustomIDF() {
        return new CustomIDF(trainedClasses);
    }
}