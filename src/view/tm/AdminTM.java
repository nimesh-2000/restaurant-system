package view.tm;

import javafx.scene.control.Button;

public class AdminTM {
    private String adminId;
    private String adminName;
    private String password;
    private Button userDelete;
    private Button userUpdate;

    public AdminTM() {
    }

    public AdminTM(String adminId, String adminName, String password, Button userDelete, Button userUpdate) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.password = password;
        this.userDelete = userDelete;
        this.userUpdate = userUpdate;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getUserDelete() {
        return userDelete;
    }

    public void setUserDelete(Button userDelete) {
        this.userDelete = userDelete;
    }

    public Button getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Button userUpdate) {
        this.userUpdate = userUpdate;
    }

    @Override
    public String toString() {
        return "AdminTM{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", password='" + password + '\'' +
                ", userDelete=" + userDelete +
                ", userUpdate=" + userUpdate +
                '}';
    }
}
