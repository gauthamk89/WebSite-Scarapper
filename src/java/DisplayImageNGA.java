/* This is a short example of MVC.
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this file.  So it is also the starting place for the web
 * application.
 *
 * The servlet is acting as the controller.
 * There are two views - prompt.jsp and result.jsp.
 * It decides between the two by determining if there is a search parameter or
 * not. If there is no parameter, then it uses the prompt.jsp view, as a 
 * starting place. If there is a search parameter, then it searches for a 
 * picture and uses the result.jsp view.
 * The model is provided by InterestingPictureModel.
 */

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /InterestingPictureServlet
 * then the servlet with the name InterestingPictureServlet should be used.
 */
@WebServlet(name = "DisplayImageNGA",
        urlPatterns = {"/getAnInterestingPicture"})
public class DisplayImageNGA extends HttpServlet {

    InterestingPictureModel ipm = null;  // The "business model" for this app
    String url = null;

    @Override
    public void init() {
        ipm = new InterestingPictureModel();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * Get the search parameter if it exists
         */
        String search = request.getParameter("searchWord");
        String url = "";
        /**
         * Determine the Agent using the page
         */
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        /**
         * Check the DocType according to kind of device
         */
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        /**
         * Check if search string is null
         */
        if (search != null) {
            // use model to do the search and choose the result view
            ipm.SearchImage(search);
            if (mobile == true) {
                /**
                 * Set the URL for mobile URL
                 */
                url = ipm.getMobileURL();
                request.setAttribute("heading",ipm.getTitle());
                url = "https://images.nga.gov/" + url;
                request.setAttribute("pictureTag",url);
            } else {
                /**
                 * Set the URL for desktop URL
                 */
                request.setAttribute("heading",ipm.getTitle());
                url = ipm.getDesktopURL();
                 request.setAttribute("pictureTag",url);
            }          
        }
        /**
         * Transfer control over the the correct "view"
         */
        if(url.equals("not found"))
        {
           RequestDispatcher view = request.getRequestDispatcher("result.jsp");
           view.forward(request, response); 
        }
        else
        {
            
            RequestDispatcher view = request.getRequestDispatcher("result.jsp");
            view.forward(request, response);
        }
    }
}
