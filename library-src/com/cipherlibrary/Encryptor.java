package com.cipherlibrary;

/**
 * Base Class of all  Encryptor classes, implements Encryptable Interface.
 * @see Encryptable
 */
public abstract class Encryptor implements Encryptable {
    /**
     * Mode used in encryption and decryption.
     * @see CipherMode
     */
    protected CipherMode mode;

    /**
     * Base class constructor for all of Encryptor Classes.
     * @param mode Sets mode in which encryption will take place.
     * @see CipherMode
     */
    protected Encryptor(CipherMode mode) {
        this.mode = mode;
    }

    /**
     * Retrieves mode used in encryption and decryption. Implementati
     * @return Mode used in encrpytion/decrpytion
     * @see CipherMode
     */
    public CipherMode getMode() {
        return mode;
    }

    /**
     * Sets mode used in encryption and decryption.
     * @param mode Mode used in encrpytion/decrpytion
     * @see CipherMode
     */
    public void setMode(CipherMode mode) {
        this.mode = mode;
    }
}
