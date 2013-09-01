// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package net.sourceforge.eclipsejetty;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.ArtifactKey;
import org.eclipse.m2e.core.project.IMavenProjectFacade;

/**
 * Some utilities
 * 
 * @author Christian K&ouml;berl
 * @author Manfred Hantschel
 */
public class JettyPluginM2EUtils
{

    public static boolean isM2EAvailable()
    {
        try
        {
            Class.forName("org.eclipse.m2e.core.MavenPlugin");
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }

        return true;
    }

    public static IMavenProjectFacade getMavenProjectFacade(ILaunchConfiguration configuration) throws CoreException
    {
        String projectName = JettyPluginConstants.getProject(configuration);

        if ((projectName != null) && (projectName.length() > 0))
        {
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

            if (project != null)
            {
                return MavenPlugin.getMavenProjectRegistry().getProject(project);
            }
        }

        return null;
    }

    public static String toPortableString(String groupId, String artifactId, String version, String classifier)
    {
        return toPortableString(groupId, artifactId, version, classifier, null);
    }

    public static String toPortableString(String groupId, String artifactId, String version, String classifier,
        String variant)
    {
        StringBuilder builder = new StringBuilder();

        if (groupId != null)
        {
            builder.append(groupId);
        }

        builder.append(':');

        if (artifactId != null)
        {
            builder.append(artifactId);
        }

        builder.append(':');

        if (version != null)
        {
            builder.append(version);
        }

        builder.append(':');

        if (classifier != null)
        {
            builder.append(classifier);
        }

        builder.append(':');

        if (variant != null)
        {
            builder.append(variant);
        }

        return builder.toString();
    }

    public static String toPath(ArtifactKey artifactKey)
    {
        StringBuilder builder = new StringBuilder();

        if (artifactKey.getGroupId() != null)
        {
            builder.append(artifactKey.getGroupId().replace('.', '/'));
        }

        if (artifactKey.getVersion() != null)
        {
            if (builder.length() > 0)
            {
                builder.append("/");
            }

            builder.append(artifactKey.getVersion());
        }

        if (builder.length() > 0)
        {
            builder.append("/");
        }

        builder.append(artifactKey.getArtifactId());

        if (artifactKey.getVersion() != null)
        {
            builder.append("-").append(artifactKey.getVersion());
        }

        // TODO where's the classifier!?

        return builder.toString();
    }

}