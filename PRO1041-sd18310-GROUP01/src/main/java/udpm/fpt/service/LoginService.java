package udpm.fpt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.model.User;
import udpm.fpt.repository.IUser;

public class LoginService {

    private final IUser user = getBean(IUser.class);
    private final List<User> cachedUsers;

    public LoginService() {
        this.cachedUsers = new ArrayList<>();
    }

    public void loadAsync() {
        CompletableFuture.runAsync(() -> {
            List<User> users = user.findAll();
            cachedUsers.clear();
            cachedUsers.addAll(users);
        }, Executors.newCachedThreadPool());
    }

    public User login(String username, String password) {
        try {
            for (User loginUser : this.cachedUsers) {
                if (username.equals(loginUser.getUsername()) && new BcryptHash().checkPassword(password, loginUser.getPassword())) {
                    return loginUser;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
