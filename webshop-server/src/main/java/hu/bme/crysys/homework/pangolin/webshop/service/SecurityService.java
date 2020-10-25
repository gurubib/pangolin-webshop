package hu.bme.crysys.homework.pangolin.webshop.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
