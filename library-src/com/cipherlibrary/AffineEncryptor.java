package com.cipherlibrary;

import java.math.BigInteger;
import java.util.*;
/**
 * Represents a machine, which can encrypt or decrypt text, using affine cipher
 * (each letter is enciphered with the function (ax + b) mod 26)
 */
public class AffineEncryptor extends Encryptor {

    private static final Set<Integer> acceptableAValues=new HashSet<Integer>();
    static {
        int count[] = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        for (int el : count) {
            acceptableAValues.add(el);
        }
    }

    /**
     * Creates a new AffineEncryptor with the given perimeters.
     * @param a the coefficient of x, a must be chosen such that a and 26 are coprime.
     * @param b the magnitude of the shift.
     * @param mode can be chosen from LETTERS_ONLY, PUNCTUATION_MARKS and SPACES.
     */
    public AffineEncryptor( int a, int b, CipherMode mode) {

        super(mode);

        this.b = b;
        if(acceptableAValues.contains(a)) {
            this.a = a;
        }
        else {
            this.a = 1;
        }

    }

    private int a;
    private int b;

    /**
     * Retrieves coefficient b
     * @return current coefficient b
     */
    public int getB() {
        return b;
    }

    /**
     * Sets coefficient b
     * @param b to set
     */
    public void setB(int b) {
        this.b = b;
    }

    /**
     * Retrieves coefficient a
     * @return current coefficient a
     */
    public int getA() {
        return a;
    }

    /**
     * Sets coefficient a
     * @param a to set. Coefficient a must be chosen such that a and 26 are coprime, otherwise a will be set to 1.
     */
    public void setA(int a) {
        if(acceptableAValues.contains(a))
            this.a = a;
        else
            this.a=1;
    }

    /**
     * Returns encrypted input.
     * @param input string to encrypt.
     * @return encrypted string.
     */
    @Override
    public String encrypt(String input) {
        return encryptAffine(input);
    }

    /**
     * Returns decrypted input.
     * @param input string to decrypt.
     * @return decrypted string.
     */
    @Override
    public String decrypt(String input) {
        return decryptAffine(input);
    }

    private String encryptAffine(String input) {

        String res = "";
        input = input.toUpperCase();

        switch (mode) {
            case LETTERS_ONLY : {
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z'){
                        res += (char) (((c)*a +b)%26 +65);
                    }
                }
                break;
            }
            case PUNCTUATION_MARKS: {
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c*a +b)%26 +65);
                    }  else if ((c == ' ') || (c >= '!' && c <= '@') || (c >= '[' && c <= '`')
                            || (c >= '{' && c <= '~')) {
                        res += c;
                    }
                }
                break;
            }
            case SPACES : {
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c*a +b)%26 +65);
                    }  else if ((c == ' ')) {
                        res += c;
                    }
                }
                break;
            }
        }

        return res;
    }

    private String decryptAffine(String input) {

        String res = "";
        input = input.toUpperCase();

        switch (mode) {
            case LETTERS_ONLY : {
                BigInteger inverse = BigInteger.valueOf(a).modInverse(BigInteger.valueOf(26));
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z'){
                        res += (char) (inverse.intValue()*(c-b)%26+65);
                    }
                }
                break;
            }
            case PUNCTUATION_MARKS: {
                BigInteger inverse = BigInteger.valueOf(a).modInverse(BigInteger.valueOf(26));
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) (inverse.intValue()*(c-b)%26+65);
                    }  else if ((c == ' ') || (c >= '!' && c <= '@') || (c >= '[' && c <= '`')
                            || (c >= '{' && c <= '~')) {
                        res += c;
                    }
                }
                break;
            }
            case SPACES : {
                BigInteger inverse = BigInteger.valueOf(a).modInverse(BigInteger.valueOf(26));
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) (inverse.intValue()*(c-b)%26+65);
                    }  else if ((c == ' ')) {
                        res += c;
                    }
                }
                break;
            }
        }

        return res;
    }
}
