package com.cipherlibrary;


/**
 * Represents a machine, which can encrypt or decrypt text, using rail fence cipher
 * (the text is written downwards and diagonally on successive "rails" of an imaginary fence).
 */
public class RailFenceEncryptor extends Encryptor {

    private int fence;

    /**
     * Retrieves fence used in encryption/decryption
     * @return current height of the fence
     */
    public int getFence() {
        return fence;
    }

    /**
     * Sets fence used in encryption/decryption
     * @param fence to set
     */
    public void setFence(int fence) {
        this.fence = fence;
    }

    /**
     * Creates a new RailFenceEnryptor with the given perimeters.
     * @param fence the height of the fence must be greater than 1, otherwise will be set to 2
     * @param mode can be chosen from LETTERS_ONLY, PUNCTUATION_MARKS and SPACES
     */
    public RailFenceEncryptor(int fence, CipherMode mode) {
        super(mode);
        this.fence = fence;
        if(fence < 2)
            setFence(2);
    }

    /**
     * Returns encrypted input
     * @param input string to encrypt
     * @return encrypted string
     */
    @Override
    public String encrypt(String input) {
        return encryptRailFence(input);
    }

    /**
     * Returns decrypted input
     * @param input string to decrypt
     * @return decrypted string
     */
    @Override
    public String decrypt(String input) {
        return decryptRailFence(input);
    }

    private String encryptRailFence(String text) {
        String textToEncode = text.toUpperCase();
        StringBuilder[] rows = new StringBuilder[fence];
        int l = 0;
        int f = 1;
        char c;
        for (int i = 0; i < fence; i++)
            rows[i] = new StringBuilder();
        switch (mode) {
            case LETTERS_ONLY: {
                for (int i = 0; i < textToEncode.length(); i++) {
                    c = textToEncode.charAt(i);
                    if ((c == ' ') || (c >= '!' && c <= '@') || (c >= '[' && c <= '`'))
                        continue;
                    else {
                        rows[l].append(textToEncode.charAt(i));
                        l += f;
                        if (l == fence || l == -1) {
                            f *= -1;
                            l += f + f;
                        }
                    }
                }
                break;
            }


            case SPACES: {
                for (int i = 0; i < textToEncode.length(); i++) {
                    c = textToEncode.charAt(i);
                    if ((c >= '!' && c <= '@') || (c >= '[' && c <= '`'))
                        continue;
                    else {
                        rows[l].append(textToEncode.charAt(i));
                        l += f;
                        if (l == fence || l == -1) {
                            f *= -1;
                            l += f + f;
                        }
                    }
                }
                break;
            }

            case PUNCTUATION_MARKS: {
                for (int i = 0; i < textToEncode.length(); i++) {
                    rows[l].append(textToEncode.charAt(i));
                    l += f;
                    if (l == fence || l == -1) {
                        f *= -1;
                        l += f + f;
                    }
                }
                break;
            }
        }

        StringBuilder encrypted = new StringBuilder();
        for (StringBuilder e : rows)
            encrypted.append(e.toString());

        return encrypted.toString();
    }

    private String decryptRailFence(String text){
        String textToDecode = text.toUpperCase();
        int d[] = new int[fence];
        int l=0;
        int f=1;
        for(int i=0; i<textToDecode.length(); i++){
            d[l]++;
            l += f;
            if(l == fence || l == -1){
                f *= -1;
                l += f + f;
            }
        }
        String[] rows = new String[fence];
        int fi = 0;
        for(int i=0; i<d.length; i++){
            int li = d[i] + fi;
            rows[i] = textToDecode.substring(fi, li);
            fi = li;
        }

        StringBuilder decrypted = new StringBuilder();
        int[] ins = new int[fence];
        l = 0;
        f = 1;
        for(int i=0; i<textToDecode.length(); i++){
            decrypted.append(rows[l].charAt(ins[l]));
            ins[l]++;
            l += f;
            if(l == fence || l == -1){
                f *= -1;
                l += f + f;
            }
        }
        return decrypted.toString();
    }


}
