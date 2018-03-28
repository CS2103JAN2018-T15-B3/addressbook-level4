package seedu.address.model.util;

import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserDatabase;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.DuplicateUserException;

/**
 * Contains utility methods for populating {@code UserDatabase} with one default user.
 */
public class SampleUsersUtil {

    public static ReadOnlyUserDatabase getSampleUserDatabase() {
        try {
            UserDatabase sampleUd = new UserDatabase();
            sampleUd.addUser(new User(new Username("user"), new Password("pass"), "data/addressbook-user.xml"));
            sampleUd.addUser(new User(new Username("hello"), new Password("pass"), "data/addressbook-hello.xml"));
            sampleUd.addUser(new User(new Username("slap"), new Password("pass"), "data/addressbook-slap.xml"));
            return sampleUd;
        } catch (DuplicateUserException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

}
