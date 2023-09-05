package org.example;

import jdk.dynalink.Operation;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        ids.forEach(id -> {
            Optional<CustomUser> user = userRepository.findById(id);
            user.ifPresent(u -> {
                if ( ! AppConfig.ADMIN_LOGIN.equals(u.getLogin())) {
                    userRepository.deleteById(u.getId());
                }
            });
        });
    }

    @Transactional(readOnly = true)
    public CustomUser findID (Long id ){
        return userRepository.findById(id).get();
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String email,
                           String phone,
                           String address) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, role, email, phone, address);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void updateUser(String login, String email, String phone) {
        CustomUser user = userRepository.findByLogin(login);

        if (user == null)
            return;

        user.setEmail(email);
        user.setPhone(phone);

        userRepository.save(user);
    }

    @Transactional
    public void edit (String login, String email,String phone,String address){
        CustomUser user = userRepository.findByLogin(login);

        if (user == null)
            return;

        user.setAddress(address);
        user.setPhone(phone);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Transactional
    public void newAddress(String login,String address){
        CustomUser user = userRepository.findByLogin(login);
        if (user ==null)
            return;
        user.setAddress(address);
        userRepository.save(user);
    }
}