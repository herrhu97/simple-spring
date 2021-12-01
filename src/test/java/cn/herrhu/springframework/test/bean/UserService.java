package cn.herrhu.springframework.test.bean;

public class UserService {
    private String name;

    public void queryUserInfo() {
        System.out.println("query user info: " + name);
    }

    public UserService(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                '}';
    }
}
