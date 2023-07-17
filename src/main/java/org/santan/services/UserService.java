package org.santan.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.santan.entities.Position;
import org.santan.entities.User;
import org.santan.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LevelService levelService;

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
