package org.exoplatform.addons.portlets.simpleapp;

import org.w3c.dom.Element;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import java.io.IOException;

/** @author <a href="mailto:bpaillereau@exoplatform.com">Benjamin Paillereau</a> */
public class ResponseFilter implements RenderFilter
{

  public void init(FilterConfig filterConfig) throws PortletException
  {
  }

  public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain) throws IOException, PortletException
  {

    String id = request.getPreferences().getValue("id", null);
    String serverBase = ""; 
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort= request.getServerPort();
    serverBase = scheme+"://"+serverName;
    if (serverPort!=80) serverBase += ":"+serverPort;
    serverBase += "/rest/jcr/repository/dev-monit/"+id;


    String js = request.getPreferences().getValue("js", null);
    if (id!=null && (js==null || "default".equals(js)) ) {
      // http://demo.exoplatform.net/rest/jcr/repository/dev-monit/rss/default.js
      js = serverBase + "/default.js";
    }

    String css = request.getPreferences().getValue("css", null);
    if (id!=null && (css==null || "default".equals(css)) ) {
      // http://demo.exoplatform.net/rest/jcr/repository/dev-monit/rss/default.css
      css = serverBase + "/default.css";
    }


    if (js!=null) {
      Element jQuery1 = response.createElement("script");
      jQuery1.setAttribute("type", "text/javascript");
      jQuery1.setAttribute("src", js);
      response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jQuery1);
    }
    if (css!=null) {
      Element jQuery1 = response.createElement("link");
      jQuery1.setAttribute("rel", "stylesheet");
      jQuery1.setAttribute("type", "text/css");
      jQuery1.setAttribute("href", css);
      response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jQuery1);
    }

    //
    chain.doFilter(request, response);
  }

  public void destroy()
  {
  }
}
