package com.cipherlibrary;

/**
 * Represents a machine, which can encrypt or decrypt text, adding 47
 * to a position of a letter in an alphabet (starting from 1)
 */
public class Rot47Encryptor extends CaesarEncryptor {
    /**
     * Creates a new Rot47Encryptor with the given perimeters.
     * @param mode can be chosen from LETTERS_ONLY, PUNCTUATION_MARKS and SPACES.
     * @see CipherMode
     */
    public Rot47Encryptor(CipherMode mode) {
        super(mode);
        modifier = 47;
        /**
         * @param modifier is changed to 47
         */
    }
}
