package org.santan.services;

import org.santan.entities.Position;
import org.santan.entities.User;
import org.santan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LevelService levelService;

    public Optional<User> getUserById(Long telegramUserId) {
        return userRepository.findById(telegramUserId);
    }

    public void saveUser(User userToSave) {
        userRepository.save(userToSave);
        userRepository.flush();
    }

    public void resetUserLevelToFirstOne(User user) {
        user.setCurrentLevel(levelService.getFirstLevel());
        user.setCurrentPosition(Position.FRONT);
        saveUser(user);
    }
}
