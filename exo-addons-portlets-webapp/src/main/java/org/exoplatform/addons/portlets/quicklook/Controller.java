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
package org.exoplatform.addons.portlets.quicklook;

import juzu.*;
import juzu.plugin.ajax.Ajax;
import juzu.template.Template;
import org.exoplatform.addons.portlets.quicklook.model.WikiPage;
import org.exoplatform.calendar.service.CalendarEvent;
import org.exoplatform.faq.service.Question;
import org.exoplatform.forum.service.Topic;
import org.exoplatform.portal.webui.util.Util;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class Controller
{
  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  QuicklookData quicklookData_;

  @Inject
  PortletPreferences portletPreferences;

  @View
  public Response.Content index()
  {
    String app = portletPreferences.getValue("app", "Wiki");


    return indexTemplate.with()
            .set("title", app)
            .ok();
  }


  @Ajax
  @Resource
  public Response.Content getWikiPages()
  {

    List<WikiPage> wikiPages = quicklookData_.getWikiTopLevel();

    String jsonFav = getWikiPageToJson(wikiPages);

//    System.out.println(jsonFav);

    return Response.ok(jsonFav).withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
  }

  @Ajax
  @Resource
  public Response.Content getForumTopics()
  {

    List<Topic> topics = quicklookData_.getForumTopics();

    String jsonFav = getForumTopicsToJson(topics);

//    System.out.println(jsonFav);

    return Response.ok(jsonFav).withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
  }

  @Ajax
  @Resource
  public Response.Content getQuestions()
  {

    List<Question> questions = quicklookData_.getQuestions();

    String jsonFav = getQuestionsToJson(questions);

    return Response.ok(jsonFav).withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
  }
    @Ajax
    @Resource
    public Response.Content getEvents()
    {

        List<CalendarEvent> events = quicklookData_.getEvents();

        String jsonEvents = getCalendarEventsToJson(events);

        return Response.ok(jsonEvents).withMimeType("application/json; charset=UTF-8").withHeader("Cache-Control", "no-cache");
    }



  private static String getWikiPageToJson(List<WikiPage> wikiPages)
  {
    StringBuilder swikiPages = new StringBuilder();
    swikiPages.append("{ \"pages\": [");

    boolean first=true;
    for (WikiPage wikiPage:wikiPages)
    {
      if (!first) swikiPages.append(",");
      first = false;
      swikiPages.append("{\"name\":\"").append(wikiPage.getName()).append("\",");
      swikiPages.append("\"title\":\"").append(wikiPage.getTitle()).append("\",");
      swikiPages.append("\"url\":\"").append(wikiPage.getURL()).append("\",");
      swikiPages.append("\"author\":\"").append(wikiPage.getAuthor()).append("\"}");
    }

    swikiPages.append("]}");
    return swikiPages.toString();
  }


  private String getForumTopicsToJson(List<Topic> topics)
  {
    StringBuilder sforumTopics = new StringBuilder();
    sforumTopics.append("{ \"topics\": [");

    boolean first=true;
    for (Topic topic:topics)
    {
      if (!first) sforumTopics.append(",");
      first = false;
      HttpServletRequest request = Util.getPortalRequestContext().getRequest();
      String uri = request.getRequestURI() + "/forum/topic/" + topic.getId();
      sforumTopics.append("{\"topicName\":\"").append(topic.getTopicName()).append("\",");
      sforumTopics.append("\"description\":\"").append(topic.getDescription()).append("\",");
      sforumTopics.append("\"id\":\"").append(topic.getId()).append("\",");
      sforumTopics.append("\"uri\":\"").append(uri).append("\",");
      sforumTopics.append("\"lastPostBy\":\"").append(topic.getLastPostBy()).append("\"}");
    }

    sforumTopics.append("]}");
    return sforumTopics.toString();
  }

  private String getQuestionsToJson(List<Question> questions)
  {
    StringBuilder sQuestions = new StringBuilder();
    sQuestions.append("{ \"questions\": [");

    boolean first=true;
    for (Question question:questions)
    {
      if (!first) sQuestions.append(",");
      first = false;
      HttpServletRequest request = Util.getPortalRequestContext().getRequest();
      String uri = request.getRequestURI() + "/faq#" + question.getId();
      sQuestions.append("{\"id\":\"").append(question.getId()).append("\",");
      if (question.getAnswers().length>0)
        sQuestions.append("\"answer\":\"").append(question.getAnswers()[0].getResponses()).append("\",");
        sQuestions.append("\"uri\":\"").append(uri).append("\",");
      sQuestions.append("\"question\":\"").append(question.getQuestion()).append("\"}");
    }

    sQuestions.append("]}");
    return sQuestions.toString();
  }

    private String getCalendarEventsToJson(List<CalendarEvent> events)
    {

        Locale locale =  Util.getPortalRequestContext().getLocale();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm",locale);
        StringBuilder sEvents = new StringBuilder();
        sEvents.append("{ \"events\": [");

        boolean first=true;
        for (CalendarEvent event:events)
        {
            if (!first) sEvents.append(",");
            first = false;

            if (!event.getEventState().equalsIgnoreCase(CalendarEvent.COMPLETED)) {
                sEvents.append("{\"from\":\"").append(dateFormat.format(event.getFromDateTime())).append("\",");
                sEvents.append("\"to\":\"").append(dateFormat.format(event.getToDateTime())).append("\",");
                sEvents.append("\"url\":\"").append("/portal/intranet/calendar/details/").append(event.getId()).append("\",");
                sEvents.append("\"titre\":\"").append(event.getSummary()).append("\"}");
            }

        }

        sEvents.append("]}");
        return sEvents.toString();
    }



}
