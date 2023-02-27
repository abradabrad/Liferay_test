package testportlet.render;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import testportlet.constants.TestPortletKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Objects;

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
        getUserFields(renderRequest);
        return "/view_user.jsp";
    }

    private void getUserFields(RenderRequest request) {
        Long userId = ParamUtil.getLong(request, "userId");
        User user = null;
        try {
            user = userLocalService.getUser(userId);
        } catch (PortalException e) {
            e.printStackTrace();
        }
        if (Objects.nonNull(user)) {
            request.setAttribute("USER", user);
        }
    }

    @Reference
    private volatile UserLocalService userLocalService;
}
