package by.kolp.myappservice.service;


import by.kolp.myappcore.model.entity.User;


public interface RegistrationService {

    void register(User user, boolean isAdmin);

}
