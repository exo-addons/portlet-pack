package org.exoplatform.addons.portlets.simpleapp;

import juzu.*;
import juzu.request.HttpContext;
import juzu.template.Template;
import juzu.request.RenderContext;
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
  public Response.Content index(RenderContext renderContext)
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
