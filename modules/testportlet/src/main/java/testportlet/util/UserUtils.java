package testportlet.util;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.ArrayList;
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

}
