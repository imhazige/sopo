package com.kazge.sopoexample.common;

public class StringUtils extends org.apache.commons.lang.StringUtils
{
	public static String encodeHtml(String content)
    {
		if (isEmpty(content))
		{
			return content;
		}
        int length = content.length();

        StringBuilder builder = null;

        for (int i = 0; i < length; i++)
        {
            char ch = content.charAt(i);

            switch (ch)
            {
                case '<':

                    if (builder == null)
                    {
                        builder = new StringBuilder(2 * length);

                        builder.append(content.substring(0, i));
                    }

                    builder.append("&lt;");
                    continue;

                case '>':

                    if (builder == null)
                    {
                        builder = new StringBuilder(2 * length);

                        builder.append(content.substring(0, i));
                    }

                    builder.append("&gt;");
                    continue;

                case '&':

                    if (builder == null)
                    {
                        builder = new StringBuilder(2 * length);

                        builder.append(content.substring(0, i));
                    }

                    builder.append("&amp;");
                    continue;

                default:

                    if (builder != null)
                        builder.append(ch);
            }
        }

        return builder == null ? content : builder.toString();
    }
}
