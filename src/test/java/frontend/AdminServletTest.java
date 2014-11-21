package frontend;

import backend.AccountServiceImpl;
import base.UserProfile;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.security.Permission;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AdminServletTest extends TestCase {

    private AccountServiceImpl service = new AccountServiceImpl();
    private HttpSession httpSession = Mockito.mock(HttpSession.class);
    private AdminServlet adminServlet = new AdminServlet(this.service);
    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private PrintWriter printWriter = Mockito.mock(PrintWriter.class);

    private UserProfile getAdminUser() {
        String authLogin = "admin";
        String authEmail = "admin@admin.ru";
        String authPass = "admin";
        return new UserProfile(authLogin, authEmail, authPass);
    }

    private UserProfile getNotAdminUser() {
        String authLogin = "notadmin";
        String authEmail = "notadmin@notadmin.ru";
        String authPass = "notadmin";
        return new UserProfile(authLogin, authEmail, authPass);
    }

    @Before
    public void setUp() throws Exception {
        service.logoutUser(httpSession);
    }

    @After
    public void tearDown() throws Exception {
        service.logoutUser(httpSession);
    }

   public void testDoGetShowOk() throws Exception {
       service.authUser(this.getAdminUser(), httpSession);
       when(request.getSession()).thenReturn(httpSession);
       when(request.getParameter("shutdown")).thenReturn(null);
       when(response.getWriter()).thenReturn(printWriter);
       adminServlet.doGet(request, response);
       verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    public void testDoGetStopServer() throws Exception {
        service.authUser(this.getAdminUser(), httpSession);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("shutdown")).thenReturn("1000");
    }

    public void testDoGetFail() throws Exception {
        service.registerUser(this.getNotAdminUser(), httpSession);
        service.authUser(this.getNotAdminUser(), httpSession);
        when(request.getSession()).thenReturn(httpSession);
        adminServlet.doGet(request, response);
        verify(response).sendRedirect("/#");
        verify(response).setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
    }

    public void testDoPost() throws Exception {
        adminServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}