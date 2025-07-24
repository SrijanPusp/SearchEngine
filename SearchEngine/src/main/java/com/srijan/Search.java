package com.srijan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        Connection connection = DatabaseConnection.getConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT pagetitle, pagelink, (length(lower(pageText)) - length(replace(lower(pageText), '" + keyword.toLowerCase() + "', '')))/length('" + keyword.toLowerCase() + "') as countoccurance from pages order by countoccurance desc limit 20;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            for(SearchResult result:results)
            {
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }
            request.setAttribute("results", results);
            request.getRequestDispatcher("search.jsp").forward(request, response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch(SQLException | ServletException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
