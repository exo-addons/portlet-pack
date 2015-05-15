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
package org.exoplatform.addons.portlets.rss;

import juzu.Path;
import juzu.Resource;
import juzu.Response;
import juzu.View;
import juzu.template.Template;
import org.exoplatform.addons.portlets.rss.model.Feed;
import org.exoplatform.addons.portlets.rss.model.Item;
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

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
public class RssApplication
{

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  @Path("feed.gtmpl")
  Template feedTemplate;


  @Inject
  PortletPreferences portletPreferences;

  @View
  public Response index() throws IOException
  {
    String cache = portletPreferences.getValue("cache", "300");
    String url = portletPreferences.getValue("url", "");
    return indexTemplate.with().set("cache", cache).set("id", url.hashCode()).ok();
  }

  @Resource
  public Response getFeed() throws Exception {

    String sizePref = portletPreferences.getValue("size", "5");
    String url = portletPreferences.getValue("url", "");
    Document document_ = getData(url);
    Integer size_ = Integer.parseInt(sizePref);

    NodeList nodeList = document_.getElementsByTagName("item");

    String title = ((Element)document_.getElementsByTagName("channel").item(0)).getElementsByTagName("title").item(0).getTextContent();
    String more = ((Element)document_.getElementsByTagName("channel").item(0)).getElementsByTagName("link").item(0).getTextContent();

    Feed feed_ = new Feed();
    feed_.setTitle(title);
    feed_.setMore(more);

    for (int i = 0; i < nodeList.getLength() && i < size_; i++)
    {
      Node node = nodeList.item(i);

      Element fstElmnt = (Element) node;

      title = ((Element)fstElmnt.getElementsByTagName("title").item(0)).getTextContent();
      String link = ((Element)fstElmnt.getElementsByTagName("link").item(0)).getTextContent();
      String pubDate = ((Element)fstElmnt.getElementsByTagName("pubDate").item(0)).getTextContent();
      String desc = ((Element)fstElmnt.getElementsByTagName("description").item(0)).getTextContent();

      Item item = new Item();
      item.setTitle(title);
      item.setLink(link);
      item.setDescription(desc);
      item.setPubDate(pubDate);

      feed_.addItem(item);

    }

    return feedTemplate.with().set("feed", feed_).ok();

  }

  private Document getData(String uri) {

    Document doc = null;

    try {
      URL url = new URL(uri);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/xml");

      InputStream xml = connection.getInputStream();

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(xml);


    } catch (IOException e) {
    } catch (ParserConfigurationException e) {
    } catch (SAXException e) {
    }

    return doc;
  }

}
