package Classification.Builders;

import Classification.Algorithms.CustomTFIDF;
import Parametrization.TFIDF.TFIDF;

import java.util.ArrayList;

public class CustomTFIDFBuilder {
    private ArrayList<TFIDF> trainData;

    public CustomTFIDFBuilder setTrainData(ArrayList<TFIDF> trainData) {
        this.trainData = trainData;
        return this;
    }

    public CustomTFIDF createCustomTFIDF() {
        return new CustomTFIDF(trainData);
    }
}