package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aleksei on 19.10.14.
 */
public interface Frontend {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException;

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException;
}
