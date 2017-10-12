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
  <h1>Add Movie</h1><br>
 <form action="/kalkidan_teklu_review/add?movie" method="post">
  Movie Title:<br>
  <input type="text" name="title" value="">
  <br>
  Year:<br>
  <input type="text" name="year" value="">
  <br>
  Rank:<br>
  <input type="text" name="rank" value="">
  <br><br>
  	<table>
		<tbody>
			<tr>
				<td><input id="genres-1" type="checkbox" name="genres"
					value="action"> <label for="genres-1">Action</label></td>
				<td><input id="genres-2" type="checkbox" name="genres"
					value="adventure"> <label for="genres-2">Adventure</label></td>
				<td><input id="genres-3" type="checkbox" name="genres"
					value="animation"> <label for="genres-3">Animation</label></td>
				<td><input id="genres-4" type="checkbox" name="genres"
					value="biography"> <label for="genres-4">Biography</label></td>
			</tr>
			<tr>
				<td><input id="genres-5" type="checkbox" name="genres"
					value="comedy"> <label for="genres-5">Comedy</label></td>
				<td><input id="genres-6" type="checkbox" name="genres"
					value="crime"> <label for="genres-6">Crime</label></td>
				<td><input id="genres-7" type="checkbox" name="genres"
					value="documentary"> <label for="genres-7">Documentary</label></td>
				<td><input id="genres-8" type="checkbox" name="genres"
					value="drama"> <label for="genres-8">Drama</label></td>
			</tr>
			<tr>
				<td><input id="genres-9" type="checkbox" name="genres"
					value="family"> <label for="genres-9">Family</label></td>
				<td><input id="genres-10" type="checkbox" name="genres"
					value="fantasy"> <label for="genres-10">Fantasy</label></td>
				<td><input id="genres-11" type="checkbox" name="genres"
					value="film_noir"> <label for="genres-11">Film-Noir</label></td>
				<td><input id="genres-12" type="checkbox" name="genres"
					value="game_show"> <label for="genres-12">Game-Show</label></td>
			</tr>
			<tr>
				<td><input id="genres-13" type="checkbox" name="genres"
					value="history"> <label for="genres-13">History</label></td>
				<td><input id="genres-14" type="checkbox" name="genres"
					value="horror"> <label for="genres-14">Horror</label></td>
				<td><input id="genres-15" type="checkbox" name="genres"
					value="music"> <label for="genres-15">Music</label></td>
				<td><input id="genres-16" type="checkbox" name="genres"
					value="musical"> <label for="genres-16">Musical</label></td>
			</tr>
			<tr>
				<td><input id="genres-17" type="checkbox" name="genres"
					value="mystery"> <label for="genres-17">Mystery</label></td>
				<td><input id="genres-18" type="checkbox" name="genres"
					value="news"> <label for="genres-18">News</label></td>
				<td><input id="genres-19" type="checkbox" name="genres"
					value="reality_tv"> <label for="genres-19">Reality-TV</label></td>
				<td><input id="genres-20" type="checkbox" name="genres"
					value="romance"> <label for="genres-20">Romance</label></td>
			</tr>
			<tr>
				<td><input id="genres-21" type="checkbox" name="genres"
					value="sci_fi"> <label for="genres-21">Sci-Fi</label></td>
				<td><input id="genres-22" type="checkbox" name="genres"
					value="sport"> <label for="genres-22">Sport</label></td>
				<td><input id="genres-23" type="checkbox" name="genres"
					value="talk_show"> <label for="genres-23">Talk-Show</label></td>
				<td><input id="genres-24" type="checkbox" name="genres"
					value="thriller"> <label for="genres-24">Thriller</label></td>
			</tr>
			<tr>
				<td><input id="genres-25" type="checkbox" name="genres"
					value="war"> <label for="genres-25">War</label></td>
				<td><input id="genres-26" type="checkbox" name="genres"
					value="western"> <label for="genres-26">Western</label></td>
			</tr>
		</tbody>
	</table>
  <input type="submit" value="Add Movie" class="button"/>
</form> 

<br>
 <form method="get">
  <button type="submit" formaction="/kalkidan_teklu_review/index.html" class="button" name="search">Home</button>
</form>
</body>
</html>