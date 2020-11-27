package hu.bme.crysys.homework.pangolin.webshop.mapper;

import hu.bme.crysys.homework.pangolin.webshop.dto.UserResult;
import hu.bme.crysys.homework.pangolin.webshop.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public final class UserMapper {

    private UserMapper() { }

    public static List<UserResult> mapUsersToUserResults(final List<User> users) {
        return users.stream().map(UserMapper::mapUserToUserResult).collect(toList());
    }

    public static UserResult mapUserToUserResult(final User user) {
        final UserResult userResult = new UserResult();
        userResult.setUuid(user.getUuid());
        userResult.setUsername(user.getUsername());
        userResult.setEmail(user.getEmail());
        userResult.setDateOfBirth(user.getDateOfBirth());
        userResult.setRegistrationDate(user.getRegistrationDate());
        return userResult;
    }

}
