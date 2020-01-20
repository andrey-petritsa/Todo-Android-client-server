<?php
	//API для авторизации пользователя.
	require_once 'baseApi.php';

	class Auth extends BaseApi {
		function __construct(){
			parent::__construct();
            header('Content-Type: application/json');
		}

		//Проверка совпадения почты и пароля в базе.
		private function checkCredentional($email_adress, $password){
			$query_format = 'SELECT user_id FROM user WHERE email_adress="%s" AND password="%s"';
			$query = sprintf($query_format, $email_adress, $password);
			$result = $this->sendQuery($query);

			$array = array();
			while($row =mysqli_fetch_assoc($result)) { 
        		$array[] = $row;
			}

			if(!$array){	//Если такого пользователяне не нашли
				$error = array('error' => ApiError::$WRONG_EMAIL_OR_PASS); 
				return $error;
			}

			return $array;
		}

		/*
		Метод авторизует пользователя.
		Если у пользователя нет токена, генерирует новый.
		Если токен есть, отправляет в ответ токен.
		*/
		public function login_user($email_adress, $password) {
			$check_user = $this->checkCredentional($email_adress, $password);

            if (array_key_exists("error", $check_user)) {
                    return json_encode($check_user);
            }
			//Если пользователь найден, проверяем, есть ли его токкен в базе.
			else {
				$format = 'SELECT user_id, token FROM user WHERE email_adress="%s" AND password="%s"';
				$query= sprintf($format, $email_adress, $password);
				$result = $this->sendQuery($query);
				$user = mysqli_fetch_assoc($result);
        			
        		if(!$user['token']) { //Если токен небыл найден сгенирируем его и добавим в базу
        			$token = md5(time());
        			$format = 'UPDATE user SET token = "%s" WHERE user_id = "%d"';
        			$query = sprintf($format, $token, $user['user_id']);
        			$result = $this->sendQuery($query);
        			return json_encode(array('token' => $token));
        		}
        		else {	//Если нашли токкен, просто отправим его клиенту в ответ
        			return json_encode(array('token' => $user['token']));
        		}
			}
		}

		//Метод регистрации пользователя
		function register_user($last_name, $first_name, $patronymic_name, $email_adress, $password) {
			$format = 'INSERT INTO user (last_name, first_name, patronymic_name, email_adress, password) VALUES ("%s", "%s", "%s", "%s", "%s")';
			$query = sprintf($format, $last_name, $first_name, $patronymic_name, $email_adress, $password);
			$stmt = $this->sql->getConnector()->prepare($query);
			$stmt->execute();

			if(!$stmt->error){
				return json_encode(array('message' => 'Sucess register'));
			}
			else
			{
				return json_encode(array('message' => $stmt->error));
			}
		}




	}
?>