<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>mLaw OpenID Login</title>

    <!-- Simple OpenID Selector -->
    <link rel="stylesheet" href="<c:url value='/css/openid.css'/>" />
    <script type="text/javascript" src="<c:url value='/scripts/jquery-1.2.6.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/openid-jquery.js'/>"></script>

    <script type="text/javascript">
    $(document).ready(function() {
        openid.init('openid_identifier');
     //   openid.setDemoMode(true); Stops form submission for client javascript-only test purposes
    });
    </script>
    <!-- /Simple OpenID Selector -->

    <style type="text/css">
        /* Basic page formatting. */
        body {
            font-family:"Helvetica Neue", Helvetica, Arial, sans-serif;
        }
        #input_identifier {width:250px}
    </style>
</head>

<body>
<div style="width: 580px;">
	<c:if test="${not empty param.login_error}">
		<span style="color: red"> Your login attempt was not
			successful, please try again.<br />
		<br /> Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
		</span>
	</c:if>
	<c:if test="${not empty param.mlaw_openid_activation}">
		<c:choose>
			<c:when
				test="${fn:endsWith(mlaw_openid_activation_message, ':(')}">
				<span style="color: red"> Your login attempt was not
					successful, please try again.<br />
				<br /> Reason: <c:out value="${mlaw_openid_activation_message}" />.
				</span>
			</c:when>
			<c:otherwise>
				<span style="color: green"> <c:out value="${mlaw_openid_activation_message}" />.
				</span>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
	<!-- Simple OpenID Selector -->
<form action="<c:url value='j_spring_openid_security_check'/>" method="post" id="openid_form">
    <input type="hidden" name="action" value="verify" />

    <fieldset>
            <legend>Sign-in</legend>

            <div id="openid_choice">
                <p>Please click your account provider:</p>
                <div id="openid_btns"></div>

            </div>

            <div id="openid_input_area">
                <input id="openid_identifier" name="openid_identifier" type="text" value="http://" />
                <input id="openid_submit" type="submit" value="Sign-In"/>
            </div>
            <noscript>
            <p style="display: none;">OpenID is a service that allows you to log-on to many different websites using a single identity.
            Find out <a href="http://openid.net/what/">more about OpenID</a> and
             <a href="http://openid.net/get/">how to get an OpenID enabled account</a>.</p>
            </noscript>
    </fieldset>
</form>
<!-- /Simple OpenID Selector -->

</body>
</html>
