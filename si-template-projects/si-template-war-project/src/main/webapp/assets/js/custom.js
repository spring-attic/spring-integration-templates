/** Contains custom scripts */

var tweetTemplate;

$.PeriodicalUpdater(tweetsUrl, {
	method : 'get', // method; get or post
	data : function() {
		return {latestTweetId: window.latestTweetId};
	}, // array of values to be passed to the page - e.g. {name: "John", greeting: "hello"}
	minTimeout : 5000, // starting value for the timeout in milliseconds
	maxTimeout : 20000, // maximum length of time between requests
	multiplier : 2, // the amount to expand the timeout by if the response hasn't changed (up to maxTimeout)
	type : 'json', // response type - text, xml, json, etc. See $.ajax config options
	maxCalls : 0, // maximum number of calls. 0 = no limit.
	autoStop : 0
// automatically stop requests after this many returns of the same data. 0 = disabled.
}, function(remoteData, success, xhr, handle) {

	if ("success" == success) {
		latestTweetId = remoteData.latestTweetIdAsString;
		var adapterRunning = remoteData.adapterRunning;

		if (adapterRunning) {
			$("#stopTwitterAdapter").removeClass("disabled");
			$("#startTwitterAdapter").addClass("disabled");
		} else {
			$("#startTwitterAdapter").removeClass("disabled");
			$("#stopTwitterAdapter").addClass("disabled");
		}

		console.log('Adapter Running: ' + adapterRunning + '; Latest Tweet Id' + window.latestTweetId);

		var context = {
			tweets : remoteData.twitterMessages
		};
		var html = tweetTemplate(context);

		if (latestTweetId > 0) {
			$(html).hide().prependTo("#tweets").fadeIn("slow");
			$('#tweets').spin(false);
		}
	}

});

$(document).ready(function() {

	Handlebars.registerHelper('link', function(url) {
		url = Handlebars.Utils.escapeExpression(url);
		console.log(url);
		return url;
	});

	var source = $("#tweet-template").html();
	tweetTemplate = Handlebars.compile(source);

	$(".delete").live('click', function(event) {
		$(this).closest(".row").slideUp("normal", function() {
			$(this).remove();
		});
	});

});

/*

You can now create a spinner using any of the variants below:

$("#el").spin(); // Produces default Spinner using the text color of #el.
$("#el").spin("small"); // Produces a 'small' Spinner using the text color of #el.
$("#el").spin("large", "white"); // Produces a 'large' Spinner in white (or any valid CSS color).
$("#el").spin({ ... }); // Produces a Spinner using your custom settings.

$("#el").spin(false); // Kills the spinner.

 */
(function($) {

	$.fn.spin = function(opts, color) {
		var presets = {
			"tiny" : {
				lines : 8,
				length : 2,
				width : 2,
				radius : 3
			},
			"small" : {
				lines : 8,
				length : 4,
				width : 3,
				radius : 5
			},
			"large" : {
				lines : 10,
				length : 8,
				width : 4,
				radius : 8
			}
		};
		if (Spinner) {
			return this.each(function() {
				var $this = $(this), data = $this.data();

				if (data.spinner) {
					data.spinner.stop();
					delete data.spinner;
				}
				if (opts !== false) {
					if (typeof opts === "string") {
						if (opts in presets) {
							opts = presets[opts];
						} else {
							opts = {};
						}
						if (color) {
							opts.color = color;
						}
					}
					data.spinner = new Spinner($.extend({
						color : $this.css('color')
					}, opts)).spin(this);
				}
			});
		} else {
			throw "Spinner class not available.";
		}
	};
})(jQuery);
