<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div>

    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#langCollapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><spring:message code="productTracker"/></a>
        </div>


        <div class="navbar-collapse in" id="langCollapse" aria-expanded="true">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <spring:message code="changeLanguage"/>
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><span class="lang-sm lang-lbl-full lang-choice" lang="en"></span></li>
                        <li><span class="lang-sm lang-lbl-full lang-choice" lang="uk"></span></li>
                        <li><span class="lang-sm lang-lbl-full lang-choice" lang="ru"></span></li>
                    </ul>
                </li>
            </ul>
        </div>

    </nav>
    <div>