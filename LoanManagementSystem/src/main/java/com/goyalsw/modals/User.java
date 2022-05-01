package main.java.com.goyalsw.modals;

import main.java.com.goyalsw.modals.account.Account;
import main.java.com.goyalsw.modals.enums.UserStatus;

public class User {
    private static Integer cnt = 1;
    private String id;
    private String name;
    private Address address;
    private String email;
    private String phone;
    private UserStatus userStatus;
    private Account account;

    public User(String name) {
        this.id = cnt.toString();
        cnt++;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
