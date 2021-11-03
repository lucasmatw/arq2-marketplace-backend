package ar.edu.mercadogratis.app.service;

import ar.edu.mercadogratis.app.dao.UserDaoImpl;
import ar.edu.mercadogratis.app.model.Email;
import ar.edu.mercadogratis.app.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service("userService")
public class UserService {

    private final UserDaoImpl userDao;

    @Transactional
    public User getUser(Long id) {
        return userDao.get(id);
    }

    @Transactional
    public User getUserForMail(String mail) {
        return userDao.getUser(mail);
    }

    @Transactional
    public Long addUser(User user) throws EmailException {
        Email email = new Email();
        if (Email.isValidEmailAddress(user.getEmail())) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            String pwd = RandomStringUtils.random(15, characters);
            user.setPassword(pwd);
            Long idUser = userDao.save(user);
            email.send(user.getEmail(), "Bienvenido a MercadoGratis", "Tu password es: " + user.getPassword());
            return idUser;
        }
        return null;

    }

    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Transactional
    public void forgetPassword(String mail) throws EmailException {

        Email email = new Email();
        if (Email.isValidEmailAddress(mail)) {
            User user = this.getUserForMail(mail);
            if (user != null) {
                email.send(user.getEmail(), "Bienvenido a MercadoGratis", "Tu password es: " + user.getPassword());
            }
        }
    }

    @Transactional
    public Long changePassword(JSONObject userWithNewPassword) throws RuntimeException, JSONException {
        Long idUser = null;

        User user = this.getUserForMail(userWithNewPassword.getString("email"));

        if (user != null && user.getPassword().equals(userWithNewPassword.getString("password"))) {
            idUser = user.getId();

            user.setPassword(userWithNewPassword.getString("newpassword"));
            userDao.save(user);

        } else {
            throw new RuntimeException("Error User o Password Ingresada");
        }
        return idUser;
    }
}
