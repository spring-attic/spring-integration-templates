/** Contains custom scripts */

var tweetTemplate;

$.PeriodicalUpdater(tweetsUrl, {
	method : 'get',
	data : function() {
		return {latestTweetId: window.latestTweetId};
	},
	minTimeout : 5000,
	maxTimeout : 20000,
	multiplier : 2,
	type : 'json',
	maxCalls : 0,
	autoStop : 0
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

		console.log('Adapter Running: ' + adapterRunning + '; Latest Tweet Id ' + window.latestTweetId + '; Number of Tweets ' + remoteData.twitterMessages.length);

		if (remoteData.twitterMessages.length > 0) {
			var context = {
				tweets : remoteData.twitterMessages
			};
			var html = tweetTemplate(context).trim();
			$(html).prependTo("#tweets");
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

	$("body").on('click',".delete",function(event) {
		$(this).closest(".row").slideUp("normal", function() {
			$(this).remove();

			if ($("#tweets div.row").length === 0) {
				$('#tweets').spin(true);
			}
		});
	});
});

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
