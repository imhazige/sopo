package com.kazge.sopo.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utils
{
	public static Object deepClone(Object orig){
		Object obj = null;
        try {
            // Write the object out to a byte array
            FastByteArrayOutputStream fbos =
                    new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in =
                new ObjectInputStream(fbos.getInputStream());
            obj = in.readObject();
        }
        catch(Exception e) {
        	throw new RuntimeException(e);
        }
        
        return obj;
	}	
}
