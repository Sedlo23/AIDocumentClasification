package Parametrization;

import java.util.HashMap;


/**
 * The type Bag of words.
 */
public class BagOfWords {

    private final HashMap<String, Integer> bagHashMap;

    private final String name;

    /**
     * Instantiates a new Bag of words.
     */
    public BagOfWords()
    {
        this("");
    }

    private double weight ;

    /**
     * Instantiates a new Bag of words.
     *
     * @param name the name
     */
    public BagOfWords(String name)
    {
        bagHashMap = new HashMap<String, Integer>(10);

        this.setWeight(0);

        this.name=name;
    }

    /**
     * Add.
     *
     * @param word the word
     */
    public void add(String word)
    {
        if (bagHashMap.containsKey(word))
        {
            bagHashMap.replace(word, bagHashMap.get(word), bagHashMap.get(word) + 1);
        }
        else
        {
            bagHashMap.put(word, 1);
        }

    }

    /**
     * Count occurrences int.
     *
     * @param word the word
     * @return the int
     */
    public int countOccurrences(String word)
    {
        if(bagHashMap.containsKey(word))
        {
            return bagHashMap.get(word);
        }
        return 0;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        if (name=="")
            return bagHashMap.toString();

        return name;
    }

    /**
     * Gets bag hash map.
     *
     * @return the bag hash map
     */
    public HashMap<String, Integer> getBagHashMap() {
        return bagHashMap;
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


}
