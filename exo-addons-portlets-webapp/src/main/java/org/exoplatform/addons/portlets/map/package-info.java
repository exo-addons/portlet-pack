@juzu.plugin.asset.Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @juzu.plugin.asset.Script(
                        id = "jquery",
                        src = "js/jquery-1.8.3.min.js")
        }
)
/*
@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/Chart.min.js", id = "chart"),
                @Script(src = "js/map.js", id = "map", depends = "chart")
        },
        stylesheets = {
                @Stylesheet(src = "/org/exoplatform/addons/portlets/map/assets/map.css", location = AssetLocation.APPLICATION)
        }
)


@Less(value = "map.less", minify = true)

*/

@Application(defaultController = MapApplication.class)
@Portlet(name="MapPortlet") package org.exoplatform.addons.portlets.map;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.less.Less;
import juzu.plugin.portlet.Portlet;