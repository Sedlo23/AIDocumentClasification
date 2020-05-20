package Parametrization.IDF;

/**
 * Parametrization.IDF
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 15.05.20
 */
public class Word
{
    private double freq;

    private String value;

    private boolean set;

    /**
     * Instantiates a new Word.
     *
     * @param value the value
     */
    public Word(String value)
    {
        this(value,0);
    }

    /**
     * Instantiates a new Word.
     *
     * @param value the value
     * @param freq  the freq
     */
    public Word(String value, double freq)
    {
        this.value=value;
        this.freq=freq;
        this.setSet(false);
    }

    @Override
    public boolean equals(Object o) {
        return value.equals(o);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Gets freq.
     *
     * @return the freq
     */
    public double getFreq() {
        return freq;
    }

    /**
     * Sets freq.
     *
     * @param freq the freq
     */
    public void setFreq(double freq) {
        this.freq = freq;
    }

    /**
     * Add freq.
     *
     * @param freq the freq
     */
    public void addFreq(double freq) {
        this.freq += freq;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Is set boolean.
     *
     * @return the boolean
     */
    public boolean isSet() {
        return set;
    }

    /**
     * Sets set.
     *
     * @param set the set
     */
    public void setSet(boolean set) {
        this.set = set;
    }
}
