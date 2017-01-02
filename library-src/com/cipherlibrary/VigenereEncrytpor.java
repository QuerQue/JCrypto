package com.cipherlibrary;

/**
 * Class representing a machine capable of encrypting and decrypting input text
 * using provided key and Vigenere Cipher. <b>VigenereEncryptor</b> inherits from Encryptor
 * thus it also implements Encryptable Interface.
 * @see Encryptor
 * @see Encryptable
 */
public class VigenereEncrytpor extends Encryptor  {
    /**
     * Constructor for <b>VigenereEncryptor</b> taking cipher key and mode as parameters.
     * @param key  Key used in encryption, should consist of letters and will be made upper case during the process.
     * @param mode Mode in which encryption will take place.
     * @see CipherMode
     * @see Encryptor
     * @see Encryptable
     */
    public VigenereEncrytpor(String key, CipherMode mode) {
        super(mode);
        this.key = key.toUpperCase();
    }

    private String key;

    /**
     * Retrieves the key used in encryption.
     * @return Key used in encryption as a String object.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets key used in encryption. Key, should consist of letters and will be made upper case during the process.
     * @param key Key used in encryption.
     */
    public void setKey(String key) {
        this.key = key.toUpperCase();
    }

    /**
     * Overridden Encryptable interface method. Encrypts given text using key set by setKey()
     * or in class constructor and Vignere Cipher.
     * @param input Text to encrypt.
     * @return Encrypted text.
     * @see Encryptable
     */
    @Override
    public String encrypt(String input) {
        return encryptVigenere(input);
    }

    /**
     * Overridden Encryptable interface method. Decrypts given text using key set by setKey()
     * or in class constructor and Vignere Cipher.
     * @param input Text to decrypt.
     * @return Decrypted text.
     * @see Encryptable
     */
    @Override
    public String decrypt(String input) {
        return decryptVigenere(input);
    }

    private String encryptVigenere(String input) {

        String res = "";
        input = input.toUpperCase();
        switch (mode) {
            case LETTERS_ONLY : {
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                        j = ++j % key.length();
                    }
                }
                break;
            }
            case PUNCTUATION_MARKS: {
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                        j = ++j % key.length();
                    }  else if ((c == ' ') || (c >= '!' && c <= '@') || (c >= '[' && c <= '`')
                            || (c >= '{' && c <= '~')) {
                        res += c;
                        j = ++j % key.length();
                    }
                }
                break;
            }
            case SPACES : {
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                        j = ++j % key.length();
                    }  else if ((c == ' ')) {
                        res += c;
                        j = ++j % key.length();
                    }
                }
                break;
            }
        }

        return res;
    }

    private String decryptVigenere(String input) {

        String res = "";
        input = input.toUpperCase();
        switch (mode) {
            case LETTERS_ONLY: {
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);

                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                        j = ++j % key.length();
                    }
                }
                break;
            }
            case PUNCTUATION_MARKS: {
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);

                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                        j = ++j % key.length();
                    } else if ((c == ' ') || (c >= '!' && c <= '@') || (c >= '[' && c <= '`')
                            || (c >= '{' && c <= '~')) {
                        res += c;
                        j = ++j % key.length();
                    }
                }
                break;
            }
            case SPACES: {
                for (int i = 0, j = 0; i < input.length(); i++) {
                    char c = input.charAt(i);

                    if (c >= 'A' && c <= 'Z') {
                        res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                        j = ++j % key.length();
                    } else if ((c == ' ')) {
                        res += c;
                        j = ++j % key.length();
                    }
                }
                break;
            }
        }
        return res;
    }
}
