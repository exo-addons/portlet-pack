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
package org.exoplatform.addons.portlets.favorites;

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
import java.util.List;
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
  FavoritesData favoritesData_;


  @View
  public Response.Content index()
  {
    return indexTemplate.ok();
  }

  @Ajax
  @Resource
  public Response.Content addFavorite(String title, String url)
  {
    try {
      favoritesData_.addFavorite(title, url);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Response.ok("{\"status\":\"ok\"}").withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
  }

  @Ajax
  @Resource
  public Response.Content deleteFavorite(String uuid)
  {
    try {
      favoritesData_.deleteFavorite(uuid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Response.ok("{\"status\":\"ok\"}").withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
  }

  @Ajax
  @Resource
  public Response.Content getFavorites()
  {

    List<FavoritesData.Favorite> favorites = favoritesData_.getFavorites();
    String jsonFav = getFavoritesToJson(favorites);

    return Response.ok(jsonFav).withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
  }


  private static String getFavoritesToJson(List<FavoritesData.Favorite> favoriteList)
  {
    StringBuilder favorites = new StringBuilder();
    favorites.append("{ \"favorites\": [");

    boolean first=true;
    for (FavoritesData.Favorite favorite:favoriteList)
    {
      if (!first) favorites.append(",");
      first = false;
      favorites.append("{\"id\":\"").append(favorite.getId()).append("\",");
      favorites.append("\"title\":\"").append(favorite.getTitle()).append("\",");
      favorites.append("\"url\":\"").append(favorite.getUrl()).append("\"}");
    }

    favorites.append("]}");
    return favorites.toString();
  }


}
