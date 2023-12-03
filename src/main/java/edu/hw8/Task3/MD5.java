package edu.hw8.Task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * MD5 class.
 */
public final class MD5 {
    private static final Logger MD_LOG = Logger.getLogger(MD5.class.getName());

    private static final String MD5_ALGORITHM = "MD5";

    private static final int DIGIT_BYTE = 0xff;
    private static final String HEX_TWO_NUMBER_FORMAT = "%02x";

    private static final String ALGORITHM_EXCEPTION_MESSAGE = "Ошибка нахождения алгоритма";

    /**
     * Class constructor.
     */
    private MD5() {

    }

    /**
     * Method encryption MD5
     *
     * @param s input string.
     * @return encryption string.
     */
    public static String encryptionMD5(String s) {
        try {
            var md5Algorithm = MessageDigest.getInstance(MD5_ALGORITHM);

            var digest = getMD5Hash(md5Algorithm, s);

            return getResultByteCode(digest);

        } catch (NoSuchAlgorithmException e) {
            MD_LOG.info(ALGORITHM_EXCEPTION_MESSAGE + e.getMessage());
            MD_LOG.info(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * Method calculation hash.
     *
     * @param md5 algorithm.
     * @param s   input string.
     * @return calculated md5 hash
     */
    private static byte[] getMD5Hash(MessageDigest md5, String s) {
        var sBytes = s.getBytes();

        md5.update(sBytes);

        return md5.digest();
    }

    /**
     * Method preparation of the final encrypted string.
     *
     * @param bytes md5 hash.
     * @return result encrypted string.
     */
    private static String getResultByteCode(byte[] bytes) {
        StringBuilder resultHash = new StringBuilder();
        for (var b : bytes) {
            resultHash.append(String.format(HEX_TWO_NUMBER_FORMAT, b & DIGIT_BYTE));
        }

        return new String(resultHash);
    }
}

