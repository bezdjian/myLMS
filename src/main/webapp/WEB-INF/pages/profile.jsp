<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="includes/header.jsp"%>
<div id="container" class="container-fluid">
    <!--div id="header" class="header-panel">
        <div id="header-content" style="border: 1px solid #efefef">
            <div id="logo">
                <img src="<c:url value="/resources/img/logo.jpg"/>" />
            </div>
        </div>
    </div-->

    <div id="page" class="panel panel-default">
        <div id="page-content" class="panel-body">

            <c:if test="${errormsg != null || errormsg != ''}">
                <div id="error-dialog-error-label">
                    <p>${errormsg}</p>
                </div>
            </c:if>

            <div class="row">
                <div class="col-sm-9">
                    <div class="panel panel-default">
                        <!--div class="panel-heading">
                            <div id="welcome-panel" class="welcome-panelsss">
                                <h4>${person.firstName} ${person.lastName}</h4>
                            </div>
                        </div-->

                        <form:form action="${contextPath}/editprofile/${persontoview.id}/preedit" modelAttribute="persontoview" enctype="multipart/form-data">
                            <div class="panel-body">
                                <div class="container-fluid userprofile">

                                    <div class="image-div">
                                        <div class="form-group">
                                            <c:choose>
                                                <c:when test="${persontoview.profileImage == null}">
                                                    <img src="${contextPath}/resources/images/no-user.png" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${contextPath}/resources/profile_pictures/${persontoview.profileImage}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="user-profile-div">
                                        <div class="form-group">
                                            <label class="col-2 col-form-label">
                                                <i class="fa fa-user" aria-hidden="true"></i> Username:</label>
                                            <span>${persontoview.username}</span>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-2 col-form-label">
                                                <i class="fa fa-user" aria-hidden="true"></i> First name:</label>
                                            <span for="person_fname">${persontoview.firstName}</span>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-2 col-form-label">
                                                <i class="fa fa-user" aria-hidden="true"></i> Last name:</label>
                                            <span for="person_fname">${persontoview.lastName}</span>
                                        </div>

                                        <div class="form-group">
                                            <label><i class="icon-globe fa fa-globe"></i> Country:</label>
                                            <span>${persontoview.country}</span>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-2 col-form-label">
                                                <i class="fa fa-envelope" aria-hidden="true"></i> Email:</label>
                                            <span for="person_email">${persontoview.email}</span>
                                        </div>
                                    </div>

                                    <hr>

                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fa fa-edit">
                                                <label>Edit</label>
                                            </i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>

                <!-- Right side block -->
                <%@include file="includes/blocks.jsp"%>
            </div>
        </div>
    </div>
</div>
    <%@include file="includes/footer.jsp"%>