@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/Chart.min.js", id = "chart"),
                @Script(src = "js/moody.js", id = "moody", depends = "chart")
        },
        stylesheets = {
                @Stylesheet(src = "/org/exoplatform/addons/portlets/moody/assets/moody.css", location = AssetLocation.APPLICATION)
        }
)
@Less(value = "moody.less", minify = true)


@Application(defaultController = MoodyApplication.class)
@Portlet(name="MoodyPortlet") package org.exoplatform.addons.portlets.moody;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.less.Less;
import juzu.plugin.portlet.Portlet;
