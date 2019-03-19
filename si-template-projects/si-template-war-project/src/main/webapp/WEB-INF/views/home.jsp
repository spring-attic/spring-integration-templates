<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Spring Integration Template</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Le styles -->
		<link href="<c:url value='/wro/g1.css'/>" rel="stylesheet">

		<!-- Le fav and touch icons -->
		<link rel="shortcut icon" href="<c:url value='/assets/ico/favicon.ico'/>">
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="https://raw.github.com/SpringSource/spring-integration-templates/master/si-sts-templates/builds/1.0.0.RELEASE/ico/apple-touch-icon-144-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="https://raw.github.com/SpringSource/spring-integration-templates/master/si-sts-templates/builds/1.0.0.RELEASE/ico/apple-touch-icon-114-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="72x72"   href="https://raw.github.com/SpringSource/spring-integration-templates/master/si-sts-templates/builds/1.0.0.RELEASE/ico/apple-touch-icon-72-precomposed.png">
		<link rel="apple-touch-icon-precomposed"                 href="https://raw.github.com/SpringSource/spring-integration-templates/master/si-sts-templates/builds/1.0.0.RELEASE/ico/apple-touch-icon-57-precomposed.png">
	</head>

	<body>

		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					<a class="brand" href="#">Twitter Template</a>
					<div class="nav-collapse">
						<ul class="nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="#aboutModal" data-toggle="modal">About</a></li>
							<li class="dropdown" id="menu-resources">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#menu-resources">
									Resources
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="https://github.com/SpringSource/spring-integration"><i class="icon-home"></i> Spring Integration</a></li>
									<li><a href="https://github.com/SpringSource/spring-integration-samples"><i class="icon-share"></i> Samples</a></li>
									<li><a href="https://forum.springsource.org/forumdisplay.php?42-Integration"><i class="icon-share"></i> Forums</a></li>
								</ul>
							</li>
						</ul>
					</div><!--/.nav-collapse -->
				</div>
			</div>
		</div>

		<div class="container">
			<div class="page-header">
				<h1>Spring Integration <small>Twitter Template</small></h1>
			</div>

			<div class="row">
				<div class="span8" id="tweets">
					<c:choose>
						<c:when test="${not empty tweets.twitterMessages}">
							<c:forEach items="${tweets.twitterMessages}" var="tweet">
								<div id="${tweet.id}" class="row" style="margin-bottom: 5px;">
									<div class="span1">
										<img alt="${tweet.fromUser}"
											title="${tweet.fromUser}"
											src="${tweet.profileImageUrl}"
											width="48" height="48"/>
									</div>
									<div class="span6">
										<p><c:out value="${tweet.text}"/></p>
										<p><small><c:out value="${tweet.createdAtISO}"/></small></p>
									</div>
									<div class="span1 delete">
										<button class="close">&times;</button>
									</div>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
					</c:choose>
				</div>
				<div class="span2">
					<div>
						Twitter Adapter
					</div>
					<button id="startTwitterAdapter" class="btn btn-success disabled"><i class="icon-play"></i></button>
					<button id="stopTwitterAdapter"  class="btn btn-warning disabled"><i class="icon-stop"></i></button>
				</div>
			</div>

		</div> <!-- /container -->

		<div class="navbar navbar-fixed-bottom">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="https://www.springsource.org/"><img alt="SpringSource"
							title="SpringSource" src="https://raw.github.com/SpringSource/spring-integration-templates/master/si-sts-templates/builds/1.0.0.RELEASE/img/spring/spring-logo.png"></a>
				</div>
			</div>
		</div>

		<div id="aboutModal" class="modal" style="display: none;">
			<div class="modal-header">
				<button class="close" data-dismiss="modal" type="button">×</button>
				<h3>About...</h3>
			</div>
			<div class="modal-body">
				<h4>About this Tempplate</h4>
				<p>
					This UI is part of the WAR deployable Spring Integration
					Template.
				</p>
				<h4>About Spring Integration</h4>
				<p>
					To learn more about Spring Integration, please visit:
					<a href="https://www.springsource.org/spring-integration/">
						https://www.springsource.org/spring-integration/
					</a>
				</p>
				<hr>
			</div>
			<div class="modal-footer">
				<a class="btn" data-dismiss="modal" href="#">Close</a>
			</div>
		</div>

		<!-- Le javascript
		================================================== -->

		<script type="text/javascript">

			var startStopUrl = '<c:url value="/adapter/"/>';
			var latestTweetId = '0';
			var tweetsUrl    = '<c:url value="/tweets.json?sortOrder=DESCENDING"/>';
		</script>

		<script src="<c:url value='/wro/g1.js'/>"></script>

<%-- 		<script src="<c:url value='/assets/js/jquery.js'/>"></script>
		<script src="<c:url value='/assets/js/bootstrap.js'/>"></script>
		<script src="<c:url value='/assets/js/jquery.periodicalupdater.js'/>"></script>
		<script src="<c:url value='/assets/js/handlebars.js'/>"></script>
		<script src="<c:url value='/assets/js/spin.js'/>"></script>
		<script src="<c:url value='/assets/js/custom.js'/>"></script> --%>

		<script type="text/javascript">

			$(document).ready(function() {

				<c:if test="${empty tweets.twitterMessages}">
					$("#tweets").spin();
				</c:if>
				$("#startTwitterAdapter").click(function (){
					jQuery.ajax(startStopUrl + "start");
					$("#startTwitterAdapter").addClass("disabled");
					$("#stopTwitterAdapter").addClass("disabled");
				});
				$("#stopTwitterAdapter").click(function (){
					jQuery.ajax(startStopUrl + "stop");
					$("#startTwitterAdapter").addClass("disabled");
					$("#stopTwitterAdapter").addClass("disabled");
				});

			});
		</script>

		<script id="tweet-template" type="text/x-handlebars-template">
			{{#each tweets}}
				{{#with this}}
					<div id="{{id}}" class="row" style="margin-bottom: 5px;">
						<div class="span1">
							<img alt="{{fromUser}}"
								title="{{fromUser}}"
								src="{{profileImageUrl}}"
								width="48" height="48"/>
						</div>
						<div class="span6">
							<p>{{text}}</p>
							<p><small>{{createdAtISO}}</small></p>
						</div>
						<div class="span1 delete">
							<button class="close">&times;</button>
						</div>
					</div>
				{{/with}}
			{{/each}}
		</script>
	</body>
</html>
