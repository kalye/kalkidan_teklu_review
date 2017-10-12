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
 <div class="label">
    <h3>Genres</h3>
  </div>
  <form action="" method="get">
	<table>
		<tbody>
			<tr>
				<td><input id="genres-1" type="checkbox" name="genres"
					value="Action"> <label for="genres-1">Action</label></td>
				<td><input id="genres-2" type="checkbox" name="genres"
					value="Adventure"> <label for="genres-2">Adventure</label></td>
				<td><input id="genres-3" type="checkbox" name="genres"
					value="Animation"> <label for="genres-3">Animation</label></td>
				<td><input id="genres-4" type="checkbox" name="genres"
					value="Biography"> <label for="genres-4">Biography</label></td>
			</tr>
			<tr>
				<td><input id="genres-5" type="checkbox" name="genres"
					value="Comedy"> <label for="genres-5">Comedy</label></td>
				<td><input id="genres-6" type="checkbox" name="genres"
					value="Crime"> <label for="genres-6">Crime</label></td>
				<td><input id="genres-7" type="checkbox" name="genres"
					value="Documentary"> <label for="genres-7">Documentary</label></td>
				<td><input id="genres-8" type="checkbox" name="genres"
					value="Drama"> <label for="genres-8">Drama</label></td>
			</tr>
			<tr>
				<td><input id="genres-9" type="checkbox" name="genres"
					value="Family"> <label for="genres-9">Family</label></td>
				<td><input id="genres-10" type="checkbox" name="genres"
					value="Fantasy"> <label for="genres-10">Fantasy</label></td>
				<td><input id="genres-11" type="checkbox" name="genres"
					value="Film_Noir"> <label for="genres-11">Film-Noir</label></td>
				<td><input id="genres-12" type="checkbox" name="genres"
					value="Game_Show"> <label for="genres-12">Game-Show</label></td>
			</tr>
			<tr>
				<td><input id="genres-13" type="checkbox" name="genres"
					value="History"> <label for="genres-13">History</label></td>
				<td><input id="genres-14" type="checkbox" name="genres"
					value="Horror"> <label for="genres-14">Horror</label></td>
				<td><input id="genres-15" type="checkbox" name="genres"
					value="Music"> <label for="genres-15">Music</label></td>
				<td><input id="genres-16" type="checkbox" name="genres"
					value="Musical"> <label for="genres-16">Musical</label></td>
			</tr>
			<tr>
				<td><input id="genres-17" type="checkbox" name="genres"
					value="Mystery"> <label for="genres-17">Mystery</label></td>
				<td><input id="genres-18" type="checkbox" name="genres"
					value="News"> <label for="genres-18">News</label></td>
				<td><input id="genres-19" type="checkbox" name="genres"
					value="Reality_Tv"> <label for="genres-19">Reality-TV</label></td>
				<td><input id="genres-20" type="checkbox" name="genres"
					value="Romance"> <label for="genres-20">Romance</label></td>
			</tr>
			<tr>
				<td><input id="genres-21" type="checkbox" name="genres"
					value="Sci_Fi"> <label for="genres-21">Sci-Fi</label></td>
				<td><input id="genres-22" type="checkbox" name="genres"
					value="Sport"> <label for="genres-22">Sport</label></td>
				<td><input id="genres-23" type="checkbox" name="genres"
					value="Talk_Show"> <label for="genres-23">Talk-Show</label></td>
				<td><input id="genres-24" type="checkbox" name="genres"
					value="Thriller"> <label for="genres-24">Thriller</label></td>
			</tr>
			<tr>
				<td><input id="genres-25" type="checkbox" name="genres"
					value="War"> <label for="genres-25">War</label></td>
				<td><input id="genres-26" type="checkbox" name="genres"
					value="Western"> <label for="genres-26">Western</label></td>
			</tr>
		</tbody>
	</table>
	<button type="submit" class="button" formaction="/kalkidan_teklu_review/search" name="titles">View the titles for movie genres</button>
	</form>
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