@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/jquery-1.8.3.min.js", id = "jQuery"),
               /* @Script(src = "js/userProfileInfo.js", id = "userProfileInfo"),*/
                @Script(src = "js/image-picker.js", id = "imagePicker", depends = "jQuery"),
                @Script(src = "js/moodswing.js", id = "moodswing", depends = "imagePicker")
        },
        stylesheets = {
                @Stylesheet(src = "/org/exoplatform/addons/portlets/moodswing/assets/moodswing.css", location = AssetLocation.APPLICATION)
        }
)
@Less(value = "moodswing.less", minify = true)

@Application(defaultController = MoodSwingApplication.class)
@Portlet(name="MoodSwingPortlet") package org.exoplatform.addons.portlets.moodswing;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.asset.Stylesheet;
import juzu.plugin.less.Less;
import juzu.plugin.portlet.Portlet;
