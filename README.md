# sopo

This is a old project developed at about 2005. When I switch from ASP.net to Java.

I like ASP.net at that time, and so try to create a framework which is similiar to ASP.net.

It use component to build a page, it allow developer build custom component easily. 

### Custom component in HTML:

```html
<div id="bf" stype="t:Beanform" ></div>
```
then the div will bind to a custom component type of Beanform. 


### Template and component nest 

a example of a grid component 
```html
<table id="g2lay" stype="s:Table" border="1" cellpadding="0"
	cellspacing="0" style="width: 100%;">
	<tr>
		<td style="width: 100%;">
			<div id="g2nav" stype="t:grid.GridPageNavigate">total...</div><button id="btnQ" stype="s:Button" name="g2btnQ" value="Query" ></button>
			<button id="btnAll" stype="s:Button" value="All" name="g2btnAll" ></button>
		</td>
	</tr>
	<tr>
		<td style="width: 100%;">
			<table id="g2q" stype="s:Table" border="1"
				cellpadding="0" cellspacing="0" style="width: 100%;">
				<tr id="g2qtr" stype="s:Tr"></tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="width: 100%;">
			<table id="g2grd" stype="s:grid.Grid" border="1" cellpadding="0"
				cellspacing="0" style="width: 100%;">				
			</table>
		</td>
	</tr>
</table>
<input type="hidden" id="hid" stype="s:Hidden">
```
it can include other custom component. It is designed to reusing.

## Example

There are a example project under the folder sopo-example

# Set up
- Create a config class extend `com.kazge.sopo.AppConfig`. 
- Set the parameter in `web.xml`
```xml
<context-param>
    <param-name>com.kazge.sopo.Config</param-name>
    <param-value>com.kazge.sopoexample.web.AppConfig</param-value>
</context-param>

<filter>
    <filter-name>sopo</filter-name>
    <filter-class>com.kazge.sopo.SopoFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>sopo</filter-name>
    <url-pattern>*.aspx</url-pattern>
</filter-mapping>
```  
- Implement your custom page and component
- Run.



 