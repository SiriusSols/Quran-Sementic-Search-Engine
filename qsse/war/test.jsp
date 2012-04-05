<%@ page import="org.jqurantree.orthography.Document" %>
<%@ page import="org.jqurantree.arabic.ArabicText" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Display Arabic</title>

<style type="text/css">
.arabic{

}

</style>
</head>
<body>


<%
	String s = Document.getVerse(27, 28).toSimpleEncoding(); 
	ArabicText tx =  Document.getChapter(1).getVerse(1).getSubstring(0,5);
	tx.toString();
	
	//Font f = new Font("Simplified Arabic", 0, 24); 
	//JTextArea ta = new JTextArea(s);  ta.setFont(f); 
%>

<p class="arabic"> <%= s %></p>

<p class="arabic"> <%= tx.toString() %></p>

<p class="arabic"> <%= tx.toBuckwalter() %></p>
<p class="arabic"> <%= tx.toSimpleEncoding() %></p>
<p class="arabic"> <%= tx.toUnicode() %></p>

</body>
</html>