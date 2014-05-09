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
package org.exoplatform.addons.portlets.googleanalytics;

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

    String account = request.getPreferences().getValue("account", null);

    if (account!=null) {
      Element jQuery1 = response.createElement("script");
      jQuery1.setAttribute("type", "text/javascript");
      String textContent = "var _gaq = _gaq || [];\n" +
              "  _gaq.push(['_setAccount', '"+account+"']);\n" +
              "  _gaq.push(['_trackPageview']);\n" +
              "\n" +
              "  (function() {\n" +
              "    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n" +
              "    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n" +
              "    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n" +
              "  })();";

      jQuery1.setTextContent(textContent);
      response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jQuery1);
    }

    //
    chain.doFilter(request, response);
  }

  public void destroy()
  {
  }
}
