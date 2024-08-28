package com.gabrielmotta.userscore.domain.service;

import com.gabrielmotta.userscore.api.dto.CustomPageRequest;
import com.gabrielmotta.userscore.api.dto.UserFilter;
import com.gabrielmotta.userscore.api.dto.UserResponse;
import com.gabrielmotta.userscore.domain.User;
import com.gabrielmotta.userscore.domain.exception.UserScoreException;
import com.gabrielmotta.userscore.infra.integration.CepClient;
import com.gabrielmotta.userscore.infra.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.gabrielmotta.userscore.helpers.CepHelper.mockCepResponse;
import static com.gabrielmotta.userscore.helpers.UserHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private CepClient cepClient;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void findByEmail_shouldReturnUserOptional_whenUserFound() {
        var mockUser = mockAdminUser();
        when(repository.findByEmail("admin@email.com")).thenReturn(Optional.of(mockUser));

        assertEquals(Optional.of(mockUser), service.findByEmail("admin@email.com"));
    }

    @Test
    void findByEmail_shouldReturnEmptyOptional_whenUserNotFound() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThat(service.findByEmail("jordana@gmail.com")).isEmpty();
    }

    @Test
    void save_shouldSaveUser_whenEmailNotRegistered() {
        when(repository.findByEmail("jairo@test.com")).thenReturn(Optional.empty());
        when(cepClient.getCepData("86010020")).thenReturn(mockCepResponse());
        when(passwordEncoder.encode("belinha2014")).thenReturn("encriptedPa$$word");
        var request = mockUserRequest();

        assertThatCode(() -> service.save(request)).doesNotThrowAnyException();

        verify(repository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue())
            .extracting("zipCode", "state", "city", "neighborhood", "streetAddress", "password")
            .containsExactly("86015010", "PR", "Londrina", "Jardim Higienópolis", "Avenida Higienópolis", "encriptedPa$$word");
    }

    @Test
    void save_shouldThrowUserScoreException_whenEmailAlreadyRegistered() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(mockAdminUser()));
        var request = mockUserRequest();

        assertThatExceptionOfType(UserScoreException.class)
            .isThrownBy(() -> service.save(request))
            .withMessage("Email already registered!");

        verify(cepClient, never()).getCepData(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void findAll_shouldReturnPageOfUserResponse_whenCalled() {
        when(repository.findAll(any(Example.class), any(Pageable.class)))
            .thenReturn(userResponsePage());

        var page = service.findAll(UserFilter.builder().build(), new CustomPageRequest());

        assertThat(page).isInstanceOf(PagedModel.class);
        assertThat(page.getContent()).isEqualTo(List.of(mockUserResponse()));
    }

    @Test
    void findAll_shouldReturnEmptyPage_ifNoUsersFoundWithDefinedFilters() {
        when(repository.findAll(any(Example.class), any(Pageable.class)))
            .thenReturn(Page.empty());

        var page = service.findAll(UserFilter.builder().build(), new CustomPageRequest());

        assertThat(page.getContent()).isEqualTo(List.of());
    }

    @Test
    void getUserResponseById_shouldReturnUserResponse_ifUserFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockAdminUser()));

        assertThat(service.getUserResponseById(1L))
            .isInstanceOf(UserResponse.class)
            .isEqualTo(mockUserResponse());
    }

    @Test
    void getUserResponseById_shouldThrowEntityNotFoundException_ifUserNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
            .isThrownBy(() -> service.getUserResponseById(2L))
            .withMessage("User not found!");
    }

    @Test
    void update_shouldUpdateUser_whenEmailNotRegistered() {
        var existingUser = mockAdminUser();
        existingUser.setZipCode("86010020");
        when(repository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(repository.findByEmail("jairo@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("belinha2014")).thenReturn("encriptedPa$$word");
        var request = mockUserRequest();

        assertThatCode(() -> service.update(1L, request)).doesNotThrowAnyException();

        verify(repository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue())
            .extracting("zipCode", "state", "city", "neighborhood", "streetAddress", "password")
            .containsExactly("86010020", "SP", "São Paulo", "Vila Gertrudes", "Avenida das Nações Unidas", "encriptedPa$$word");

        verify(cepClient, never()).getCepData(anyString());
    }

    @Test
    void update_shouldUpdateUserAndCallCepClient_whenNewZipCode() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockAdminUser()));
        when(repository.findByEmail("jairo@test.com")).thenReturn(Optional.empty());
        when(cepClient.getCepData("86010020")).thenReturn(mockCepResponse());
        when(passwordEncoder.encode("belinha2014")).thenReturn("encriptedPa$$word");
        var request = mockUserRequest();

        assertThatCode(() -> service.update(1L, request)).doesNotThrowAnyException();

        verify(repository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue())
            .extracting("zipCode", "state", "city", "neighborhood", "streetAddress", "password")
            .containsExactly("86015010", "PR", "Londrina", "Jardim Higienópolis", "Avenida Higienópolis", "encriptedPa$$word");
    }

    @Test
    void update_shouldThrowUserScoreException_whenEmailAlreadyRegistered() {
        when(repository.findByEmail("jairo@test.com")).thenReturn(Optional.of(mockAdminUser()));
        when(repository.findById(1L)).thenReturn(Optional.of(mockAdminUser()));
        var request = mockUserRequest();

        assertThatExceptionOfType(UserScoreException.class)
            .isThrownBy(() -> service.update(1L, request))
            .withMessage("Email already registered!");

        verify(repository, never()).save(any(User.class));
        verify(cepClient, never()).getCepData(anyString());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void update_shouldThrowEntityNotFoundException_ifUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        var request = mockUserRequest();

        assertThatExceptionOfType(EntityNotFoundException.class)
            .isThrownBy(() -> service.update(1L, request))
            .withMessage("User not found!");

        verify(repository, never()).save(any(User.class));
        verify(repository, never()).findByEmail(anyString());
        verify(cepClient, never()).getCepData(anyString());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void softDelete_shouldDeactivateUser_whenUserFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockAdminUser()));

        assertThatCode(() -> service.softDelete(1L)).doesNotThrowAnyException();

        verify(repository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getDeactivatedAt())
            .isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.MINUTES));
    }

    @Test
    void softDelete_shouldThrowEntityNotFoundException_ifUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
            .isThrownBy(() -> service.softDelete(1L))
            .withMessage("User not found!");

        verify(repository, never()).save(any(User.class));
    }

    @Test
    void activateUser_shouldActivateUser_whenUserFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockAdminUser()));

        assertThatCode(() -> service.activateUser(1L)).doesNotThrowAnyException();

        verify(repository).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getDeactivatedAt()).isNull();
    }

    @Test
    void activateUser_shouldThrowEntityNotFoundException_ifUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
            .isThrownBy(() -> service.activateUser(1L))
            .withMessage("User not found!");

        verify(repository, never()).save(any(User.class));
    }

    @Test
    void getUserScoreDescription_shouldReturnUserScore_ifUserFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockAdminUser()));

        assertThat(service.getUserScoreDescription(1L)).isEqualTo("920 - Recomendável");
    }

    @Test
    void getUserScoreDescription_shouldThrowEntityNotFoundException_ifUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
            .isThrownBy(() -> service.getUserScoreDescription(1L))
            .withMessage("User not found!");
    }
}
