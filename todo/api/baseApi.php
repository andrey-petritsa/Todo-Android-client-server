<?php
require_once 'sql.php'; 
require_once 'apiError.php';

class BaseApi {
	public $sql = 0;
	function __construct(){
		$this->sql = new Sql();
	}

	protected function sendQuery($query){
		$connection = $this->sql->getConnector();
		$result = $connection->query($query);
		return $result;
	}
}
?>