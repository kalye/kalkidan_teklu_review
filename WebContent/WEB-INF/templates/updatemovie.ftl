<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View the titles of all movies of a selected genre</title>
<style>
table {
    border-collapse: collapse;
}

table, th, td {
    border: 1px solid black;
}
.button {
  background-color: ForestGreen;  
  border-radius: 5px;
  color: white;
  text-decoration: none;
}
</style>
</head>
<body>
<#if error??>
 <div style="display:inline-block; float:left; color: red;">${message}</div><br>
</#if>
  <h1>Update Movie</h1><br>
 <form action="/kalkidan_teklu_review/update?movie=${id?string.computer}" method="post">
  Movie Title:<br>
  <input type="text" name="title" value="${title}">
  <br>
  Year:<br>
  <input type="text" name="year" value="${year?string.computer}">
  <br>
  Rank:<br>
  <input type="text" name="rank" value="${rank}">
  <br><br>
  	<table>
    <#list allGenres as genre>
	    <tr>
	        <td><input id="genres-${genre.id}" type="checkbox" name="genres"
						value="${genre.genre}" ${(genre.checked)?then('checked', '')}/> <label for="genres-${genre.id}">${genre.genre?cap_first}</label></td>
	    </tr>
    </#list>
	</table>
  <input type="submit" value="Update Movie" class="button"/>
</form> 

<br>
 <form method="get">
  <button type="submit" formaction="/kalkidan_teklu_review/index.html" class="button" name="search">Home</button>
</form>
</body>
</html>