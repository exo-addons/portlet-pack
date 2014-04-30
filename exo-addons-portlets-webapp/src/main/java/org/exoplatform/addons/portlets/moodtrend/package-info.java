@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/highcharts.js", id = "highcharts"),
                @Script(src = "js/exporting.js", id = "exporting")
        },
        stylesheets = {
                @Stylesheet(src = "/org/exoplatform/addons/portlets/moodtrend/assets/moodtrend.css", location = AssetLocation.APPLICATION)
        }
)
@Less(value = "moodtrend.less", minify = true)


@Application(defaultController = MoodTrendApplication.class)
@Portlet(name="MoodTrendPortlet") package org.exoplatform.addons.portlets.moodtrend;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.less.Less;
import juzu.plugin.portlet.Portlet;
