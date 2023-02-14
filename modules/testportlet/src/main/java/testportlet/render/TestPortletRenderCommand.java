package testportlet.render;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import testportlet.constants.TestPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import testportlet.util.DateTimeUtils;

import javax.portlet.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name="+ TestPortletKeys.TEST,
                "mvc.command.name=/render"
        },
        service = MVCRenderCommand.class
)
public class TestPortletRenderCommand implements MVCRenderCommand
{

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse) {
        System.out.println("processAction" + renderRequest);
        getUsersList(renderRequest);

        return "/view_user.jsp";
    }

    private void getUsersList(RenderRequest actionRequest) {
        System.out.println(userLocalService.getUsersCount());
        //actionRequest.setAttribute("userLocalService", getUserLocalService());
        StringBuilder resultString = new StringBuilder();
        //System.out.println(userLocalService.getUsers(0,userLocalService.getUsersCount()));

        userLocalService.getUsers(0,userLocalService.getUsersCount())
                .stream()
                //.peek(System.out::println)
                .map(this::convertToResultString)
                //.peek(System.out::println)
                .forEach(resultString::append);

        actionRequest.setAttribute("LIST_USERS", resultString);
        //System.out.println(actionRequest.getAttribute("LIST_USERS"));
        System.out.println("resultString getUsersList: " + resultString);
    }
/*
    private UserTest convertToUserTest(User user) {
        UserTest userTest = userTestLocalService.addUserTest(userTestLocalService.createUserTest(user.getUserId()));
        userTest.setUserLastName(user.getLastName());
        userTest.setUserFirstName(user.getFirstName());
        userTest.setUserMiddleName(user.getMiddleName());
        userTest.setDisplayEmailAddress(user.getDisplayEmailAddress());
        userTest.setJobTitle(user.getJobTitle());
        try {
            userTest.setBirthday(user.getBirthday());
        } catch (PortalException e) {
            e.printStackTrace();
        }
        try {
            userTest.setOrganisations(user.getOrganizations().toString());
        } catch (PortalException e) {
            e.printStackTrace();
        }
        userTest.setPhoneNumber(user.getPhones().toString());
        return userTest;

    }*/

    private String convertToResultString(User user) {
        Date birthday = null;
        try {
            birthday = user.getBirthday();
        } catch (PortalException e) {
            e.printStackTrace();
        }
        //System.out.println("birthday " + birthday);

        List<Organization> orgs = new ArrayList<>();
        try {
           orgs = user.getOrganizations();
        } catch (PortalException e) {
            e.printStackTrace();
        }
        //System.out.println("orgs " + orgs);

            return "".concat(
                    String.valueOf(user.getUserId())
            ).concat(
                    user.getLastName().isEmpty() ? "" : ", " + user.getLastName()
            ).concat(
                    user.getFirstName().isEmpty() ? "" : " " + user.getFirstName()
            ).concat(
                    user.getMiddleName().isEmpty() ? "" : " " + user.getMiddleName()
            ).concat(
                    user.getDisplayEmailAddress().isEmpty() ? "" : ", " + user.getDisplayEmailAddress()
            ).concat(
                    user.getJobTitle().isEmpty() ? "" : ", " + user.getJobTitle()
            ).concat(
                    (birthday == null) ?
                            "" :
                            ", " + DateTimeUtils.dateToString(birthday)
            )//.concat(orgs.isEmpty() ? "" : orgs.toString())

                    .concat("<br>");


    }

    @Reference
    private volatile UserLocalService userLocalService;
}
