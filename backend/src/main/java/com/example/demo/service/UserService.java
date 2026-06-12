package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.entity.UserItem;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<String, UserAccount> userMap = new ConcurrentHashMap<>();
    private final List<UserItem> listData = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        UserAccount admin = new UserAccount();
        admin.setUsername("admin");
        admin.setPassword("123456");
        admin.setEmail("admin@example.com");
        admin.setBirthday("2000-01-01");
        userMap.put(admin.getUsername(), admin);

        listData.add(buildRow("王小虎", "上海", "普陀区", "上海市普陀区金沙江路 1518 弄", "200333"));
        listData.add(buildRow("李小虎", "上海", "普陀区", "上海市普陀区金沙江路 1517 弄", "200333"));
        listData.add(buildRow("张小虎", "上海", "普陀区", "上海市普陀区金沙江路 1519 弄", "200333"));
        listData.add(buildRow("赵小虎", "上海", "普陀区", "上海市普陀区金沙江路 1516 弄", "200333"));
        listData.add(buildRow("孙小虎", "上海", "普陀区", "上海市普陀区金沙江路 1515 弄", "200333"));
    }

    public List<UserItem> getList() {
        return new ArrayList<>(listData);
    }

    public UserAccount findByUsername(String username) {
        return userMap.get(username);
    }

    public boolean register(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return false;
        }
        if (userMap.containsKey(request.getUsername())) {
            return false;
        }

        UserAccount account = new UserAccount();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());
        account.setEmail(request.getEmail());
        account.setBirthday(request.getBirthday());
        account.setAvatar(request.getAvatar());
        userMap.put(account.getUsername(), account);
        return true;
    }

    public boolean updatePassword(String username, String oldPwd, String newPwd) {
        UserAccount user = userMap.get(username);
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(oldPwd)) {
            return false;
        }
        user.setPassword(newPwd);
        return true;
    }

    private UserItem buildRow(String name, String province, String city, String address, String zip) {
        UserItem item = new UserItem();
        item.setId(idGenerator.getAndIncrement());
        item.setDate("2024-04-12 00:00:00");
        item.setName(name);
        item.setProvince(province);
        item.setCity(city);
        item.setAddress(address);
        item.setZip(zip);
        return item;
    }
}
