package com.openclassrooms.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.mapper.UserMapperImpl;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserMapperImpl userMapperImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test_user");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setPassword("password");

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setEmail("test_user");
        expectedUser.setLastName("Doe");
        expectedUser.setFirstName("John");
        expectedUser.setPassword("password");

        // Mocking the mapping behavior
        when(userMapper.toEntity(userDto)).thenReturn(expectedUser);

        // When
        User result = userMapperImpl.toEntity(userDto);

        // Then
        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getEmail(), result.getEmail());
        assertEquals(expectedUser.getLastName(), result.getLastName());
        assertEquals(expectedUser.getFirstName(), result.getFirstName());
        assertEquals(expectedUser.getPassword(), result.getPassword());
    }

    @Test
    void testToDto() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test_user");
        user.setLastName("Doe");
        user.setFirstName("John");

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(1L);
        expectedUserDto.setEmail("test_user");
        expectedUserDto.setLastName("Doe");
        expectedUserDto.setFirstName("John");

        // Mocking the mapping behavior
        when(userMapper.toDto(user)).thenReturn(expectedUserDto);

        // When
        UserDto result = userMapperImpl.toDto(user);

        // Then
        assertEquals(expectedUserDto.getId(), result.getId());
        assertEquals(expectedUserDto.getEmail(), result.getEmail());
        assertEquals(expectedUserDto.getLastName(), result.getLastName());
        assertEquals(expectedUserDto.getFirstName(), result.getFirstName());
    }

    @Test
    void testToEntityList() {
        // Given
        List<UserDto> userDtoList = Arrays.asList(
                new UserDto(1L, "test1@example.com", "Last1", "First1", true, "password1", null, null),
                new UserDto(2L, "test2@example.com", "Last2", "First2", false, "password2", null, null)
        );

        List<User> expectedUserList = Arrays.asList(
                new User(1L, "test1@example.com", "Last1", "First1", "password1", true, null, null),
                new User(2L, "test2@example.com", "Last2", "First2", "password2", false, null, null)
        );

        // Mocking the mapping behavior for list
        when(userMapper.toEntity(userDtoList.get(0))).thenReturn(expectedUserList.get(0));
        when(userMapper.toEntity(userDtoList.get(1))).thenReturn(expectedUserList.get(1));

        // When
        List<User> resultList = userMapperImpl.toEntity(userDtoList);

        // Then
        assertEquals(expectedUserList.size(), resultList.size());
        for (int i = 0; i < expectedUserList.size(); i++) {
            assertEquals(expectedUserList.get(i).getId(), resultList.get(i).getId());
            assertEquals(expectedUserList.get(i).getEmail(), resultList.get(i).getEmail());
            assertEquals(expectedUserList.get(i).getLastName(), resultList.get(i).getLastName());
            assertEquals(expectedUserList.get(i).getFirstName(), resultList.get(i).getFirstName());
            assertEquals(expectedUserList.get(i).isAdmin(), resultList.get(i).isAdmin());
            assertEquals(expectedUserList.get(i).getPassword(), resultList.get(i).getPassword());
        }
    }

    @Test
    void testToDtoList() {
        // Given
        List<User> userList = Arrays.asList(
                new User(1L, "test1@example.com", "Last1", "First1", "password1", true, null, null),
                new User(2L, "test2@example.com", "Last2", "First2", "password2", false, null, null)
        );

        List<UserDto> expectedUserDtoList = Arrays.asList(
                new UserDto(1L, "test1@example.com", "Last1", "First1", true, "password1", null, null),
                new UserDto(2L, "test2@example.com", "Last2", "First2", false, "password2", null, null)
        );

        // Mocking the mapping behavior for list
        when(userMapper.toDto(userList.get(0))).thenReturn(expectedUserDtoList.get(0));
        when(userMapper.toDto(userList.get(1))).thenReturn(expectedUserDtoList.get(1));

        // When
        List<UserDto> resultList = userMapperImpl.toDto(userList);

        // Then
        assertEquals(expectedUserDtoList.size(), resultList.size());
        for (int i = 0; i < expectedUserDtoList.size(); i++) {
            assertEquals(expectedUserDtoList.get(i).getId(), resultList.get(i).getId());
            assertEquals(expectedUserDtoList.get(i).getEmail(), resultList.get(i).getEmail());
            assertEquals(expectedUserDtoList.get(i).getLastName(), resultList.get(i).getLastName());
            assertEquals(expectedUserDtoList.get(i).getFirstName(), resultList.get(i).getFirstName());
            assertEquals(expectedUserDtoList.get(i).isAdmin(), resultList.get(i).isAdmin());
        }
    }
}
