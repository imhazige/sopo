package com.kazge.sopo.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import com.kazge.sopo.SopoException;



public class FileUtils extends org.apache.commons.io.FileUtils
{
	public static BufferedImage readImageFile(String filename)
	{
		try
		{
			return ImageIO.read(new File(filename));
		}
		catch (IOException ex)
		{
			return null;
		}
	}
	
	public static String readPackageResource(String packageResource)
	{
		return readPackageResource(packageResource, null);
	}
	
	public static String readPackageResource(String packageResource, ClassLoader loader)
	{
		return readPackageResource(packageResource, loader ,null);
	}
	
	public static String readPackageResource(String packageResource, ClassLoader loader,Charset charset)
	{		
		if (packageResource == null)
			return null;
		if (null == charset)
		{
			charset = Charset.forName("utf-8");
		}
		InputStream is = null;
		BufferedReader bf = null;
		try
		{
			is = getPackageResourceStream(packageResource, loader);
			if (is == null)
				throw new IOException("load package resource failed. resource file: " + packageResource);
			bf = new BufferedReader(new InputStreamReader(is,charset));
			StringBuilder recordBuf = new StringBuilder();
			String record = null;
			while ((record = bf.readLine()) != null)
			{
				recordBuf.append(record + "\n");
			}
			bf.close();
			is.close();
			return recordBuf.toString();
		}
		catch (Exception e)
		{
			throw new SopoException(e);
		}
		finally
		{
			try
			{
				if (bf != null)
					bf.close();
			}
			catch (IOException e)
			{
			}
			try
			{
				if (is != null)
					is.close();
			}
			catch (IOException e)
			{
			}
		}
	}

	public static byte[] readPackageByteResource(String packageResource)
	{
		try
		{
			return readPackageByteResource(packageResource, null);
		}
		catch (Exception ex)
		{
			return null;
		}
		
	}
	
	public static byte[] readPackageByteResource(String packageResource, ClassLoader loader) throws IOException
	{
		if (packageResource == null)
			return null;
		InputStream is = null;
		try
		{
			// get data length
			is = getPackageResourceStream(packageResource, loader);
			int length = 0;
			while (is.read() != -1)
			{
				length++;
			}
			is.close();
			// read data
			byte[] data = new byte[length];
			is = getPackageResourceStream(packageResource, loader);
			is.read(data);
			is.close();
			return data;
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			if (is != null)
				is.close();
		}
	}
	
	public static String getPackageResourcePath(String packageResource)
	{
		return getPackageResourcePath(packageResource, null);
	}
	
	public static String getPackageResourcePath(String packageResource, ClassLoader loader)
	{
		URL url = getPackageResourceUrl(packageResource, loader);
		if (url != null)
			return url.getFile().replaceAll("%20", " ");
		else
			return null;
	}
	
	public static URL getPackageResourceUrl(String packageResource)
	{
		return getPackageResourceUrl(packageResource, null);
	}
	
	public static URL getPackageResourceUrl(String packageResource, ClassLoader loader)
	{
		if (packageResource == null)
			return null;
		if (packageResource.startsWith("\\") || packageResource.startsWith("/"))
			packageResource = packageResource.substring(1);
		if (packageResource.endsWith("\\") || packageResource.endsWith("/"))
			packageResource = packageResource.substring(0, packageResource.length() - 1);
		if (loader == null)
			loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(packageResource);
		if (url == null)
		{
			loader = FileUtils.class.getClassLoader();
			url = loader.getResource(packageResource);
		}
		return url;
	}
	
	public static InputStream getPackageResourceStream(String packageResource)
	{
		return getPackageResourceStream(packageResource, null);
	}


	public static InputStream getPackageResourceStream(String packageResource, ClassLoader loader)
	{
		if (packageResource == null)
			return null;
		if (packageResource.startsWith("\\") || packageResource.startsWith("/"))
			packageResource = packageResource.substring(1);
		if (packageResource.endsWith("\\") || packageResource.endsWith("/"))
			packageResource = packageResource.substring(0, packageResource.length() - 1);
		if (loader == null)
			loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream(packageResource);
		if (is == null)
		{
			loader = FileUtils.class.getClassLoader();
			is = loader.getResourceAsStream(packageResource);
		}
		return is;
	}
	
	
}
