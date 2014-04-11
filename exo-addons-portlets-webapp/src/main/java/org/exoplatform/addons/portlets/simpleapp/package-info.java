/*
@Assets(
        location = AssetLocation.SERVER,
        scripts = {
                @Script(src = "js/simpleapp.js", id = "simpleapp")
        }
)
*/
@Application
@Portlet package org.exoplatform.addons.portlets.simpleapp;

import juzu.Application;
import juzu.asset.AssetLocation;
import juzu.plugin.asset.Assets;
import juzu.plugin.asset.Script;
import juzu.plugin.portlet.Portlet;

