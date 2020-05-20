package Classification.Builders;

import Classification.Algorithms.NaiveBayesIDF;
import Parametrization.IDF.IDF;

import java.util.ArrayList;

public class NaiveBayesIDFBuilder {
    private ArrayList<IDF> trainedClasses;

    public NaiveBayesIDFBuilder setTrainedClasses(ArrayList<IDF> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    public NaiveBayesIDF createNaiveBayesIDF() {
        return new NaiveBayesIDF(trainedClasses);
    }
}