package org.pfg.priceestimation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String login() {
        return "login";  // Return login.html
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> loginUser(@RequestBody Map<String, String> loginData) {
        String userLogin = loginData.get("userLogin");
        String userPassword = loginData.get("userPassword");

        Map<String, Object> response = new HashMap<>();
        try {
            User user = jdbcTemplate.queryForObject("EXEC Users_Login_Validation ?, ?",
                    new Object[]{userLogin, userPassword}, new UserRowMapper());

            if (user != null) {
                response.put("success", true);
                response.put("user", user);
            } else {
                response.put("success", false);
            }
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }

    public static class User {
        private int userIdNumber;
        private String userName;
        private int rolId;
        private String rolName;
        private String userStatus;

        // Getters and Setters
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserIdNumber(rs.getInt("UserIdNumber"));
            user.setUserName(rs.getString("UserName"));
            user.setRolId(rs.getInt("RolId"));
            user.setRolName(rs.getString("RolName"));
            user.setUserStatus(rs.getString("UserStatus"));
            return user;
        }
    }
}
