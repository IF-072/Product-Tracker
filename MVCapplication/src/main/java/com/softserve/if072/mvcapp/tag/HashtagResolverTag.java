package com.softserve.if072.mvcapp.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The HashtagResolverTag defines behavior of hashtagResolver custom tag.
 * This tag converts all hashtags which tag's body contains into hyperlinks.
 *
 * @author Oleh Pochernin
 */
public class HashtagResolverTag extends SimpleTagSupport {
    private static final String HREF_TEMPLATE = "<a class=\"search\" hashtag=\"%s\" href=#>%s</a>";

    /**
     * This method converts all hashtags which tag's body contains into hyperlinks.
     * It is called once and only once for any given tag invocation.
     *
     * @throws JspException a generic exception known to the JSP engine
     * @throws IOException general class of exceptions produced by failed or interrupted I/O operations
     */
    @Override
    public void doTag() throws JspException, IOException {
        StringWriter sw = new StringWriter();
        getJspBody().invoke(sw);

        String description = sw.toString();

        Pattern pattern = Pattern.compile("#\\S+");
        Matcher matcher = pattern.matcher(description);
        String hashtag;

        while (matcher.find()) {
            hashtag = matcher.group();
            description = description.replace(
                    hashtag, String.format(HREF_TEMPLATE, hashtag, hashtag));
        }

        JspWriter out = getJspContext().getOut();
        out.println(description);
    }
}
