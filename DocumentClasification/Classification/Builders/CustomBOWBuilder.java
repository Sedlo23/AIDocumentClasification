package Classification.Builders;

import Classification.Algorithms.CustomBOW;
import Parametrization.BagOfWords;

import java.util.ArrayList;

public class CustomBOWBuilder {
    private ArrayList<BagOfWords> trainedClasses;

    public CustomBOWBuilder setTrainedClasses(ArrayList<BagOfWords> trainedClasses) {
        this.trainedClasses = trainedClasses;
        return this;
    }

    public CustomBOW createCustomBOW() {
        return new CustomBOW(trainedClasses);
    }


}