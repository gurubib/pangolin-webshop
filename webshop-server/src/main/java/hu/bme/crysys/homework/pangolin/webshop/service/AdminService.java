package hu.bme.crysys.homework.pangolin.webshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import hu.bme.crysys.homework.pangolin.webshop.dto.UpdateUserRequest;
import hu.bme.crysys.homework.pangolin.webshop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public boolean updateUser(String userUuid, UpdateUserRequest request) {
        // TODO - write updateUser
        // admin should not be able to modified
        return true;
    }

    public boolean removeUser(String userUuid) {
        // TODO - write removeUser
        // admin should not be able to deleted
        return true;
    }

}
