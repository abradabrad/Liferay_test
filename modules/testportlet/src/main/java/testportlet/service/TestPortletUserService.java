package testportlet.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestPortletUserService {

    public static List<Role> getCurrentUserRoles(RenderRequest request) {
        ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        try {
            return RoleServiceUtil.getUserRoles(td.getUserId());
        } catch (PortalException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<User> getUsersWithRoles(RenderRequest request) {

        return UserLocalServiceUtil.getUsers(0, UserLocalServiceUtil.getUsersCount())
                .stream()
                .filter(it -> it.getRoles()
                        .stream()
                        .allMatch(role -> getCurrentUserRoles(request)
                                .stream()
                                .anyMatch(role::equals)))
                .filter(user -> !user.getDefaultUser())
                .collect(Collectors.toList());
    }

    public static String getOrganizations(User user) {
        try {
            return user.getOrganizations()
                    .stream()
                    .map(Organization::getName)
                    .collect(Collectors
                            .joining("<br>")
                    );
        } catch (PortalException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPhones(User user) {
        return user.getPhones()
                .stream()
                .map(Phone::getNumber)
                .collect(Collectors
                        .joining("<br>")
                );
    }

    public static List<User> getUsers(RenderRequest request, int start, int end) {

        return ListUtil.subList(TestPortletUserService.getUsersWithRoles(request), start, end);
    }

    public static Integer getUsersCount(RenderRequest request) {
        return getUsersWithRoles(request).size();
    }
}
