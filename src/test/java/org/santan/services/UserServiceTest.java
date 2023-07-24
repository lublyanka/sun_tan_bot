package org.santan.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.santan.entities.Level;
import org.santan.entities.Position;
import org.santan.entities.User;
import org.santan.repositories.UserRepository;

class UserServiceTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    LevelService levelService = Mockito.mock(LevelService.class);
    UserService userService;
    @BeforeEach
    void init(){
        this.userService = new UserService(userRepository, levelService);
    }

    @Test
    void getUserById() {
        Long telegramUserId = 1L;
        User user = new User();
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(telegramUserId)).thenReturn(optionalUser);
        Optional<User> result = userService.getUserById(telegramUserId);
        assertEquals(optionalUser, result);
      }

    @Test
    void saveUser() {
        User user = new User();
        userService.saveUser(user);
        verify(userRepository,times(1)).save(eq(user));
        verify(userRepository,times(1)).flush();
      }

    @Test
    void resetUserLevelToFirstOne() {
        User user = new User();
        Level level = new Level(1,1,1,1,1,1);
        when(levelService.getFirstLevel()).thenReturn(level);
        userService.resetUserLevelToFirstOne(user);
        verify(userRepository,times(1)).save(eq(user));
        verify(userRepository,times(1)).flush();
        assertEquals(Position.FRONT, user.getCurrentPosition());
        assertEquals(level, user.getCurrentLevel());
      }
}