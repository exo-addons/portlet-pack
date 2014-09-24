package org.exoplatform.addons.portlets.mycrm;

import juzu.Path;
import juzu.View;
import juzu.request.RenderContext;
import juzu.template.Template;

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
    public void index(RenderContext renderContext) throws IOException
    {
        String remoteUser = renderContext.getSecurityContext().getRemoteUser();

        indexTemplate.render();
    }
}
