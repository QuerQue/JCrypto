package com.cipherlibrary;


/**
 * Represents a machine, which can encrypt or decrypt text, adding 3
 * to a position of a letter in an alphabet (starting from 1)
 */
public class CaesarEncryptor extends Encryptor {
    protected int modifier;

    /**
     * Creates a new CaesarEncryptor with the given perimeters.
     * @param mode can be chosen from LETTERS_ONLY, PUNCTUATION_MARKS and SPACES.
     * @see CipherMode
     */
    public CaesarEncryptor(CipherMode mode) {
        super(mode);
        modifier = 3;
    }
    /**
     * Returns encrypted input.
     * @param input string to encrypt.
     * @return encrypted string.
     */
    @Override
    public String encrypt(String input) {
        return encryptCesar(input);
    }

    /**
     * Returns decrypted input.
     * @param input string to decrypt.
     * @return decrypted string.
     */
    @Override
    public String decrypt(String input) {
        return decryptCesar(input);
    }


    private String encryptCesar(String input) {

        String res = "";
        input = input.toUpperCase();
        char x[ ] = input.toCharArray( );
        switch (mode) {
            case PUNCTUATION_MARKS: {

                for (int i = 0; i != x.length; i++) {
                    if (x[i] >= 'A' && x[i] <= 'Z') {
                        x[i]+= modifier%26;
                        if(x[i]>'Z')
                            x[i]= (char)('A' + x[i]%'Z' -1);
                        res += x[i];
                    } else if ((x[i]== ' ') || (x[i] >= '!' && x[i] <= '@') || (x[i]>= '[' && x[i]<= '`')
                            || (x[i] >= '{' && x[i]<= '~')) {
                        res += x[i];
                    }

                }
                break;
            }


            case LETTERS_ONLY: {

                for (int i = 0; i != x.length; i++) {
                    if (x[i] >= 'A' && x[i] <= 'Z') {
                        x[i]+= modifier%26;
                        if(x[i]>'Z')
                            x[i]= (char)('A' + x[i]%'Z' -1);
                        res += x[i];
                    }
                }
                break;
            }


            case SPACES: {

                for (int i = 0; i != x.length; i++) {
                    if (x[i] >= 'A' && x[i] <= 'Z') {
                        x[i]+= modifier%26;
                        if(x[i]>'Z')
                            x[i]= (char)('A' + x[i]%'Z' -1);
                        res += x[i];
                    } else if (x[i] == ' ') {
                        res += x[i];
                    }
                }
                break;
            }
        }

        return res;

    }


    private String decryptCesar(String input) {

        String res = "";
        input = input.toUpperCase();
        char x[ ] = input.toCharArray( );

        switch (mode) {
            case PUNCTUATION_MARKS: {

                for (int i = 0; i != x.length; i++) {
                    if (x[i] >= 'A' && x[i] <= 'Z') {
                        x[i]-= modifier%26;
                        if(x[i]<'A')
                            x[i]= (char)('Z'+1 -('A'-x[i]));
                        res += x[i];
                    } else if ((x[i]== ' ') || (x[i] >= '!' && x[i] <= '@') || (x[i]>= '[' && x[i]<= '`')
                            || (x[i] >= '{' && x[i]<= '~')) {
                        res += x[i];
                    }

                }
                break;
            }


            case LETTERS_ONLY: {

                for (int i = 0; i != x.length; i++) {
                    if (x[i] >= 'A' && x[i] <= 'Z') {
                        x[i]-= modifier%26;
                        if(x[i]<'A')
                            x[i]= (char)('Z'+1 -('A'-x[i]));
                        res += x[i];
                    }
                }
                break;
            }


            case SPACES: {

                for (int i = 0; i != x.length; i++) {
                    if (x[i] >= 'A' && x[i] <= 'Z') {
                        x[i]-= modifier%26;
                        if(x[i]<'A')
                            x[i]= (char)('Z'+1 -('A'-x[i]));
                        res += x[i];
                    } else if (x[i] == ' ') {
                        res += x[i];
                    }
                }
                break;
            }
        }

        return res;
    }


}