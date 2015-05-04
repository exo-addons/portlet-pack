

@Scripts(
        {
                @Script(value = "js/moodswing.js", id = "moodswing",location = AssetLocation.SERVER)
        }
)
@Stylesheets(
        {
                @Stylesheet(value = "/org/exoplatform/addons/portlets/moodswing/assets/moodswing.css", location = AssetLocation.APPLICATION)
        }
)
@Less(value = "moodswing.less", minify = true)

@Application(defaultController = MoodSwingApplication.class)
@Portlet(name="MoodSwingPortlet")

@Assets("*")
package org.exoplatform.addons.portlets.moodswing;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.*;
import juzu.plugin.less.Less;
import juzu.plugin.portlet.Portlet;
