<?php 
require_once 'settings.php';
class Sql {
	private $connect = 0;
	private $driver = 0;
	function __construct(){
		$this->connect = new mysqli(Setting::$DB_HOST, Setting::$DB_USER, Setting::$DB_PASS, Setting::$DB_NAME);

		//Устанавливает драйвер для try cath
		$this->driver = new mysqli_driver();
		$this->driver->report_mode = MYSQLI_REPORT_STRICT | MYSQLI_REPORT_ERROR;
	}

	function getConnector(){
		return $this->connect;
	}


}
?>