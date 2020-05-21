package Classification.Builders;

import Classification.Algorithms.NaiveBayesIDF;
import Parametrization.IDF.IDF;

import java.util.ArrayList;

/**
 * The type Naive bayes idf builder.
 */
public class NaiveBayesIDFBuilder {
    private ArrayList<IDF> trainedClasses;

    /**
     * Sets trained classes.
     *
     * @param trainedClasses the trained classes
     * @return the trained classes
     */
    public NaiveBayesIDFBuilder setTrainedClasses(ArrayList<IDF> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    /**
     * Create naive bayes idf naive bayes idf.
     *
     * @return the naive bayes idf
     */
    public NaiveBayesIDF createNaiveBayesIDF() {
        return new NaiveBayesIDF(trainedClasses);
    }
}