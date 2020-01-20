<?php

$mysqli = new mysqli('127.0.0.1:3306', 'todo', 'pass', 'ToDo');

if ($mysqli->connect_errno) {
	echo "Ошибка: Не удалась создать соединение с базой MySQL и вот почему: \n";
    echo "Номер ошибки: " . $mysqli->connect_errno . "\n";
    echo "Ошибка: " . $mysqli->connect_error . "\n";
    exit;
	}

$sql = "SELECT * FROM user";	
$result = $mysqli->query($sql);

$user = $result->fetch_assoc();

echo json_encode($user);

?>
 

