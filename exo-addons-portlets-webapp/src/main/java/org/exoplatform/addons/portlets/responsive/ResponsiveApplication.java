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

import juzu.Path;
import juzu.Response;
import juzu.View;
import juzu.template.Template;

import javax.inject.Inject;
import java.io.IOException;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
public class ResponsiveApplication
{

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @View
  public Response.Content index() throws IOException
  {
    //indexTemplate.render();
    return Response.ok("");
  }

}
