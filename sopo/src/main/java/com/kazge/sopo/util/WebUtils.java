package com.kazge.sopo.util;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class WebUtils
{
	/**
	 * build a url with the given absolute path in the application  
	 * @param argRequest
	 * @param argAbsolutePath this path must begin with the "/" which means the context root
	 * @return String  
	 * @author zhouka Apr 1, 2008
	 * @see
	 */
	public static String buildUrl(HttpServletRequest argRequest, String argAbsolutePath)
	{
		String strHost = argRequest.getHeader("host");
		String strContext = argRequest.getContextPath();
		if (!argAbsolutePath.startsWith("/"))
		{
			argAbsolutePath = "/" + argAbsolutePath;
		}
		return String.format("http://%s%s%s",  strHost, strContext, argAbsolutePath );
	}
	public static String buildContextUrl(HttpServletRequest argRequest, String argAbsolutePath)
	{
		String strContext = argRequest.getContextPath();
		if (!argAbsolutePath.startsWith("/"))
		{
			argAbsolutePath = "/" + argAbsolutePath;
		}
		return strContext + argAbsolutePath;
	}
	public static String innerTrim(String orgin){
		if (null == orgin){
			return null;
		}
		
		String [] ss = orgin.split(" ");
		String newstr = "";
		for(int i = 0 ; i < ss.length ; i++){
			if ("".equalsIgnoreCase(ss[i])){
				continue;
			}
			newstr += ss[i] + " ";			
		}
		
		return newstr.trim();
	}
	
	public static boolean existsPath(HttpServletRequest req,String path)
	{
		if (null == path)
		{
			return false;
		}
		if (path.startsWith("/"))
		{
			path = req.getSession().getServletContext().getRealPath(path);			
		}
		
		return new File(path).exists();
	}	
	
	public static String getServletPath(HttpServletRequest httpServletRequest)
	{
		String servletPath;
				
		if (!StringUtils.isBlank(httpServletRequest.getServletPath()))
		{
			servletPath = httpServletRequest.getServletPath();
			
			return servletPath;
		}
		
		servletPath = httpServletRequest.getRequestURI();
		int i0 = servletPath.indexOf(httpServletRequest.getContextPath());
		servletPath = servletPath.substring(i0);
		
		return servletPath;
	}
		
	@SuppressWarnings("unchecked")
	public static String printParams(HttpServletRequest request)
	{
		Enumeration en = request.getParameterNames();
		StringBuilder builder = new StringBuilder();
		while(en.hasMoreElements())
		{
			String key = (String)en.nextElement();
			String value = request.getParameter(key);
			builder.append(String.format("%s=%s\n",key,value));
		}
		
		return builder.toString();
	}
	
	public static String encodeHtml(String content)
    {
		if (StringUtils.isEmpty(content))
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
	
	public static String exceptionStatck2String(Throwable e)
	{
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		
		return sw.toString();
	}

	public static String encodeText(String content)
	{
		if (StringUtils.isEmpty(content))
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
                    
                case ' ':

                    if (builder == null)
                    {
                        builder = new StringBuilder(2 * length);

                        builder.append(content.substring(0, i));
                    }

                    builder.append("&nbsp;");
                    continue; 
                    
                    
                default:

                    if (builder != null)
                        builder.append(ch);
            }
        }

        return builder == null ? content : builder.toString();
	}
}
