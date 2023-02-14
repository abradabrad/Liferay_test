package testportlet.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Reference;
import testportlet.constants.TestPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import testportlet.util.DateTimeUtils;

import java.io.IOException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author serovam
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Test",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TestPortletKeys.TEST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TestPortlet extends MVCPortlet {

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	@Reference
	private volatile UserLocalService userLocalService;

	@Override
	public void render(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {

		request.setAttribute("userLocalService", getUserLocalService());
		//getUserFields(request);
		getUsersList(request);
		super.render(request, response);
	}

	private void getUsersList(RenderRequest request) {
		Long currentUserId = 0L;
		try {
			System.out.println(request.getAttribute(WebKeys.USER_ID));
			currentUserId = Long.parseLong(request.getUserPrincipal().getName());

		} catch (Exception e) {
			//ignore
			//return;
		}
		System.out.println(currentUserId);

		User currentUser =null;
		try {
			currentUser = userLocalService.getUser(currentUserId);
		} catch (PortalException e) {
			//e.printStackTrace();
		}
		//System.out.println(currentUser);
		List<Role> roles = null;
		if (Objects.nonNull(currentUser)) {
			roles = currentUser.getRoles();

			List<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());
			roleNames.add("Guest");
			System.out.println(roleNames);

			User finalCurrentUser = currentUser;
			List users = userLocalService.getUsers(0,userLocalService.getUsersCount())
					.stream()
					.filter(it -> it.getRoles()
							.stream()
							.allMatch(role1 -> finalCurrentUser.getRoles()
									.stream()
									.anyMatch(role1::equals)))
					.collect(Collectors.toList());
			System.out.println(users);
		}
		StringBuilder resultString = new StringBuilder();

		userLocalService.getUsers(0,userLocalService.getUsersCount())
				.stream()
				.map(this::convertToResultString)
				.forEach(resultString::append);
		request.setAttribute("LIST_USERS", resultString);

		/*
		userLocalService.getUsers(0,userLocalService.getUsersCount())
				.stream()
				.map(this::convertToResultString)
				.forEach(resultString::append);
		request.setAttribute("LIST_USERS", resultString);
		 */
	}

	private String convertToResultString(User user) {
		Date birthday = null;
		try {
			birthday = user.getBirthday();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		List<Organization> orgs = new ArrayList<>();
		try {
			orgs = user.getOrganizations();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		List<Phone> phones = user.getPhones();

		return "<strong>UserId:</strong> ".concat(
				String.valueOf(user.getUserId())
		).concat(
				user.getLastName().isEmpty() ? "" : ",<br><strong>Фамилия:</strong> " + user.getLastName()
		).concat(
				user.getFirstName().isEmpty() ? "" :  ",<br><strong>Имя:</strong> " + user.getFirstName()
		).concat(
				user.getMiddleName().isEmpty() ? "" :  ",<br><strong>Отчество:</strong> " + user.getMiddleName()
		).concat(
				user.getDisplayEmailAddress().isEmpty() ? "" :  ",<br><strong>Электронная почта:</strong> " + user.getDisplayEmailAddress()
		).concat(
				user.getJobTitle().isEmpty() ? "" :  ",<br><strong>Должность:</strong> " + user.getJobTitle()
		).concat(
				(birthday == null) ?
						"" :
						",<br><strong>Дата рождения:</strong> " + DateTimeUtils.dateToString(birthday, FormatStyle.LONG)
		).concat(
				orgs.isEmpty() ? "" : ",<br><strong>Организации:</strong> " + orgs.stream().map(Organization::getName).collect(Collectors.joining(", "))
		).concat(
				phones.isEmpty() ? "" : ",<br><strong>Телефоны:</strong> " + phones.stream().map(Phone::getNumber).collect(Collectors.joining(", "))
		).concat("<br>");

	}

	private void getUserFields(RenderRequest request) {
		Long userId = ParamUtil.getLong(request, "userId");
		User user = null;
		try {
			user = userLocalService.getUser(userId);
		} catch (PortalException e) {
			//e.printStackTrace();
		}
		if (Objects.nonNull(user)) {
			StringBuilder result = new StringBuilder(convertToResultString(user));
			request.setAttribute("USER", result);
		}

	}


}