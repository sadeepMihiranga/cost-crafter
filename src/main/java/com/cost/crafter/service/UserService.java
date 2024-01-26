package com.cost.crafter.service;

import com.cost.crafter.dal.UserRepository;
import com.cost.crafter.dto.User;
import com.cost.crafter.security.BCrypt;
import com.cost.crafter.util.CommonValidatorUtil;
import com.cost.crafter.util.DateTimeUtil;
import com.mysql.cj.util.StringUtils;

import static com.cost.crafter.util.FontColors.ANSI_RED;
import static com.cost.crafter.util.FontColors.ANSI_RESET;

public class UserService {

    private UserExpensesCategoryService userExpensesCategoryService = null;

    private UserRepository userRepository = null;

    public boolean registerUser (User user) throws Exception {

        System.out.println("\nRegistering...");

        // input validations
        if (!DateTimeUtil.isValid(user.getDateOfBirth(), "yyyy-MM-dd")) {
            System.out.println(ANSI_RED + "\nDate of birth should be in yyyy-MM-dd format" + ANSI_RESET);
            return false;
        }

        if (!CommonValidatorUtil.isEmailValid(user.getEmail())) {
            System.out.println(ANSI_RED + "\nEmail is invalid" + ANSI_RESET);
            return false;
        }

        if (!CommonValidatorUtil.isAlpha(user.getFirstName())) {
            System.out.println(ANSI_RED + "\nFirst name can only contains letters" + ANSI_RESET);
            return false;
        }

        if (!StringUtils.isNullOrEmpty(user.getLastName()) && !CommonValidatorUtil.isAlpha(user.getLastName())) {
            System.out.println(ANSI_RED + "\nLast name can only contains letters" + ANSI_RESET);
            return false;
        }

        final String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        UserRepository userRepository = null;
        try {
            // save user info
            userRepository = new UserRepository();
            int createdId = userRepository.insertUser(user);

            // transfer and assign default expenses categories to new user
            if (createdId > 1) {
                userExpensesCategoryService = new UserExpensesCategoryService();
                userExpensesCategoryService.syncDefaultCategories(createdId);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while processing user registration");
        } finally {
            userExpensesCategoryService = null;
        }
    }

    public User login(String username, String password) throws Exception {
        try {
            // fetch user from database
            userRepository = new UserRepository();
            User user = userRepository.login(username);
            if (user == null) {
                return null;
            }
            if (!BCrypt.checkPassword(password, user.getPassword())) {
                return null;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while processing login");
        } finally {
            userRepository = null;
        }
    }
}
