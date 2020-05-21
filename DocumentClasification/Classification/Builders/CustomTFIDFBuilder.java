package Classification.Builders;

import Classification.Algorithms.CustomTFIDF;
import Parametrization.TFIDF.TFIDF;

import java.util.ArrayList;

/**
 * The type Custom tfidf builder.
 */
public class CustomTFIDFBuilder {
    private ArrayList<TFIDF> trainData;

    /**
     * Sets train data.
     *
     * @param trainData the train data
     * @return the train data
     */
    public CustomTFIDFBuilder setTrainData(ArrayList<TFIDF> trainData) {
        this.trainData = trainData;
        return this;
    }

    /**
     * Create custom tfidf custom tfidf.
     *
     * @return the custom tfidf
     */
    public CustomTFIDF createCustomTFIDF() {
        return new CustomTFIDF(trainData);
    }
}