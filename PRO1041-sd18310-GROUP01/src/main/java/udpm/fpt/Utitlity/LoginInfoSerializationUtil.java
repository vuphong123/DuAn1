package udpm.fpt.Utitlity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author NONG HOANG VU
 */
 public class LoginInfoSerializationUtil {

        public void saveLoginInfoToFile(LoginInfo loginInfo) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.ser"))) {
                oos.writeObject(loginInfo);
            } catch (IOException e) {
            }
        }

        public LoginInfo readLoginInfoFromFile() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.ser"))) {
                return (LoginInfo) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        }
    }