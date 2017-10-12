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
<#if movies??>
 <h1>Display movies</h1><br>
 <table>
 <tr><td>Name<td><td>Year</td> <td></td></tr>
 <form method="">
 <#list results as movie>
    <tr>
    <td><a href="/kalkidan_teklu_review/search?movie=${movie.id?string.computer}">${movie.name}</a><td>
    <td>${movie.year?string.computer}</td> 
    <td>
    <a id="${movie.id?string.computer}" class="button" href="/kalkidan_teklu_review/update?id=${movie.id?string.computer}" >Update</a>
	</td>
    <td>
    <a id="${movie.id?string.computer}" class="button" href="/kalkidan_teklu_review/delete?id=${movie.id?string.computer}" >Delete</a>
	</td>
    </tr>
  </#list>
  </table>
  </form>
</#if>
<br>
 <form method="get">
  <button type="submit" formaction="/kalkidan_teklu_review/index.html" class="button" name="search">Home</button>
</form>
</body>
</html>