<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<body>
<div id="all" style="width: 800; margin: 0 auto;">
    <h1 style="text-align: center">服务号：${robotAnswer.serviceId}${serviceId}</h1>

    <div id="talk" style="width: 50%; float: left">

        <form action="/robot/talk" method="post" enctype="multipart/form-data">
            <input type="hidden" name="serviceId" value="10000">
            <br/>
            <br/>
            回复内容：<br/>
            <input type="hidden" name="discussion" value="${discussion}">
            <textarea disabled="true" rows="10" cols="35"
                      onpropertychange="this.scrollTop=this.scrollHeight"
                      onfocus="this.scrollTop=this.scrollHeight"
                      readonly="readonly">${discussion}</textarea>
            <br/>

            <br/>
            说话:<input type="text" name="talk" value="${talk}"/>
            <input type="submit" name="send" value="发送"/>
        </form>
    </div>
    <div id="learn" style="width: 50%; float: right">
        <form action="/robot/addAnswer" method="post" enctype="multipart/form-data">

            <input id="serviceId" type="hidden" name="serviceId" value="10000"/>
            <br/>
            <br/>
            <label for="keyword">关键字：</label>
            <input id="keyword" type="text" name="keyword" value="${robotAnswer.keyWord}"/>
            <br/>
            <br/>
            <label for="answer">答案：</label>
            <input id="answer" type="text" name="answer" value="${robotAnswer.answer}"/>
            <br/>
            <br/>
            <label>是否覆盖原有回复：</label>
            <input type="hidden" name="_overwrite" value="${overwrite}"/>
            <input id="overwrite" type="checkbox" name="overwrite"  value="true">
            <%--<form:checkbox name="overwrite" path="${overwrite}" />--%>
            <br/>
            <br/>
                <input type="submit" name="add" value="添加"/>
                <input type="submit" formaction="/robot/removeAnswer" value="删除"/>
            <br/>
        </form>

    </div>
</div>
</body>