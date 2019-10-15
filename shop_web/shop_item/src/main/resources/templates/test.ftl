<html>
    ${key}

    <hr/>
    <#if age < 18>
        未成年
        <#elseif age < 40>
        成年
        <#elseif age < 60>
        中年
        <#else >
        老年
    </#if>
    <hr/>
    <#list likes as like>
        ${like}
    </#list>
    <hr/>
    ${now?date}  ${now?time}  ${now?datetime}  ${now?string('yyyy年MM月')}
    <hr/>
    ${money?string("￥#,###.##")}
</html>