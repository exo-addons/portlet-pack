package org.exoplatform.addons.portlets.favorites;


import juzu.SessionScoped;
import org.exoplatform.portal.webui.util.Util;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

@Named("favoritesData")
@SessionScoped
public class FavoritesData {

  RepositoryService repositoryService_;

  NodeHierarchyCreator nodeHierarchyCreator_;

  SessionProviderService sessionProviderService_;

  private static final String LINK_NODETYPE = "exo:link";
  private static final String OPENED_PROPERTY = "exo:opened";
  private static final String TITLE_PROPERTY = "exo:title";
  private static final String URL_PROPERTY = "exo:linkURL";


  @Inject
  public FavoritesData(RepositoryService repositoryService, SessionProviderService sessionProviderService, NodeHierarchyCreator nodeHierarchyCreator)
  {
    repositoryService_ = repositoryService;
    nodeHierarchyCreator_= nodeHierarchyCreator;
    sessionProviderService_ = sessionProviderService;
    initFavoritesFolder();
  }

  public SessionProvider getUserSessionProvider() {
    SessionProvider sessionProvider = sessionProviderService_.getSessionProvider(null);
    return sessionProvider;
  }


  protected List<Favorite> getFavorites()
  {
    SessionProvider sessionProvider = getUserSessionProvider();
    try
    {
      //get info
      Session session = sessionProvider.getSession("collaboration", repositoryService_.getCurrentRepository());


      Node rootNode = session.getRootNode();
      Node docNode = rootNode.getNode(getUserFavoritesPath());

      NodeIterator nodes = docNode.getNodes();
      List<Favorite> favorites = new ArrayList<Favorite>();
      while (nodes.hasNext())
      {
        Node node = nodes.nextNode();
        Favorite favorite = getFavoriteFromNode(node);
        favorites.add(favorite);
      }
      return favorites;

    }
    catch (Exception e)
    {
      System.out.println("JCR::\n" + e.getMessage());
    }
    return null;
  }


  protected void addFavorite(String title, String url) throws Exception
  {
    SessionProvider sessionProvider = getUserSessionProvider();
    Session session = sessionProvider.getSession("collaboration", repositoryService_.getCurrentRepository());

    Node favHomeNode = session.getRootNode().getNode(getUserFavoritesPath());
    Node favNode = favHomeNode.addNode(getNameFromTitle(title), LINK_NODETYPE);
    favNode.setProperty(TITLE_PROPERTY, title);
    favNode.setProperty(URL_PROPERTY, url);
    session.save();

  }


  protected void deleteFavorite(String id) throws Exception
  {
    SessionProvider sessionProvider = getUserSessionProvider();
    Session session = sessionProvider.getSession("collaboration", repositoryService_.getCurrentRepository());

    Node node = getNodeById(id, session);
    node.remove();
    session.save();

  }

  private String getNameFromTitle(String title) {
    return "favorite-"+System.currentTimeMillis();
  }

  private Node getNodeById(String id, Session session) throws Exception
  {
    Node node = null;
    if (!id.contains("/"))
    {
      node = session.getNodeByUUID(id);
    }
    else
    {
      Node rootNode = session.getRootNode();
      String path = (id.startsWith("/"))?id.substring(1):id;
      node = rootNode.getNode(path);
    }

    return node;
  }

  private Favorite getFavoriteFromNode(Node node) throws Exception{
    Favorite favorite = new Favorite();
    if (node.hasProperty(TITLE_PROPERTY)) {
      favorite.setTitle(node.getProperty(TITLE_PROPERTY).getString());
    }
    if (node.hasProperty(URL_PROPERTY)) {
      favorite.setUrl(node.getProperty(URL_PROPERTY).getString());
    }
    favorite.setId(node.getUUID());

    return favorite;
  }



  private String getUserFavoritesPath()
  {
    String userName = Util.getPortalRequestContext().getRemoteUser();

    SessionProvider sessionProvider = getUserSessionProvider();
    try
    {
      Node userNode = nodeHierarchyCreator_.getUserNode(sessionProvider, userName);
      return userNode.getPath().substring(1)+"/Private/Bookmarks";
    }
    catch (Exception e)
    {
      System.out.println("JCR::" + e.getMessage());
    }

    return null;
  }

  private void initFavoritesFolder()
  {
    String userName = Util.getPortalRequestContext().getRemoteUser();

    SessionProvider sessionProvider = getUserSessionProvider();
    try
    {
      Node userNode = nodeHierarchyCreator_.getUserNode(sessionProvider, userName);
      Node privateNode = userNode.getNode("Private");
      if (!privateNode.hasNode("Bookmarks"))
      {
        Session session = sessionProvider.getSession("collaboration", repositoryService_.getCurrentRepository());
        privateNode = session.getRootNode().getNode(userNode.getPath().substring(1)+"/Private");
        Node bookmarks = privateNode.addNode("Bookmarks", "nt:unstructured");
        bookmarks.addMixin("exo:hiddenable");
        session.save();
      }
    }
    catch (Exception e)
    {
      System.out.println("JCR::" + e.getMessage());
    }

  }


  public class Favorite {
    String id;
    String title;
    String url;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

  }
}
