package com.cipherlibrary;


/**
 * Represents a machine, which can encrypt or decrypt text, using AtBash cipher
 * (inversion of the alphabet (A to Z, B to Y etc.)
 */
public class AtBashEncryptor extends Encryptor {

    /**
     * Creates a new AtBashEncryptor (class constructor)
     * @param mode can be chosen from LETTERS_ONLY, PUNCTUATION_MARKS and SPACES.
     * @see CipherMode
     */
    public AtBashEncryptor(CipherMode mode) {
        super(mode);
    }

    /**
     * Returns encrypted input
     * @param input string to encrypt
     * @return encrypted string
     */
    @Override
    public String encrypt(String input) {
        return encryptAtBash(input);
    }

    /**
     * Returns decrypted input
     * @param input string to decrypt
     * @return decrypted string
     */
    @Override
    public String decrypt(String input) {
        return encryptAtBash(input);
    }

    private String encryptAtBash(String input){

        String res="";
        input=input.toUpperCase();
        switch(mode){
            case LETTERS_ONLY:{
                for (int i = 0; i < input.length(); i++){
                    char c = input.charAt(i);
                    if(c>='A' && c<='Z'){
                        res+=switchLetters(c);
                    }
                }
                break;
            }
            case PUNCTUATION_MARKS:{
                for (int i = 0; i < input.length(); i++){
                    char c = input.charAt(i);
                    if(c>='A' && c<='Z'){
                        res+=switchLetters(c);
                    }else if ((c == ' ') || (c >= '!' && c <= '@') || (c >= '[' && c <= '`')
                            || (c >= '{' && c <= '~')) {
                        res += c;
                    }
                }
                break;
            }
            case SPACES:{
                for (int i = 0; i < input.length(); i++){
                    char c = input.charAt(i);
                    if(c>='A' && c<='Z'){
                        res+=switchLetters(c);
                    } else if ((c == ' ')) {
                        res += c;
                    }
                }
                break;
            }
        }
        return res;
    }

    /**
     * Encrypts one letter
     * @param c one letter in string
     * @return encrypted/decrypted letter
     */
    private char switchLetters(char c) {
        return (char) (25 - (c - 'A') + 'A');
    }


}



