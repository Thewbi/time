<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<title>Time</title>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
</head>
<body>
	<a href="<c:url value="/projects"/>">Back to Projects</a>

	<h1>Tasks</h1>

	<c:set var="errorMsg" value="${errorMsg}" />
	<c:if test="${not empty errorMsg}">
		<div class="alert alert-danger" role="alert">
			<spring:message code="${errorMsg}" />
		</div>
	</c:if>

	<c:set var="taskCreatedMsg" value="${taskCreatedMsg}" />
	<c:if test="${not empty taskCreatedMsg}">
		<div class="alert alert-success" role="alert">
			<spring:message code="${taskCreatedMsg}" />
		</div>
	</c:if>

	<table class="table">
		<c:forEach var="task" items="${tasks}">
			<tr>
				<td>Task Name: <a href="<c:url value="/tasks/${task.id}"/>"><c:out
							value="${task.name}" /></a></td>
				<td>Task Description: <c:out value="${task.description}" /></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>