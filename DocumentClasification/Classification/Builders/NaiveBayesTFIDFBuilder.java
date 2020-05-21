package Classification.Builders;

import Classification.Algorithms.NaiveBayesTFIDF;
import Parametrization.TFIDF.TFIDF;

import java.util.ArrayList;

/**
 * The type Naive bayes tfidf builder.
 */
public class NaiveBayesTFIDFBuilder {
    private ArrayList<TFIDF> trainedClasses;

    /**
     * Sets trained classes.
     *
     * @param trainedClasses the trained classes
     * @return the trained classes
     */
    public NaiveBayesTFIDFBuilder setTrainedClasses(ArrayList<TFIDF> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    /**
     * Create naive bayes tfidf naive bayes tfidf.
     *
     * @return the naive bayes tfidf
     */
    public NaiveBayesTFIDF createNaiveBayesTFIDF() {
        return new NaiveBayesTFIDF(trainedClasses);
    }
}