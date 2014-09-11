package org.exoplatform.addons.portlets.quicklook;


import juzu.SessionScoped;
import org.exoplatform.addons.portlets.quicklook.model.WikiPage;
import org.exoplatform.calendar.service.CalendarEvent;
import org.exoplatform.calendar.service.CalendarService;
import org.exoplatform.calendar.service.impl.JCRDataStorage;
import org.exoplatform.faq.service.DataStorage;
import org.exoplatform.faq.service.*;
import org.exoplatform.forum.common.jcr.KSDataLocation;
import org.exoplatform.forum.common.jcr.PropertyReader;
import org.exoplatform.forum.service.Category;
import org.exoplatform.forum.service.*;
import org.exoplatform.forum.service.Utils;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.webui.util.Util;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.space.spi.SpaceService;
import org.exoplatform.wiki.mow.core.api.wiki.PageImpl;
import org.exoplatform.wiki.resolver.TitleResolver;
import org.exoplatform.wiki.service.WikiService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("favoritesData")
@SessionScoped
public class QuicklookData {

  ForumService forumService_;
  WikiService wikiService_;
  KSDataLocation locator_;
  SpaceService spaceService_;
  FAQService faqService_;
  DataStorage dataStorage_;
    CalendarService calendarService_;



  @Inject
  public QuicklookData(ForumService forumService, WikiService wikiService, KSDataLocation locator, SpaceService spaceService, FAQService faqService, DataStorage dataStorage,CalendarService calendarService)
  {
    forumService_ = forumService;
    wikiService_ = wikiService;
    locator_ = locator;
    spaceService_ = spaceService;
    faqService_ = faqService;
    dataStorage_ = dataStorage;
      calendarService_ = calendarService;
  }

  protected List<WikiPage> getWikiTopLevel()
  {
    PageImpl page;
    String title = "Wiki Home";
    List<WikiPage> wikiPages = new ArrayList<WikiPage>();

    try
    {
//      System.out.println("SpaceGroup: *"+getSpaceGroup()+"*");
      page = (PageImpl) wikiService_.getPageById(PortalConfig.GROUP_TYPE, getSpaceGroup(), TitleResolver.getId(title, false));

      Map<String, PageImpl> childPages = page.getChildPages();

      for (PageImpl child:childPages.values())
      {

        WikiPage wikiPage = new WikiPage();
        wikiPage.setAuthor(child.getAuthor());
        wikiPage.setTitle(child.getTitle());
        wikiPage.setName(child.getName());
        wikiPage.setURL(child.getURL());

        wikiPages.add(wikiPage);

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return wikiPages;

  }

  protected List<Topic> getForumTopics()
  {
    String spaceGroup = getSpace();
    Space space = spaceService_.getSpaceByPrettyName(spaceGroup);

    String forumName = space.getDisplayName();
    try
    {
      Forum forum = getForumByName(forumName);
      Category cat = getCategoryByForumName(forumName);

      List<Topic> topics = forumService_.getTopics(cat.getId(), forum.getId());

      return topics;
    }
    catch (Exception e)
    {

    }

    return null;
  }

  protected List<Question> getQuestions()
  {

    String spaceGroup = getSpace();
    Space space = spaceService_.getSpaceByPrettyName(spaceGroup);
    String faqName = space.getDisplayName();

    try {
      List<org.exoplatform.faq.service.Category> categories = faqService_.getAllCategories();
      org.exoplatform.faq.service.Category currentCategory = null;
      for (org.exoplatform.faq.service.Category category:categories) {
        if (category.getName().equals(faqName)) {
          currentCategory = category;
        }
      }
      if (currentCategory !=null) {
        String cateId = currentCategory.getId();
        if (cateId.indexOf("/") > 0)
          cateId = cateId.substring(cateId.lastIndexOf("/") + 1);

        FAQSetting faqSetting = new FAQSetting();
        faqSetting.setOrderBy("createdBy");
        faqSetting.setOrderType("desc");
        faqSetting.setSortQuestionByVote(false);

        QuestionPageList questionPageList = dataStorage_.getQuestionsByCatetory(cateId, faqSetting);

        List<Question> questions = questionPageList.getAll();
        return questions;

      }


    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getSpaceGroup()
  {
    String uri = getSpace();
    if (uri != null)
    {
      return "spaces/"+uri;
    }

    return null;
  }

  protected String getSpace()
  {
    HttpServletRequest request = Util.getPortalRequestContext().getRequest();
    String uri = request.getRequestURI();
    if (uri.indexOf("/portal/g/:spaces:")>-1)
    {
      uri = uri.substring(18);
      uri = uri.substring(0, uri.indexOf("/"));

      return uri;

    }

    return null;
  }


  private Forum getForumByName(String forumName) throws Exception {
    StringBuffer sb = new StringBuffer(Utils.JCR_ROOT);
    sb.append("/").append(locator_.getForumCategoriesLocation()).append("//element(*,");
    sb.append(Utils.EXO_FORUM).append(")[jcr:like(exo:name, '%").append(forumName).append("%')]");

    NodeIterator iter =  forumService_.search(sb.toString());
    if (iter.hasNext()) {
      Node forumNode = (Node)iter.next();

      Forum forum = new Forum();
      PropertyReader reader = new PropertyReader(forumNode);
      forum.setId(forumNode.getName());
      forum.setPath(forumNode.getPath());
      forum.setOwner(reader.string(Utils.EXO_OWNER));
      forum.setForumName(reader.string(Utils.EXO_NAME));
      forum.setViewer(reader.strings(Utils.EXO_VIEWER));

      return forum;
    }


    return null;
  }

  private Category getCategoryByForumName(String forumName) throws Exception {
    StringBuffer sb = new StringBuffer(Utils.JCR_ROOT);
    sb.append("/").append(locator_.getForumCategoriesLocation()).append("//element(*,");
    sb.append(Utils.EXO_FORUM).append(")[jcr:like(exo:name, '%").append(forumName).append("%')]");

    NodeIterator iter =  forumService_.search(sb.toString());
    if (iter.hasNext()) {
      Node forumNode = (Node)iter.next();
      if (forumNode.getParent() != null) {
        Node cateNode =  forumNode.getParent();
        Category cat = new Category(cateNode.getName());
        cat.setPath(cateNode.getPath());
        PropertyReader reader = new PropertyReader(cateNode);
        cat.setOwner(reader.string(Utils.EXO_OWNER));
        cat.setCategoryName(reader.string(Utils.EXO_NAME));
        return cat;
      }
    }

    return null;
  }
    protected List<CalendarEvent> getEvents() {

        List<CalendarEvent> calendarEvents = null;
        StringBuffer calednarId = new StringBuffer();

        try {
            //--- Get the current Space
            String spaceApp = getSpace();
            calednarId.append(spaceApp);

            //--- Compute the calendar Ids
            List<String> calendarIds = new ArrayList<String>();
            calednarId.append("_space_calendar");
            calendarIds.add(calednarId.toString());

            calendarEvents = calendarService_.getGroupEventByCalendar(calendarIds);


        } catch (Exception E) {
            E.printStackTrace();
            return calendarEvents;
        }

        return calendarEvents;

    }

}
