mysql> 
mysql> CALL GetDoctorWithMostPatientsByMonth(4, 2025);
+-----------+---------------+
| doctor_id | patients_seen |
+-----------+---------------+
|         2 |            31 |
+-----------+---------------+
1 row in set (0.00 sec)

Query OK, 0 rows affected (0.00 sec)

mysql> 