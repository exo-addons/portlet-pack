/*
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.addons.portlets.simpleapp;

import juzu.Path;
import juzu.Response;
import juzu.View;
import juzu.template.Template;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
public class Controller
{
  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  PortletPreferences portletPreferences;

  @View
  public Response.Content index()
  {
    String id = portletPreferences.getValue("id", "");
    String title = portletPreferences.getValue("title", "");
    String html = portletPreferences.getValue("html", "");
    String cache = portletPreferences.getValue("cache", "-1");
    String decoration = portletPreferences.getValue("decoration", "true");
/*
    String serverBase = getServerBase(renderContext.getHttpContext(), id);
    String js = portletPreferences.getValue("js", null);
    if (id!=null && (js==null || "default".equals(js)) ) {
      // http://demo.exoplatform.net/rest/jcr/repository/dev-monit/rss/default.js
      js = serverBase + "/default.js";
    }
*/

    return indexTemplate.with()
            .set("title", title)
            .set("id", id)
            .set("html", html)
            .set("cache", cache)
            .set("decoration", decoration)
            .ok();
  }


/*
  private String getServerBase(HttpContext request, String id) {
    String serverBase = "";
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort= request.getServerPort();
    serverBase = scheme+"://"+serverName;
    if (serverPort!=80) serverBase += ":"+serverPort;
    serverBase += "/rest/jcr/repository/dev-monit/"+id;

    return serverBase;
  }
*/
}
