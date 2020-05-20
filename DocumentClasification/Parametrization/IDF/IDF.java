package Parametrization.IDF;


import Parametrization.BagOfWords;

import java.util.ArrayList;
import java.util.Map;

/**
 * The type Idf.
 */
public class IDF
{

    private WordsSets wordsSets;

    private ArrayList<BagOfWords>bagsOfWords;

    private String name;

    private  double weight;

    private  double fileCount;

    /**
     * Instantiates a new Idf.
     *
     * @param bagsOfWords the bags of words
     * @param name        the name
     */
    public IDF(ArrayList<BagOfWords> bagsOfWords,String name)
    {
        this.setWeight(0);

        this.setFileCount(0);

        this.bagsOfWords=bagsOfWords;

        
        this.name=name;

        wordsSets= new WordsSets(name);

        for (BagOfWords bag:bagsOfWords)
        {
            
            WordsSets tmp =wordsSets;

            for (Word w:tmp.getWordList())
                w.setSet(false);
            
            for (Map.Entry entry:bag.getBagHashMap().entrySet())
            {
                Word word=new Word((String) entry.getKey());

                if (!tmp.getWordList().contains(word))
                        tmp.getWordList().add(word);

                for (Word w:tmp.getWordList())
                {
                    if (w.getValue().equals(entry.getKey()))
                        if (!w.isSet())
                        {
                            w.setSet(true);
                            w.addFreq(1);
                        }
                }
                
            }
            
            }

        for (Word w:wordsSets.getWordList())
            {
                w.setFreq((bagsOfWords.size()/w.getFreq()));
            }
        

    }

    /**
     * Gets words sets.
     *
     * @return the words sets
     */
    public WordsSets getWordsSets() {
        return wordsSets;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets file count.
     *
     * @return the file count
     */
    public double getFileCount() {
        return fileCount;
    }

    /**
     * Sets file count.
     *
     * @param fileCount the file count
     */
    public void setFileCount(double fileCount) {
        this.fileCount = fileCount;
    }
}

