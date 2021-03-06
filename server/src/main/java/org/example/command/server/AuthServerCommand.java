package org.example.command.server;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.DBController;
import org.example.enums.AuthState;
import org.example.model.Message;
import org.example.model.User;
import org.example.util.DatabaseUtil;
import org.example.util.MusicBandValidator;

import java.sql.SQLException;

public class AuthServerCommand extends AbstractServerCommand {

    @Override
    public String command() {
        return "auth";
    }

    @Override
    public String description() {
        return "Авторизация на сервере";
    }

    /**
     * Использует {@link MusicBandValidator} для наполнения объекта {@link MusicBand}
     * и добавляет его в коллекцию
     *
     * @param args аргументы
     * @return
     */
    @Override
    public Message execute(Message args) {
        DBController dbController = serviceLocator.getDbController();
        User user = args.getUser();
        try {
            dbController.setConnection(DatabaseUtil.getConnection());
            User foundUser = dbController.findByName(user.getLogin());
            if (foundUser == null) {
                return new Message("Пользователя не существует");
            }
            if (!foundUser.getPassword().equals(DigestUtils.md2Hex(user.getPassword()))) {
                return new Message("Неверный пароль");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Message("Ошибка на сервере");
        } finally {
            DatabaseUtil.closeConnection();
        }
        return new Message(AuthState.AUTH_SUCCESS.name());
    }
}
