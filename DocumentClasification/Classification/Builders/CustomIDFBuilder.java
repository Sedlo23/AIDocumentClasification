package Classification.Builders;

import Classification.Algorithms.CustomIDF;
import Parametrization.IDF.IDF;

import java.util.ArrayList;

public class CustomIDFBuilder {
    private ArrayList<IDF> trainedClasses;

    public CustomIDFBuilder setTrainedClasses(ArrayList<IDF> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    public CustomIDF createCustomIDF() {
        return new CustomIDF(trainedClasses);
    }
}