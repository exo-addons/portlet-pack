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
package org.exoplatform.addons.portlets.geoloc;

import juzu.*;
import juzu.impl.common.Builder;
import juzu.plugin.ajax.Ajax;
import juzu.request.HttpContext;
import juzu.request.ResourceContext;
import juzu.template.Template;
import juzu.request.RenderContext;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.User;
import org.exoplatform.services.organization.UserProfile;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.profile.ProfileFilter;
import org.exoplatform.social.core.space.spi.SpaceService;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import java.util.HashMap;
import java.util.Map;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class Controller
{
  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  PortletPreferences portletPreferences;

  OrganizationService organizationService_;

  @Inject
  public Controller(OrganizationService organizationService)
  {
    organizationService_ = organizationService;
  }
  static final String LATITUDE = "geoloc.latitude";
  static final String LONGITUDE = "geoloc.longitude";
  static final String COUNTRY = "geoloc.country";
  static final String CODE = "geoloc.code";

  String country_ = "";
  String code_ = "";

  @View
  public Response.Content index(RenderContext renderContext)
  {
    String remoteUser = renderContext.getSecurityContext().getRemoteUser();
    if ("".equals(code_)) {
      try {
        Map<String, String> infos = getInfos(remoteUser);
        country_ = infos.get(COUNTRY);
        code_ = infos.get(CODE);

      } catch (Exception e) {
      }
    }
    return indexTemplate.with()
            .set("country", country_)
            .set("code", code_)
            .ok();
  }


  @Ajax
  @Resource
  public Response.Content setGeoloc(String latitude, String longitude, String country, String code, ResourceContext resourceContext) {
    String remoteUser = resourceContext.getSecurityContext().getRemoteUser();
    if (code!=null && !code.equals(code_))
    {
      try {
        this.setInfos(remoteUser, latitude, longitude, country, code);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return Response.ok("ok").withMimeType("text/html; charset=UTF-8").withHeader("Cache-Control", "no-cache");

  }

  public void setInfos(String username, String latitude, String longitude, String country, String code) throws Exception {
    UserProfile userProfile = organizationService_.getUserProfileHandler().findUserProfileByName(username);
    userProfile.setAttribute(LATITUDE, latitude);
    userProfile.setAttribute(LONGITUDE, longitude);
    userProfile.setAttribute(COUNTRY, country);
    userProfile.setAttribute(CODE, code);
    organizationService_.getUserProfileHandler().saveUserProfile(userProfile, false);
  }

  public Map<String, String> getInfos(String username) throws Exception {
    UserProfile userProfile = organizationService_.getUserProfileHandler().findUserProfileByName(username);
    Map<String, String> infos = new HashMap<String, String>();
    infos.put(LATITUDE, userProfile.getAttribute(LATITUDE));
    infos.put(LONGITUDE, userProfile.getAttribute(LONGITUDE));
    infos.put(COUNTRY, userProfile.getAttribute(COUNTRY));
    infos.put(CODE, userProfile.getAttribute(CODE));
    return infos;
  }


}
