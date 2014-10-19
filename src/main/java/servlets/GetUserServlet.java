package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import account_service.AccountService;
import account_service.UserProfile;
import org.json.*;

/**
 * Created by Алексей on 23.09.2014.
 */
public class GetUserServlet extends HttpServlet {
    private AccountService service;
    public GetUserServlet(AccountService service) {
        this.service = service;
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException, NullPointerException {
//        UserProfile user = service.getUserBySession(request.getSession().toString());
        UserProfile user = service.getUserBySession_DB(request.getSession());
        if ( user==null ) {
            user = new UserProfile("Guest","","");
        }
        String[] jsnKeys = {"id","email","login","score"};
        JSONObject jsnObj = new JSONObject(user, jsnKeys);
        response.getWriter().println(jsnObj.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException { response.setStatus(HttpServletResponse.SC_FORBIDDEN); }
}