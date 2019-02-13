package com.h.jamil.api.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Git Version information, will use the git.properties created by maven
 *
 * To actually create the git.properties add the following to the pom under <build><plugins>
 *
 * <plugin>
 *     <groupId>pl.project13.maven</groupId>
 *     <artifactId>git-commit-id-plugin</artifactId>
 *     <version>2.2.1</version>
 *     <executions>
 *         <execution>
 *             <phase>validate</phase>
 *             <goals>
 *                 <goal>revision</goal>
 *             </goals>
 *         </execution>
 *     </executions>
 *     <configuration>
 *         <dateFormat>yyyyMMdd-HHmmss</dateFormat><!--  human-readable part of the version number -->
 *         <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
 *         <generateGitPropertiesFile>true</generateGitPropertiesFile><!-- somehow necessary. otherwise the variables are not available in the pom -->
 *     </configuration>
 * </plugin>
 *
 * To use just replace .version("1.0") in the SwaggerConfiguration with
 *   .version("1.0 "+gitVersion.getVersionString())
 *
 *   Along with
 *
 *   @Autowired
 *   GitVersion gitVersion;
 *
 *  The version string, looks like (tag: > 3.7 commit: ac24dd3 built: 20171005-122150 )
 *
 * This means that the last tag was 3.7 but it has changed since.
 * The last 7 chars of the last git commit are ac24dd3
 * And the built date and time is 20171005-122150
 */
@Configuration
@PropertySource(value = { "classpath:git.properties" },ignoreResourceNotFound = true)
public class GitVersion {
    @Value("${git.commit.id.abbrev:}")
    String commitId;
    @Value("${git.tags:}")
    String tag;
    @Value("${git.closest.tag.name:}")
    String closestTag;
    @Value("${git.build.time:}")
    String builtTime;
    @Value("${git.build.version:}")
    String buildVersion;

    /**
     * Get the last commit id
     */
    public String getCommitId() {
        return commitId;
    }

    /**
     * Get the last tag or the current tag, if the content has changed since the last tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Get the built date/time
     */
    public String getBuiltTime() {
        return builtTime;
    }

    /**
     * Get the build version
     */
    public String getBuildVersion() {
        return buildVersion;
    }

    /**
     * Get the git version string, e.g. (tag: > 3.7 commit: ac24dd3 built: 20171005-122150 )
     *
     * This means that the last tag was 3.7 but it has changed since.
     * The last 7 chars of the last git commit are ac24dd3
     * And the built date and time is 20171005-122150
     */
    public String getVersionString(){
        String actualTag= this.tag;
        if (isEmpty(actualTag) && isNotEmpty(closestTag))
            actualTag= "> "+closestTag;

        StringBuilder sb= new StringBuilder(" ");
        if (isNotEmpty(actualTag)){
            sb.append("tag: "+actualTag+" ");
        }
        if (isNotEmpty(commitId)){
            sb.append("commit: "+commitId+ " ");
        }
        if (isNotEmpty(builtTime)){
            sb.append("built: "+builtTime+ " ");
        }
        if (sb.length()>1){
            sb.setCharAt(0, '(');
            sb.append(")");
        }
        return sb.toString();
    }



    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * isEmpty(null)      = true
     * isEmpty("")        = true
     * isEmpty(" ")       = false
     * isEmpty("bob")     = false
     * isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     *
     * <pre>
     * isNotEmpty(null)      = false
     * isNotEmpty("")        = false
     * isNotEmpty(" ")       = true
     * isNotEmpty("bob")     = true
     * isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }




}
