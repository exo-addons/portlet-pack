package org.exoplatform.addons.portlets.map;

import juzu.*;
import juzu.io.Stream;
import juzu.plugin.ajax.Ajax;
import juzu.request.RenderContext;
import juzu.template.Template;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import org.exoplatform.addons.portlets.map.templates.index;

public class MapApplication {


    /**
     * .
     */
    @Inject
    @Path("index.gtmpl")
    index index;

    @View
    public void index() throws IOException {
        index("", "");
    }

    @View
    public void index(String location, String mapURL) throws IOException {
        index.with().location(location).mapURL(mapURL).render();
    }

    @Action
    public Response updateLocation(String location) throws IOException {
        String mapURL = "https://maps.google.fr/maps?f=q&source=s_q&hl=en&geocode=&q=" + location + "&aq=&t=m&ie=UTF8&hq=&hnear=" + location + "&z=12&output=embed";
        return MapApplication_.index(location, mapURL);
    }


    @Ajax
    @Resource
    public Response.Content<Stream.Char> getMapURL(String location) throws IOException {
        String mapURL = "https://maps.google.fr/maps?f=q&source=s_q&hl=en&geocode=&q=" + location + "&aq=&t=m&ie=UTF8&hq=&hnear=" + location + "&z=12&output=embed";

        return Response.ok("{\"mapURL\": \"" + mapURL +"\"}").withMimeType("application/json");
    }
}
