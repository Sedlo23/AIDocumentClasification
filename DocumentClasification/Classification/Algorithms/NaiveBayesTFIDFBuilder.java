package Classification.Algorithms;

import Parametrization.TFIDF.TFIDF;

import java.util.ArrayList;

public class NaiveBayesTFIDFBuilder {
    private ArrayList<TFIDF> trainedClasses;

    public NaiveBayesTFIDFBuilder setTrainedClasses(ArrayList<TFIDF> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    public NaiveBayesTFIDF createNaiveBayesTFIDF() {
        return new NaiveBayesTFIDF(trainedClasses);
    }
}