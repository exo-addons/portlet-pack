package org.exoplatform.addons.portlets.quicklook.model;

/**
 * Created by benjamin on 08/09/2014.
 */
public class WikiPage
{
  String title;
  String author;
  String name;
  String URL;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
