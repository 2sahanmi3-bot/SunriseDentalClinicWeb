package com.sunrisedental.service;

import com.sunrisedental.dao.UserDAO;
import com.sunrisedental.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StaffManagementService {

    private UserDAO userDAO;

    public StaffManagementService(
            UserDAO userDAO) {

        this.userDAO =
                userDAO;
    }

    public boolean createStaffUser(
            String username,
            String password,
            String role)
            throws SQLException {

        Optional<User> existingUser =
                userDAO.findByUsername(
                        username
                );

        if (existingUser.isPresent()) {

            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }

        // Only the two supported staff roles can be saved.
        if (!"ADMIN".equals(role)
                && !"STAFF".equals(role)) {

            throw new IllegalArgumentException(
                    "Invalid user role"
            );
        }

        String hashedPassword =
                BCrypt.hashpw(
                        password,
                        BCrypt.gensalt()
                );

        User user =
                new User(
                        0,
                        username,
                        hashedPassword,
                        role
                );

        return userDAO.saveUser(
                user
        );
    }

    public List<User> getAllUsers()
            throws SQLException {

        return userDAO.findAllUsers();
    }

    public boolean changeUserRole(
            int userId,
            String role)
            throws SQLException {

        if (!"ADMIN".equals(role)
                && !"STAFF".equals(role)) {

            throw new IllegalArgumentException(
                    "Invalid user role"
            );
        }

        List<User> users =
                userDAO.findAllUsers();

        User targetUser = null;

        for (User user : users) {

            if (user.getUserId() == userId) {
                targetUser = user;
                break;
            }
        }

        if (targetUser == null) {

            throw new IllegalArgumentException(
                    "User account not found"
            );
        }

        if (targetUser.isActive()
                && "ADMIN".equals(targetUser.getRole())
                && "STAFF".equals(role)) {

            long activeAdmins =
                    users.stream()
                            .filter(User::isActive)
                            .filter(user ->
                                    "ADMIN".equals(
                                            user.getRole()
                                    ))
                            .count();

            if (activeAdmins <= 1) {

                throw new IllegalArgumentException(
                        "At least one active admin account is required"
                );
            }
        }

        return userDAO.updateUserRole(
                userId,
                role
        );
    }

    public boolean changeUserStatus(
            int userId,
            boolean active,
            String currentUsername)
            throws SQLException {

        List<User> users =
                userDAO.findAllUsers();

        User targetUser = null;

        for (User user : users) {

            if (user.getUserId() == userId) {
                targetUser = user;
                break;
            }
        }

        if (targetUser == null) {

            throw new IllegalArgumentException(
                    "User account not found"
            );
        }

        // Do not allow an admin to disable the account they are using.
        if (!active
                && targetUser.getUsername()
                .equals(currentUsername)) {

            throw new IllegalArgumentException(
                    "You cannot deactivate your own account"
            );
        }

        if (!active
                && "ADMIN".equals(targetUser.getRole())) {

            long activeAdmins =
                    users.stream()
                            .filter(User::isActive)
                            .filter(user ->
                                    "ADMIN".equals(
                                            user.getRole()
                                    ))
                            .count();

            if (activeAdmins <= 1) {

                throw new IllegalArgumentException(
                        "At least one active admin account is required"
                );
            }
        }

        return userDAO.updateUserStatus(
                userId,
                active
        );
    }
}
