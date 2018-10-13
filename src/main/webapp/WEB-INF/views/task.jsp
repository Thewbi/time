<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
<meta charset="UTF-8">
<title>Time</title>

<!-- 
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
 -->
<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css">
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
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
</head>
<body>
	<a href="<c:url value="/tasks"/>">Back to Tasks</a>

	<h1>Task ${task.name}</h1>
	
	<h2>Add Duration</h2>
	<form:form method="POST" action="/tasks/addDurationToTask" modelAttribute="entry">
		<table>
			<tr>
				<td><form:label path="name">Name</form:label></td>
				<td><form:input path="name" maxlength="12" required="required" /><form:errors path="name" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td><form:label path="description">Description</form:label></td>
				<td><form:textarea path="description" rows="5" cols="30" /></td>
			</tr>
			
			<tr>
				<td><form:label path="start">Start</form:label></td>
				<td>
					
					<div class="container">
					    <div class="row">
				            <div class="form-group">
				                <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
				                    <form:input id="hidden-start" path="start" maxlength="12" required="required" class="form-control datetimepicker-input" data-target="#datetimepicker1"/><form:errors path="name" cssClass="error" />
				                    <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
				                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
				                    </div>
				                </div>
				            </div>
					        <script type="text/javascript">
					            $(function () {
					                $('#datetimepicker1').datetimepicker({
					                    format: 'YYYY-MM-DDTHH:mm:ss.SSS'
					                });
					            });
					        </script>
					    </div>
					</div> 
				</td>
			</tr>
			
			<tr>
				<td><form:label path="end">End</form:label></td>
				<td>
					<div class="container">
					    <div class="row">
				            <div class="form-group">
				                <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
				                    <form:input id="hidden-end" path="end" maxlength="12" required="required" class="form-control datetimepicker-input" data-target="#datetimepicker2"/><form:errors path="name" cssClass="error" />
				                    <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
				                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
				                    </div>
				                </div>
				            </div>
					        <script type="text/javascript">
					            $(function () {
					                $('#datetimepicker2').datetimepicker({
					                    format: 'YYYY-MM-DDTHH:mm:ss.SSS'
					                });
					            });
					        </script>
					    </div>
					</div> 
				</td>
			</tr>
			
			<tr>
				<td><input type="submit" value="Submit" class="btn btn-primary" /></td>
			</tr>
		</table>
		
		<form:hidden path="tempParentId" value="${task.id}" />
		
	</form:form>
	
	<c:set var="errorMsg" value="${errorMsg}" />
	<c:if test="${not empty errorMsg}">
		<div class="alert alert-danger" role="alert">
		  <spring:message code="${errorMsg}"/>
		</div>
	</c:if>
	
	<c:set var="taskCreatedMsg" value="${durationCreatedMsg}" />
	<c:if test="${not empty durationCreatedMsg}">
		<div class="alert alert-success" role="alert">
		  <spring:message code="${durationCreatedMsg}"/>
		</div>
	</c:if>
	
	<h2>Durations in this Task</h2>

	<table class="table">
	<c:forEach var="duration" items="${durations}">
		<tr>
			<td>Duration Name: <a href="<c:url value="/durations/${duration.id}"/>"><c:out value="${duration.parent.parent.name}-${duration.parent.name} ${duration.name}" /></a></td>
			<td>Duration: <c:out value="${duration.durationAsString}" /></td>
			<!-- 
			<td>Duration Name: <a href="<c:url value="/durations/${duration.id}"/>"><c:out value="${duration.name}" /></a></td>
		    -->
			<td>Duration Description: <c:out value="${duration.description}" /></td>
			<td>Duration Start: <fmt:formatDate value="${duration.start}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td>Duration End: <fmt:formatDate value="${duration.end}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td><a href="<c:url value="/durations/delete/${duration.id}"/>">Delete</a></td>
		</tr>
	</c:forEach>
	</table>
 
</body>
</html>