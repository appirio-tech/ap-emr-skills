sqoop-1.4.6.bin__hadoop-2.0.4-alpha/bin/sqoop list-tables\
--driver com.informix.jdbc.IfxDriver \
--connect "jdbc:informix-sqli://prod.dw01.topcoder.com:2020/informixoltp:INFORMIXSERVER=informixoltp_tcp;" --username coder --password Qn8TZxFFD77tzQwc \
--query 'SELECT \
 project_result.user_id userId, \
 coder.handle handle, \
 project_result.project_id challengeId, \
 project.phase_desc challengeType, \
 project_technology.name \
FROM \
 tcs_dw:project_result
 INNER JOIN tcs_dw:project as project
    ON project.project_id = project_result.project_id
 INNER JOIN topcoder_dw:coder as coder
    ON project_result.user_id = coder.coder_id
 INNER JOIN tcs_dw:project_technology 
    ON project_technology.project_id = project.project_id'
--boundary-query 'select min(coder.coder_id), max(coder.coder_id) from topcoder_dw:coder as coder'  
--target-dir challengeSkills





sqoop-1.4.6.bin__hadoop-2.0.4-alpha/bin/sqoop list-tables \
--driver com.informix.jdbc.IfxDriver \
--connect "jdbc:informix-sqli://localhost:8888/topcoder_dw:INFORMIXSERVER=datawarehouse_tcp;IFX_LOCK_MODE_WAIT=5;OPTCOMPIND=0;STMT_CACHE=1;" --username coder --password Qn8TZxFFD77tzQwc \
