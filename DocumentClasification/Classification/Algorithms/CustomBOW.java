package Classification.Algorithms;


import Classification.Interfaces.ILearningAlgorithm;
import Parametrization.BagOfWorld.BagOfWords;
import Tools.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * The type Custom BackgroundWorkerBOW.
 */
public class CustomBOW implements ILearningAlgorithm
{

    private final ArrayList<BagOfWords> TrainedClasses;

    /**
     * Instantiates a new Custom BackgroundWorkerBOW.
     *
     * @param TrainedClasses the trained classes
     */
    public CustomBOW(ArrayList<BagOfWords> TrainedClasses)
    {
        this.TrainedClasses = TrainedClasses;
    }

    /**
     * Gets string output.
     *
     * @param input the input
     * @return the string output
     */
    public String getStringOutput(String input)
    {

        BagOfWords bag =new BagOfWords(" ");


        input = input.replaceAll("\n"," ");

        for (String s:input.split(" "))
        {
            bag.add(s);
        }

        for (BagOfWords bag1: TrainedClasses)
        {
            bag1.setWeight(0);
        }

        for(HashMap.Entry<String, Integer> entry : bag.getBagHashMap().entrySet())
        {
            String key = entry.getKey();

            for (BagOfWords bag1: TrainedClasses)
            {
                if (bag1.countOccurrences(key)>0)
                {
                    double pX=bag.countOccurrences(key);

                    double pY=(bag.getBagHashMap().size());

                    double z=(pX+pY);

                    double pX2=bag1.countOccurrences(key);

                    double pY2=(bag1.getBagHashMap().size());

                    double z2=(pX2+pY2);

                    double c=Math.pow(z*z2,2);

                    bag1.setWeight(Math.sqrt(Math.pow(bag1.getWeight(),2) +Math.pow(Math.sqrt(c),2))/bag1.getBagHashMap().size());

                }

            }

        }

        TrainedClasses.sort(new Comparator<BagOfWords>() {
            @Override
            public int compare(BagOfWords bagOfWords, BagOfWords t1) {

                if (bagOfWords.getWeight() < t1.getWeight())
                    return 1;
                else return -1;
            }
        });

        String[] output=new String[TrainedClasses.size()];

        for (int i = 0; i< TrainedClasses.size(); i++)
        {
            output[i]= TrainedClasses.get(i).getName();
        }

        return Text.formatOutput(output,this.getClass().getSimpleName());
    }

    /**
     * Gets accuracy with lab format.
     *
     * @param input the input
     * @return the accuracy with lab format
     */
    public double getAccuracyWithLabFormat(String input)
    {

        BagOfWords bag =new BagOfWords(" ");

        String tt=input;

        input = input.replaceAll("\n"," ");

        for (String s:input.split(" "))
        {
            bag.add(s);
        }

        for (BagOfWords bag1: TrainedClasses)
        {
            bag1.setWeight(0);
        }

        for(HashMap.Entry<String, Integer> entry : bag.getBagHashMap().entrySet())
        {
            String key = entry.getKey();

            for (BagOfWords bag1: TrainedClasses)
            {
                if (bag1.countOccurrences(key)>0)
                {
                    double pX=bag.countOccurrences(key);

                    double pY=bag1.countOccurrences(key);

                    double pN=bag.getBagHashMap().size();

                    double z=(pX*Math.log((pN/pY)));


                  

                    bag1.setWeight( bag1.getWeight() +((z)));
                }

            }

        }

        for (BagOfWords bag1: TrainedClasses)
           bag1.setWeight( Math.sqrt(bag1.getWeight()));

        TrainedClasses.sort(new Comparator<BagOfWords>() {
            @Override
            public int compare(BagOfWords bagOfWords, BagOfWords t1) {

                if (bagOfWords.getWeight() < t1.getWeight())
                    return 1;
                else return -1;
            }
        });

        int i= TrainedClasses.size();

        for (BagOfWords bag1: TrainedClasses)
        {
            if (tt.split("\n")[0].contains(bag1.getName()))
            {
                i=Math.min(i, TrainedClasses.indexOf(bag1));
            }

        }

        return  (100/Math.pow(i+1,2));
    }


}
