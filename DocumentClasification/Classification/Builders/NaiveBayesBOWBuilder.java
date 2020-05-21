package Classification.Builders;

import Classification.Algorithms.NaiveBayesBOW;
import Parametrization.BagOfWorld.BagOfWords;

import java.util.ArrayList;

/**
 * The type Naive bayes bow builder.
 */
public class NaiveBayesBOWBuilder {
    private ArrayList<BagOfWords> trainedClasses;

    /**
     * Sets trained classes.
     *
     * @param trainedClasses the trained classes
     * @return the trained classes
     */
    public NaiveBayesBOWBuilder setTrainedClasses(ArrayList<BagOfWords> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    /**
     * Create naive bayes bow naive bayes bow.
     *
     * @return the naive bayes bow
     */
    public NaiveBayesBOW createNaiveBayesBOW() {
        return new NaiveBayesBOW(trainedClasses);
    }
}