$(document).ready(function(){

	$("#load").click(function() {
		parseMessages("358751");
	});

//http://www.sportinglife.com/football/match/live-commentary/358751/all/

	function addOutput(mins, text, extra) {
		$("#output").append(mins + ": " + text + "&nbsp;&nbsp;<b>" + extra + "</b><br>");
	}

	function parseText(text) {

		//console.log(text, text.includes("Attempt missed"));
		if (text.includes("Attempt missed")) {
			return "missed";
		} else if (text.includes("Attempt blocked")) {
			return "blocked";
		} else if (text.includes("Attempt saved")) {
			return "saved";
		} else if (text.includes("Corner")) {
			return "corner";
		} else if (text.includes("Foul")) {
			return "foul";
		} else if (text.includes("yellow card")) {
			return "foul yellow";
		}
		return "<i>other</i>";
	}


	function parseMessages(urlId) {
		console.log("updating travel for",urlId);
		$.ajax({url: "assets/load.php?id=" + urlId, dataType:"json", success: function(result){
			//console.log(result);

			var lines = result.line;
			lines = lines.reverse();

			console.log(lines);

			for (var i = 0; i < lines.length; i++) {
				var line = lines[i];
				console.log(line);

				if (!line.text.includes("Gillingham")) {
					continue;
				}
				var text = parseText(line.text);

				addOutput(line.minutes, line.text, text);
			}


		}});
	}
});