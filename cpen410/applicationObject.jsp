<%@ page import="java.lang.*"%>
<%@ page import="ut.JAR.CPEN410.*"%>

<html>
<head>
<title>A JSP to count the number of visitors</title>
</head>
<body>
<%
	//If the attribute counter does not exist, create it and initialize it!
	if (application.getAttribute("counter")==null ){%>
	This is the first visitor!
		<%application.setAttribute("counter",1);	}
	else {%>
	You are the visitor number 
			<%
				//If the attribute already exist, update it!
			try{
				int val;
				val=(new Integer(application.getAttribute("counter").toString())).intValue();
				val++;
				application.setAttribute("counter",val);	}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
	%>
	Value
	<%=application.getAttribute("counter")%>
	<%}%>
</body>
</html>

