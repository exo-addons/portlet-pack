package org.exoplatform.addons.portlets.moodswing;

import juzu.Path;
import juzu.Response;
import juzu.View;
import juzu.template.Template;

import javax.inject.Inject;
import java.io.IOException;

public class MoodSwingApplication
{

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @View
  public Response index() throws IOException
  {
      return indexTemplate.ok();
  }

}
