<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>" />
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>


<div class="metal-background rounded-block center" style="width: 70em;">

    <h2>Правило Кондерсє та Правило Копленда</h2>

    <div style="margin: 20px">
        <a href="/index?tabId=1"><button class="e-btn ${param.tabId == 1? 'chkd-btn' : ''}">Діючі особи</button></a>
        <a href="/index?tabId=2"><button class="e-btn ${param.tabId == 2? 'chkd-btn' : ''}">Альтернативи</button></a>
        <a href="/index?tabId=3"><button class="e-btn ${param.tabId == 3? 'chkd-btn' : ''}">Голосування</button></a>
    </div>

    <div style="display:${param.tabId == 1? 'block' : 'none'}">
        <table style="margin: auto">
            <thead>
                <tr>
                    <th>І'мя ЛПР</th>
                    <th class="tbl_lb">Ранг</th>
                    <th class="tbl_lb">Редагування</th>
                    <th class="tbl_lb">Видалення</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="element" items='${allLps}' varStatus="status">
                    <tr style="text-align:center">
                        <td class="tbl_tb td3" style="text-align: left">
                            ${element.name}
                        </td>
                        <td class="tbl_lb tbl_tb np cell-7">
                            ${element.range}
                        </td>
                        <td class="tbl_lb tbl_tb np cell-7">
                            <a href="/index?tabId=1&edit=${element.num}"><button class="cell-input">Редагувати</button></a>
                        </td>
                        <td class="tbl_lb tbl_tb np cell-7">
                            <button class="cell-input" onclick="deleteLpr(${element.num})">Видалити</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <form action="/tab1/postLpr" method="POST" style="margin-top:20px;">
            <span>Нова/Оновлена ЛПР:</span>
            <input type="text" name="name" value="${editTarget.name}" required/>
            <span>Ранг:</span>
            <input type="number" name="range" min="1" step="1" value="${editTarget.range}" required/>
            <input type="hidden" name="id" value="${editTarget.num}" />
            <input type="submit" value="Зберегти">
        </form>
    </div>




    <div style="display:${param.tabId == 2? 'block' : 'none'}">
        <table style="margin: auto">
            <thead>
                <tr>
                    <th>І'мя альтернативи</th>
                    <th class="tbl_lb">Редагування</th>
                    <th class="tbl_lb">Видалення</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="element" items='${allAlternatives}' varStatus="status">
                    <tr style="text-align:center">
                        <td class="tbl_tb td3" style="text-align: left">
                            ${element.name}
                        </td>
                        <td class="tbl_lb tbl_tb np cell-7">
                            <a href="/index?tabId=2&edit=${element.num}"><button class="cell-input">Редагувати</button></a>
                        </td>
                        <td class="tbl_lb tbl_tb np cell-7">
                            <button class="cell-input" onclick="deleteAlternative(${element.num})">Видалити</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <form action="/tab2/postAlternative" method="POST" style="margin-top:20px;">
            <span>Нова/Оновлена альтернатива:</span>
            <input type="text" name="name" value="${editAlternative.name}" required/>
            <input type="hidden" name="id" value="${editAlternative.num}" required/>
            <input type="submit" value="Зберегти">
        </form>
    </div>



    <div style="display:${param.tabId == 3? 'block' : 'none'}">


       <div style="margin: auto; width: 400px">
           Поточний виборець: <b>${currentLpr.name}</b>
           <form method="POST" action="/tab3/selectLpr" style="margin-top: 10px">
               <select name="newLpr">
                <c:forEach var="element" items='${selectLpr}' varStatus="status">
                   <option value="${element.num}">${element.name}</option>
                </c:forEach>
               </select>
               <input type="submit" value="Обрати" />
           </form>
       </div>

        <div style="margin-bottom:5px">
        Проставте унікальні ранги альтернативам.<br/>
        Однакові ранги та пропуски заборонено.
        </div>

        <c:if test="${param.errorCode eq 1}">
            <div style="margin-bottom:5px; color: red; font-weight:bold">
                Перевірте коректність введених даних
            </div>
        </c:if>
        <form method="POST" action="/tab3/vote" style="margin-top: 10px">
        <table style="margin: auto; width: 400px">
            <thead>
                <tr>
                    <th>Альтернатива</th>
                    <th class="tbl_lb">Ранг</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="element" items='${allAlternatives}' varStatus="statusA">
                    <tr style="text-align:center">
                        <td class="tbl_tb td3" style="text-align: left">
                            ${element.name}
                        </td>
                        <td class="tbl_lb tbl_tb np cell-7" style="text-align: left">
                            <input class="cell-input" name="r${element.num}" type="number" min="1" max="${fn:length(allAlternatives)}" step="1"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <input type="submit" style="margin-top: 5px" value="Вибір зроблено" />
        </form>

        <c:if test="${ranges ne null}" >
        <table style="margin: auto; width: 400px">
            <thead>
                <tr>
                    <th>Правило Кондерсє</th>
                    <th class="tbl_lb">Правило Копленда</th>
                </tr>
            </thead>
            <tbody>
                <tr style="text-align:center">
                    <td class="tbl_tb td3" style="text-align: left">
                        Переможець: <b>${winner1}</b>
                    </td>
                    <td class="tbl_lb tbl_tb np cell-7" style="text-align: left">
                        Переможець: <b>${winner2}</b>
                    </td>
                </tr>
                <tr style="text-align:center">
                    <td colspan="2" class="tbl_tb td3" style="text-align: left">
                    <table style="margin: auto; width: 400px;text-align:center">
                    <tr style="margin:5px;"><td colspan="${fn:length(allAlternatives)}">Ранги за списком альтернатив</td></tr>
                    <c:forEach var="entry" items="${ranges}">
                      <tr>
                      <c:forEach var="element" items='${entry.value}' varStatus="statusA">
                          <td class="tbl_lb tbl_tb np cell-7">${element}</td>
                      </c:forEach>
                      </tr>
                    </c:forEach>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
        </c:if>
    </div>
</div>


<script>
    function deleteLpr(num) {
        postComparisionChange("/tab1/reqDelete",
            JSON.stringify({
              id: num
          })
        );
    }

    function deleteAlternative(num) {
        postComparisionChange("/tab2/reqDelete",
            JSON.stringify({
              id: num
          })
        );
    }

    function postComparisionChange(url, json) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function () {
           location.reload();
        }
        xhr.send(json);
     }
</script>

</body>
</html>
