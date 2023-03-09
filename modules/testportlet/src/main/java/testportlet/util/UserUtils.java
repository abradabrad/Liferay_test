package testportlet.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtils {

    public static List<User> getUsers(List<Role> roles) {

        return UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount())
                .stream()
                .filter(it -> it.getRoles()
                        .stream()
                        .allMatch(role -> roles
                                .stream()
                                .anyMatch(role::equals)))
                .collect(Collectors.toList());
    }

    public static String getOrganizations(User user) {
        try {
            return user.getOrganizations().stream().map(Organization::getName).collect(Collectors.joining("<br>"));
        } catch (PortalException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPhones(User user) {
        return user.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining("<br>"));
    }

    //
}
