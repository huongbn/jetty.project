//
// ========================================================================
// Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under
// the terms of the Eclipse Public License 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0
//
// This Source Code may also be made available under the following
// Secondary Licenses when the conditions for such availability set
// forth in the Eclipse Public License, v. 2.0 are satisfied:
// the Apache License v2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.webapp;

import java.util.ServiceLoader;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * <p>JSP Configuration</p>
 * <p>This configuration configures the WebAppContext server/system classes to
 * be able to see the org.eclipse.jetty.jsp and org.eclipse.jetty.apache packages.
 * This class is defined in the webapp package, as it implements the {@link Configuration} interface,
 * which is unknown to the jsp package.  However, the corresponding {@link ServiceLoader}
 * resource is defined in the jsp package, so that this configuration only be
 * loaded if the jetty-jsp jars are on the classpath.
 * </p>
 */
public class JspConfiguration extends AbstractConfiguration
{
    private static final Logger LOG = Log.getLogger(JspConfiguration.class);

    public JspConfiguration()
    {
        addDependencies(WebXmlConfiguration.class, MetaInfConfiguration.class, WebInfConfiguration.class, FragmentConfiguration.class);
        addDependents(WebAppConfiguration.class);
        protectAndExpose("org.eclipse.jetty.jsp.");
        expose("org.eclipse.jetty.apache.");
        hide("org.eclipse.jdt.");
    }

    @Override
    public boolean isAvailable()
    {
        try
        {
            return Loader.loadClass("org.eclipse.jetty.jsp.JettyJspServlet") != null;
        }
        catch (Throwable e)
        {
            LOG.ignore(e);
            return false;
        }
    }
}

