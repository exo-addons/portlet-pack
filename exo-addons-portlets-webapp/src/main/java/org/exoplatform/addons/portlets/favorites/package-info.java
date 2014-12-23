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
/*
@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/simpleapp.js", id = "simpleapp")
        }
)
*/
@Application
@Portlet

@Bindings(
        {
                @Binding(value = org.exoplatform.services.jcr.RepositoryService.class),
                @Binding(value = org.exoplatform.services.jcr.ext.app.SessionProviderService.class),
                @Binding(value = org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator.class)
        }
)

@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/jquery-1.8.3.min.js", id = "jquery"),
                @Script(src = "js/favorites.js", id = "favorites", depends = "jquery")
        }
)

package org.exoplatform.addons.portlets.favorites;


import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.binding.Binding;
import juzu.plugin.binding.Bindings;
import juzu.plugin.portlet.Portlet;



