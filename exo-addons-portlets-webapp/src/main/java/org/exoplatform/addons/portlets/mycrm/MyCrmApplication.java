package org.exoplatform.addons.portlets.mycrm;

import juzu.Path;
import juzu.Response;
import juzu.View;
import juzu.template.Template;
import org.exoplatform.web.application.RequestContext;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by kmenzli on 23/09/2014.
 */
public class MyCrmApplication {

    @Inject
    @Path("index.gtmpl")
    Template indexTemplate;

    @View
    public Response index() throws IOException
    {
        String remoteUser = RequestContext.getCurrentInstance().getRemoteUser();

        return indexTemplate.ok();
    }
}
