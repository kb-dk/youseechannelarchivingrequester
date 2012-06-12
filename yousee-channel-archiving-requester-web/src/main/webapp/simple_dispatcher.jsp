<%
    response.setCharacterEncoding("UTF-8");
    //ControllerServlet.setUTF8(request);
    request.setAttribute("page_attribute", request.getParameter("page"));
    session.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
%>