package com.diploma.Backend.service.user.impl;

import com.diploma.Backend.env.ROLES;
import com.diploma.Backend.model.Report;
import com.diploma.Backend.model.Role;
import com.diploma.Backend.model.User;
import com.diploma.Backend.repo.UserRepo;
import com.diploma.Backend.rest.exception.impl.*;
import com.diploma.Backend.service.user.UserValidationService;
import com.diploma.Backend.utils.SecurityUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service

@Slf4j
@Transactional
public class UserServiceImpl implements com.diploma.Backend.service.user.UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final UserValidationService userValidationService;

    public UserServiceImpl(UserRepo userRepo, @Lazy PasswordEncoder encoder, UserValidationService userValidationService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.userValidationService = userValidationService;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(long id) {
        return userRepo.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(@NonNull String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(@NonNull String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = userRepo.findAll();
        log.info("IN findAll found: {}", users);
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findPublicUsers() {
        List<User> users = userRepo.findAllByRolesNotContains(ROLES.ADMIN);
        log.info("IN findPublicUsers found: {}", users);
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findFollowersByChairmanId(long chairmanId) {
        if (!existsById(chairmanId)) throw new UserNotFoundExceptionImpl(chairmanId);
        return userRepo.findAllByChairmanId(chairmanId);
    }

    @Override
    @Transactional(readOnly = true)
    public User findChairmanByFollowerId(long followerID) throws UserNotFoundExceptionImpl {
        Optional<User> follower = findById(followerID);
        if (follower.isEmpty()) throw new UserNotFoundExceptionImpl(followerID);
        return follower.get().getChairman();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findChairmans() {
        return userRepo.findAllByRolesContains(ROLES.CHAIRMAN);
    }


    @Override
    @Transactional
    public User createUser(@NonNull User user) {
        if (existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyTaken(user.getUsername());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundExceptionImpl(id);
        }
        if (SecurityUtils.isAdmin(user)) {
            throw new ValidationExceptionImpl("???????????? ???????????? ??????????????");
        }

        userRepo.delete(user);
        log.info("In delete - user wos deleted by user {}", user);
    }

    @Override
    @Transactional
    public User updateUserInfo(long sourceUserId, @NonNull User changedUser) throws UserNotFoundExceptionImpl, ValidationExceptionImpl, ForbiddenExceptionImpl {
        User user = this.findById(sourceUserId).orElse(null);

        if (user == null) {
            throw new UserNotFoundExceptionImpl(sourceUserId);
        }
        if (!SecurityUtils.isCurrentUserEqualsUserOrAdmin(user)) {
            throw new ForbiddenExceptionImpl();
        }
        if (changedUser.getUsername() != null && !changedUser.getUsername().isBlank()) {
            if (!userValidationService.validateUserUsername(changedUser.getUsername())) {
                throw new ValidationExceptionImpl("username ???? ???????????? ??????????????????");
            }
            if (!user.getUsername().equals(changedUser.getUsername()) && existsByUsername(changedUser.getUsername()))
                throw new UsernameAlreadyTaken(changedUser.getUsername());
            user.setUsername(changedUser.getUsername());
        }
        if (changedUser.getFirstName() != null && !changedUser.getFirstName().isBlank()) {
            if (!userValidationService.validateUserFirstName(changedUser.getFirstName())) {
                throw new ValidationExceptionImpl("?????? ???? ???????????? ??????????????????");
            }
            user.setFirstName(changedUser.getFirstName());
        }
        if (changedUser.getLastName() != null && !changedUser.getLastName().isBlank()) {
            if (!userValidationService.validateUserLastName(changedUser.getLastName())) {
                throw new ValidationExceptionImpl("?????????????? ???? ???????????? ??????????????????");
            }
            user.setLastName(changedUser.getLastName());
        }
        if (changedUser.getMiddleName() != null && !changedUser.getMiddleName().isBlank()) {
            if (!userValidationService.validateUserPatronymic(changedUser.getMiddleName())) {
                throw new ValidationExceptionImpl("???????????????? ???? ???????????? ??????????????????");
            }
            user.setMiddleName(changedUser.getMiddleName());
        }
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updatePassword(long userId, String password) {
        User user = findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundExceptionImpl(userId);
        }
        if (!SecurityUtils.isCurrentUserEqualsUserOrAdmin(user)) {
            throw new ForbiddenExceptionImpl();
        }
        if (!userValidationService.validateUserPassword(password)) {
            throw new ValidationExceptionImpl("???????????? ???? ???????????? ??????????????????");
        }

        user.setPassword(encoder.encode(password));
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updateRoles(long id, @NonNull Set<Role> roles) {
        User user = findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundExceptionImpl(id);
        }
        if (roles.contains(ROLES.ADMIN)) {
            throw new ValidationExceptionImpl("???????????? ?????????????????? ????????????");
        }

        if (user.getRoles().contains(ROLES.ADMIN)) {
            throw new ValidationExceptionImpl("???????????? ???????????????? ???????? ????????????");
        }
        user.setRoles(roles.stream().filter(Objects::nonNull).collect(Collectors.toSet()));
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updateChairman(long chairmanId, long followerId) {
        User follower = findById(followerId).orElse(null);
        if (follower == null) {
            throw new UserNotFoundExceptionImpl(followerId);
        }
        User chairman = null;
        if (chairmanId != 0) {
            chairman = findById(chairmanId).orElse(null);
            if (chairman == null) {
                throw new UserNotFoundExceptionImpl(chairmanId);
            }
            if (!SecurityUtils.isChairman(chairman)) {
                throw new ValidationExceptionImpl("?? ???????????????????????? ?????? ???????? ????????????????????????");
            }

        }


        follower.setChairman(chairman);

        log.info("IN setChairman follower: {}, chairman: {}", follower, chairman);
        return userRepo.save(follower);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserInChairmanGroup(long userId, long chairmanId) {
        User user = findById(userId).orElse(null);
        return findFollowersByChairmanId(chairmanId).contains(user);
    }
}
