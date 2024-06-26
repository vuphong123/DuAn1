package udpm.fpt.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import udpm.fpt.model.User;
import udpm.fpt.model.UserDetails;
import static udpm.fpt.Application.getBean;

import udpm.fpt.repository.IUser;
import udpm.fpt.repository.IUserDetails;

/**
 * @author Binh Quoc
 */
@NoArgsConstructor
public class UserService {

    private final IUserDetails iUserDetails = getBean(IUserDetails.class);
    private final IUser iUser = getBean(IUser.class);

    public List<UserDetails> getList() {
        return iUserDetails.findAll().stream()
                .filter(user -> user.getStatus().equalsIgnoreCase("active"))
                .collect(Collectors.toList());
    }

    public boolean addNewUser(UserDetails userDetails) {
        return this.iUserDetails.save(userDetails) != null;
    }

    public boolean uniquedUsername(String username) {
        boolean isContain = false;
        List<UserDetails> list = this.getList();
        for (UserDetails obj : list) {
            if (obj.getUser().getUsername().equals(username)) {
                isContain = true;
            }
        }
        return isContain;
    }

    public boolean deleteNewUser(UserDetails userDetails) {
        return this.iUserDetails.save(userDetails) != null;
    }

    public boolean updateUser(UserDetails userDetails) {
        return this.iUserDetails.save(userDetails) != null;
    }

    public String getAvatar(String username) {
        try {
            return this.iUserDetails.findByUser_Username(username).getPhoto();
        } catch (Exception exception) {
            return null;
        }
    }

    public List<UserDetails> filterEmployee(String userQuery) {
        return iUserDetails.findByQuery(userQuery);
    }
    public UserDetails getUserByGmail(String email){
        return iUserDetails.findByEmail(email);
    }
    public Boolean changePassword(User newPassword){
        return iUser.save(newPassword) != null;
    }
}
