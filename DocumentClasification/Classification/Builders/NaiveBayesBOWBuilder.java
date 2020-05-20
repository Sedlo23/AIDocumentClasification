package Classification.Builders;

import Classification.Algorithms.NaiveBayesBOW;
import Parametrization.BagOfWords;

import java.util.ArrayList;

public class NaiveBayesBOWBuilder {
    private ArrayList<BagOfWords> trainedClasses;

    public NaiveBayesBOWBuilder setTrainedClasses(ArrayList<BagOfWords> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    public NaiveBayesBOW createNaiveBayesBOW() {
        return new NaiveBayesBOW(trainedClasses);
    }
}