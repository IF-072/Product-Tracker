package com.softserve.if072.mvcapp.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashtagResolverTag extends SimpleTagSupport {
    private static final String HREF_TEMPLATE = "<a href=%s>%s</a>";

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
                    hashtag, String.format(HREF_TEMPLATE, "#", hashtag));
        }

        JspWriter out = getJspContext().getOut();
        out.println(description);
    }
}
