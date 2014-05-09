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
package org.exoplatform.addons.portlets.responsive;

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

    Element metaViewport = response.createElement("meta");
    metaViewport.setAttribute("name", "viewport");
    metaViewport.setAttribute("content", "width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1");
    response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, metaViewport);

    metaViewport = response.createElement("meta");
    metaViewport.setAttribute("name", "apple-mobile-web-app-capable");
    metaViewport.setAttribute("content", "yes");
    response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, metaViewport);

/*
    metaViewport = response.createElement("link");
    metaViewport.setAttribute("rel", "apple-touch-icon");
    metaViewport.setAttribute("href", "/demo-extension/img/chat-icon.png");
    response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, metaViewport);
*/

    //
    chain.doFilter(request, response);
  }

  public void destroy()
  {
  }
}
