<?php
require_once "task.php";
require_once "auth.php";
//Example localhost/todo/api/?method=getTask&params={param1=value1, param2=value2}
//Start point for all api requests. This script parse request and send it to next file.
header('Content-Type: text/html; charset=utf-8');
$method = $_GET['method'];
$params = $_GET['params'];

mysqli_report(MYSQLI_REPORT_STRICT);
function parseParams($params) {
    $parsedParams = $params;
    $parsedParams = str_replace("{", "", $parsedParams);
    $parsedParams = str_replace("}", "", $parsedParams);

    $paramsArray = explode(",", $parsedParams);
    $asocArray = [];
    //Преобразуем массив в ассоциативный
    foreach ($paramsArray as $param) {
        $param = explode("=", $param);
        $asocArray[$param[0]] = $param[1];
    }
    return $asocArray;
}

$params = parseParams($params);

/*if ($method =='getTask')    //Если клиент запросил свои задачи.
{
    if (array_key_exists("token", $params)) {
        require_once "task.php";
        $taskApi = new Task();
        echo $taskApi->getTask($params['token']);
    }
    else {
        echo "No token given";
    }
}

//Попытка авторизации
if ($method == "login")
{
    require_once "auth.php";
    $loginApi = new Auth();
    echo $loginApi->login_user($params["email"], $params["password"]);
}*/

switch ($method) {
    case "getTask":
        if (array_key_exists("token", $params)) {
            $taskApi = new Task();
            echo $taskApi->getTask($params['token']);
        }
        else {
            echo "No token given";
        }
        break;
    case "login":
        $loginApi = new Auth();
        echo $loginApi->login_user($params["email"], $params["password"]);
        break;
    case "doneTask":
        $taskApi = new Task();
        if($taskApi->isUserHaveTask($params['token'], $params['task_id'])) {
            $taskApi->doneTask($params['task_id']);
            echo json_encode(Array('message' => 'Task done updated'));
        }
        else {
            echo json_encode(Array('message' => 'User don`t have this task!'));
        }
        break;
    case "changeTaskName":
        $taskApi = new Task();
        if($taskApi->isUserHaveTask($params['token'], $params['task_id'])) {
            $taskApi->changeName($params['task_id'], $params['name']);
            echo json_encode(Array('message' => 'Task name updated'));
        }
        else {
            echo json_encode(Array('message' => 'User don`t have this task!'));
        }
        break;
    case "changeTaskPriority":
        $taskApi = new Task();
        if($taskApi->isUserHaveTask($params['token'], $params['task_id'])) {
            $taskApi->changePriority($params['task_id'], $params['priority']);
            echo json_encode(Array('message' => 'Task priority updated'));
        }
        else {
            echo json_encode(Array('message' => 'User don`t have this task!'));
        }
        break;
    case "changeWorkName":
        $taskApi = new Task();
        if($taskApi->isUserHaveWork($params['token'], $params['work_id'])) {
            $taskApi->ChangeWorkName($params['work_id'], $params['name']);
            echo json_encode(Array('message' => 'Work name updated'));
        }
        else echo json_encode(Array('message' => 'User done have this work'));
        break;
   /* case "changeWorkPriority":
        $taskApi = new Task();
        if($taskApi->isUserHaveWork($params['token'], $params['work_id'])) {
            $taskApi->changeWorkPriority($params['work_id'], $params['work_priority']);
            echo json_encode(Array('message' => 'Work priority updated'));
        }
        else echo json_encode(Array('message' => 'User done have this work'));
        break;*/
    case "changeWorkDone":
        $taskApi = new Task();
        if($taskApi->isUserHaveWork($params['token'], $params['work_id'])) {
            $taskApi->doneWork($params['work_id']);
            echo json_encode(Array('message' => 'Work done updated'));
        }
        else echo json_encode(Array('message' => 'User done have this work'));
        break;
    //"%s?method=createTask&params={token=%s,task_name=%s,task_priority=%d}
    case "createTask":
        $taskApi = new Task();
        $taskApi->createTask($params["token"], $params["task_name"], $params["task_priority"]);
        break;
    //%s?method=createWork&params={token=%s, task_id=%d, work_name=%s, work_date=%s)
    case "createWork":
        $taskApi = new Task();
        $taskApi->createWork($params["token"], $params["task_id"], $params["work_name"], $params["work_date"]);
        break;
        //"%s?method=registerUser&params={email=%s,password=%s,first_name=%s,last_name=%s,patron_name=%s}
    case "registerUser":
        $auth= new Auth();
        $auth->register_user($params["last_name"], $params["first_name"], $params["patron_name"], $params["email"], $params["password"]);
        break;
    case "getShareTasks":
        $taskApi = new Task();
        echo $taskApi->getShareTasks($params["token"]);
        break;
}
?>