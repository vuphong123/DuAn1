import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udpm.fpt.Application;
import udpm.fpt.form.UserForm;
import udpm.fpt.form.UserManagementForm;

public class NhanVienTest {
    Application application = new Application();
    UserManagementForm userForm;
    @BeforeEach
    public void setup(){
        userForm = new UserManagementForm(null, null);
    }
    @Test
    public void testAddNhanVien(){
        userForm.addNewUser();
    }
}
