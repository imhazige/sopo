package com.kazge.sopo;


public interface MarkupWriter
{
	void start(String tag,Object... args);
	
	void end();
	
    void write(String text);

    void writef(String format, Object... args);
    
    void writeRaw(String text);
    
    void writeRawf(String format, Object... args);

    void comment(String text);

    void cdata(String content);
    
}
