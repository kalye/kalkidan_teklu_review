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
 <h1>${movie.name}</h1><br>
 <form>
 <a type="submit" class="button" href="/kalkidan_teklu_review/update?id=${movie.id?string.computer}" id="${movie.id?string.computer}">Update movie</a>
 </form>
  <form method="post" action="/kalkidan_teklu_review/add?reviewMovieId=${movie.id?string.computer}">
  	<input type="text" value="" name="review" /><button type="submit" class="button">Add Review</button>
  </form>
<#if reviews??>
<form method="post">
 <table>
 <tr><td>Reviews<td><td></td><td></td></tr>
 <#list reviews as reviewToDisplay>
    <tr>
	    <td><input type="text" value="${reviewToDisplay.review}" name="review"/><td>
	    <td><button class="button" formaction="/kalkidan_teklu_review/update?movieId=${movie.id?string.computer}&reviewId=${reviewToDisplay.id?string.computer}">update</button></td> 
	      <form method="post">
	    <td>
	    <button class="button" formaction="/kalkidan_teklu_review/delete?movieId=${movie.id?string.computer}&reviewId=${reviewToDisplay.id?string.computer}">Delete</button>
		</td>
    </tr>
  </#list> 
  </table>
    </form>
   </#if>
 
</#if>
<br>
 <form method="get">
  <button type="submit" formaction="/kalkidan_teklu_review/index.html" class="button" name="search">Home</button>
</form>
</body>
</html>