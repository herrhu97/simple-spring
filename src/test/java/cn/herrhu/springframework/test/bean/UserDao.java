package cn.herrhu.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "herrhu1");
        hashMap.put("10002", "herrhu2");
        hashMap.put("10003", "herrhu3");

    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
