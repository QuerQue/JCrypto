package com.cipherlibrary;

/**
 * Enum representing all modes in which encryption and decrpytion can take place.
 * Originally all the ciphers based upon only upper case letters thus encryption and decryption
 * can produce output consisting only of upper case letters, but this library also provides
 * two alternative modes preserving spaces in input String or additionally preserving all punctuation marks
 * present in input String.
 */
public enum CipherMode {
    /**
     * Mode in which output string of encryption and decryption processes will consist solely of upper case letters.
     * For example "Super secret text!" after encryption and decryption will be converted into: "SUPERSECRETTEXT".
     */
    LETTERS_ONLY {
        @Override
        public String toString(){
            return this.name() + " mode - converts text to upper case and encrypt only letters";
        }
    },
    /**
     * Mode in which output string of encryption and decryption processes will consist of upper case letters
     * having spaces and punctuation marks left intact.
     * For example "Super secret text!" after encryption and decryption will be converted into: "SUPER SECRET TEXT!".
     */
    PUNCTUATION_MARKS {
        @Override
        public String toString(){
            return this.name() + " mode - converts text to upper case and encrypt letters\n" +
                    "leaving spaces and punctuation marks intact";
        }
    },
    /**
     * Mode in which output string of encryption and decryption processes will consist of upper case letters and spaces left intact.
     * For example "Super secret text!" after encryption and decryption will be converted into: "SUPER SECRET TEXT".
     */
    SPACES {
        @Override
        public String toString(){
            return this.name() + " mode - converts text to upper case and encrypt letters\n" +
                    "leaving spaces intact";
        }
    }
}
