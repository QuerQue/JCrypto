package com.cipherlibrary;
/**
 * Represents a machine, which can encrypt or decrypt text, adding 13
 * to a position of a letter in an alphabet (starting from 1)
 */
public class Rot13Encryptor extends CaesarEncryptor {

    /**
     * Creates a new Rot13Encryptor with the given perimeters.
     * @param mode can be chosen from LETTERS_ONLY, PUNCTUATION_MARKS and SPACES.
     * @see CipherMode
     */
    public Rot13Encryptor(CipherMode mode) {
        super(mode);
        modifier = 13;
        /**
         * @param modifier is changed to 13
         */
    }
}
