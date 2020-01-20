<?php
	//Главный API программы. Отвечают за работы с задачами.
	require_once 'baseApi.php';
	class Task extends BaseApi{
		function __construct(){
			parent::__construct();
            header('Content-Type: application/json');
		}

		//Метод возвращает все задачи пользователя по его токену.
		function getTask($user_token){
			try {
				$connector = $this->sql->getConnector();
				$find_user_by_token = $connector->prepare('SELECT user_id FROM user WHERE token = ?');
				$find_user_by_token->bind_param("s", $user_token);
				$find_user_by_token->execute();
				$find_user_by_token->bind_result($user_id);
				$find_user_by_token->fetch();
				$find_user_by_token->close();

				if($user_id) {
					$get_user_tasks = $connector->prepare('SELECT * FROM task WHERE user_id = ?');
					$get_user_tasks->bind_param("i", $user_id);
					$get_user_tasks->execute();
					$result = $get_user_tasks->get_result();

					while($row = mysqli_fetch_assoc($result)) {
						$user_tasks[] = $row;
					}
					$get_user_tasks->close();

					//В user_tasks все задачи пользователя. Выберем в них всю работу.
					foreach ($user_tasks as &$task) {
						$get_works = $connector->prepare('SELECT * FROM work WHERE task_id = ?');
						$get_works->bind_param("i", $task['task_id']);
						$get_works->execute();
						$result = $get_works->get_result();

						$user_works = array();
						while($row = mysqli_fetch_assoc($result)) {
							$user_works[] = $row;
						}
						$task['works'] = $user_works;
						$get_works->close();
					}
					
					header('Content-Type: application/json');
					return json_encode($user_tasks);
				}
				else {	//Если не нашли пользователя по токену
					return json_encode(array('error' => ApiError::$WRONG_TOKEN));
				}
			}
			catch (mysqli_sql_exception $e) {
				return $e->__toString();
			}

			return json_encode(array('error' => 'Error in method getTask'));
		}

		function doneTask($task_id) {
			try {
	            $connector = $this->sql->getConnector();
	            $done_task_request = $connector->prepare('UPDATE task SET done=1 WHERE task_id=?');
	            $done_task_request->bind_param('i', $task_id);
	            $done_task_request->execute();
	            $done_task_request->close();
	        } catch (Exception $e) {
  				echo $e->errorMessage();
  			} 

        }

        function changeName($task_id, $new_name) {
        	try {
	            $connector = $this->sql->getConnector();
	            $done_task_request = $connector->prepare('UPDATE task SET task_name=? WHERE task_id=?');
	            $done_task_request->bind_param('si', $new_name, $task_id);
	            $done_task_request->execute();
	            $done_task_request->close();
	        } catch (Exception $e) {
	        	echo $e->errorMessage();
	        }
        }

        function changePriority($task_id, $new_priority) {
        	try {
	            $connector = $this->sql->getConnector();
	            $done_task_request = $connector->prepare('UPDATE task SET priority=? WHERE task_id=?');
	            $done_task_request->bind_param('ii', $new_priority, $task_id);
	            $done_task_request->execute();
	            $done_task_request->close();
	        } catch (Exception $e) {
	        	echo $e->errorMessage();
	        }
        }

        //Првоеряет, является ли пользователь владельцем задачи
        function isUserHaveTask($user_token, $task_id) {
            $connector = $this->sql->getConnector();
            //Получаем id пользователя по токкену, который запрашивает завершить задачу
            $get_user_id_request = $connector->prepare('SELECT user_id FROM user WHERE token = ?');
            $get_user_id_request->bind_param("s", $user_token);
            $get_user_id_request->execute();
            $user_id = $get_user_id_request->get_result()->fetch_assoc()['user_id'];
            $get_user_id_request->close();
            //Проверяем, есть ли у ползователя запрашиваемая задача
            $get_task_request = $connector->prepare('SELECT task_id FROM task WHERE task_id = ? AND user_id = ?');
            $get_task_request->bind_param("ii", $task_id, $user_id);
            $get_task_request->execute();
            $task_id = $get_task_request->get_result()->fetch_assoc()["task_id"];
            $get_task_request->close();

            if($task_id) {
                return true;
            }
            else {
                return false;
            }
        }

        function isUserHaveWork($user_token, $work_id) {
        	$connector = $this->sql->getConnector();
        	//Получаем id пользователя по токкену, который запрашивает завершить задачу
            $get_user_id_request = $connector->prepare('SELECT user_id FROM user WHERE token = ?');
            $get_user_id_request->bind_param("s", $user_token);
            $get_user_id_request->execute();
            $user_id = $get_user_id_request->get_result()->fetch_assoc()['user_id'];
            $get_user_id_request->close();

            $get_work_request = $connector->prepare('SELECT work_id FROM work WHERE work_id = ? AND user_id = ?');
            $get_work_request->bind_param("ii", $work_id, $user_id);
            $get_work_request->execute();
            $work_id = $get_work_request->get_result()->fetch_assoc()["work_id"];
            $get_work_request->close();

            if($work_id) {
                return true;
            }
            else {
                return false;
            }
        }

        function doneWork($work_id) {
        	try {
	            $connector = $this->sql->getConnector();
	            $done_work_request = $connector->prepare('UPDATE work SET done=1 WHERE work_id=?');
	            $done_work_request->bind_param('i', $work_id);
	            $done_work_request->execute();
	            $done_work_request->close();
	        } catch (Exception $e) {
	        	echo $e->errorMessage();
	        }
        }
        /*
        function changeWorkPriority($work_id, $new_priority) {
        	$connector = $this->sql->getConnector();
        	$changePriority = $connector->prepare("UPDATE work SET priority=? WHERE work_id=?");
        	$changePriority->bind_param('ii', $priority, $work_id);
        	$changePriority->execute();
        	$changePriority->close();
        }*/

        function changeWorkName($work_id, $new_name) {
        	$connector = $this->sql->getConnector();
        	$changeName = $connector->prepare("UPDATE work SET name=? WHERE work_id=?");
        	$changeName->bind_param('si', $new_name, $work_id);
        	$changeName->execute();
        	$changeName->close();
        }

        //"%s?method=createTask&params={token=%s,task_name=%s,task_priority=%d}
        function createTask($token, $task_name, $task_priority) {
        	$connector = $this->sql->getConnector();
        	$get_token_id = $connector->prepare("SELECT user_id FROM user WHERE token=?");
        	$get_token_id->bind_param("s", $token);
        	$get_token_id->execute();
        	$user_id = $get_token_id->get_result()->fetch_assoc()["user_id"];
        	$get_token_id->close();

        	$currend_date = date('o-m-d');

        	if($user_id) {
        		$create_task = $connector->prepare("INSERT INTO task(task_name, date, priority, done, user_id) VALUES(?, ?, ?, 0, $user_id)");
        		$create_task->bind_param("ssi", $task_name, $currend_date, $task_priority);
        		$create_task->execute();
        		$create_task->close();
        	}
        	else {
        		echo "Wrong token";
        	}
        }

        //%s?method=createWork&params={token=%s, task_id=%d, work_name=%s, work_date=%s)
        function createWork($token, $task_id, $work_name, $work_date) {
        	$connector = $this->sql->getConnector();
        	//Получаем id пользователя по токкену, который запрашивает завершить задачу
            $get_user_id_request = $connector->prepare('SELECT user_id FROM user WHERE token = ?');
            $get_user_id_request->bind_param("s", $token);
            $get_user_id_request->execute();
            $user_id = $get_user_id_request->get_result()->fetch_assoc()['user_id'];
            $get_user_id_request->close();
            //Теперь в user_id id владельца задачи task_id
            $currend_date = date('o-m-d');
            if($user_id) {
	        	$create_work = $connector->prepare("INSERT INTO work(name, date, planned_end_date, done, user_id, task_id) VALUES(?, ?, ?, 0, ?, ?)");
	        	$create_work->bind_param("sssii", $work_name, $currend_date, $work_date, $user_id, $task_id);
	        	$create_work->execute();
	        	$create_work->close();
        	}
        	else {
        		echo "Wrong token";
        	}
        }

        //"%s?method=getSharesTask&params={token=%s}"
        function getShareTasks($token) {
        	$connector = $this->sql->getConnector();
        	$get_token_id = $connector->prepare("SELECT user_id FROM user WHERE token=?");
        	$get_token_id->bind_param("s", $token);
        	$get_token_id->execute();
        	$user_id = $get_token_id->get_result()->fetch_assoc()["user_id"];
        	$get_token_id->close();

        	$get_share_tasks_id = $connector->prepare("SELECT task_id FROM share WHERE user_id=?");
        	$get_share_tasks_id->bind_param("i", $user_id);
        	$get_share_tasks_id->execute();
        	$result = $get_share_tasks_id->get_result();
        	$user_tasks = array();
			while($row = mysqli_fetch_assoc($result)) {
				$user_tasks[] = $row;
			}
			$get_share_tasks_id->close();

			$taskArray = array();
			foreach ($user_tasks as $share_task) {
					//Получаем расшаренную задачу
					$get_share_task = $connector->prepare("SELECT * FROM task WHERE task_id=?");
					$get_share_task->bind_param("i", $share_task["task_id"]);
					$get_share_task->execute();
					$result = $get_share_task->get_result();
					while($row = mysqli_fetch_assoc($result)) {
						$taskArray[] = $row;
					}
					$get_share_task->close();
			}
			return json_encode($taskArray);
        }
    }
?>