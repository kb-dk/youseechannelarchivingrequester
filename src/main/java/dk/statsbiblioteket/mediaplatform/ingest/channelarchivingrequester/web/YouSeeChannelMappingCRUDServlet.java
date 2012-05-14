/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 * The Netarchive Suite - Software to harvest and preserve websites
 * Copyright 2004-2009 Det Kongelige Bibliotek and Statsbiblioteket, Denmark
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *   USA
 */
package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class YouSeeChannelMappingCRUDServlet extends HttpServlet {
    public static final String ID = "ID";
    public static final String SB_ID = "sb_id";
    public static final String UC_ID = "uc_id";
    public static final String DISPLAY_NAME = "display_name";
    public static final String FROM_DATE = "from_date";
    public static final String TO_DATE = "to_date";

    public static final String SUBMIT_ACTION = "action";

        public static final String CREATE = "create";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";

        public static final SimpleDateFormat JAVA_DATE_FORMAT =  new SimpleDateFormat("yyyy-MM-dd HH:mm");



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page_attr", "you_see_channel_mapping");
             req.getSession().getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

    }
}
