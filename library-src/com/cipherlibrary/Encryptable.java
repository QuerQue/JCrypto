package com.cipherlibrary;

/**
 * Interface providing methods controlling all of the Encryptor classes.
 * @see Encryptor
 */
public interface Encryptable {
    /**
     * Method used to encrypt given input text using a cipher implemented by calling Encryptor class.
     * @param input Text to encrypt.
     * @return Encrypted text as String.
     * @see Encryptor
     */
    String encrypt(String input);
    /**
     * Method used to decrypt given input text using a cipher implemented by calling Encryptor class.
     * @param input Text to decrypt.
     * @return Decrypted text as String.
     * @see Encryptor
     */
    String decrypt(String input);

    /**
     * Sets the encryption mode used during encryption and decryption
     * @param mode Mode used in encryption.
     * @see CipherMode
     */
    void setMode(CipherMode mode);

    /**
     * Retrieves mode used in encryption and decryption process.
     * @return CipherMode, mode used in encryption
     * @see CipherMode
     */
    CipherMode getMode();
}
