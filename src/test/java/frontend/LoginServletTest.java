package frontend;

import backend.AccountServiceImpl;
import base.UserProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServletTest {

    private AccountServiceImpl service = new AccountServiceImpl();
    private HttpSession httpSession = Mockito.mock(HttpSession.class);
    private LoginServlet loginServlet = new LoginServlet(this.service);
    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    private UserProfile getLoginUser() {
        String loginLogin = "login";
        String loginEmail = "login@login.ru";
        String loginPass = "login";
        return new UserProfile(loginLogin, loginEmail, loginPass);
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoPost() throws Exception {
        UserProfile user = this.getLoginUser();
        service.registerUser(user, httpSession);
        service.logoutUser(httpSession);
        service.logoutUser(httpSession);
        service.deleteUser(getLoginUser().getEmail());
    }

    @Test
    public void testDoGet() throws Exception {
        loginServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        verify(response).sendRedirect("/#");
    }
}