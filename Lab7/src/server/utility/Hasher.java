package server.utility;

import general.utility.Printer;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes password.
 */
public class Hasher {
    /**
     * Hashes password;.
     *
     * @param password Password itself.
     * @return Hashed password.
     */
    public static String hashPassword(String password) {
        String newPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes("UTF-8"), 0, password.length());
            newPassword = DatatypeConverter.printHexBinary(md.digest());
        } catch (NoSuchAlgorithmException exception) {
            Printer.printerror("Не найден алгоритм хэширования пароля!");
            throw new IllegalStateException(exception);
        } catch (UnsupportedEncodingException exception) {
            Printer.printerror("Неподдерживаемая инкодировка!");
        }
        return newPassword;
    }
}