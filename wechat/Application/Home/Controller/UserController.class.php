<?php
namespace Home\Controller;
session_start();
use Think\Controller;
use Home\Model\UserModel;
use Home\Model\FriendsModel;
use Home\Model\MomentModel;
use Home\Event\JsonEvent;
class UserController extends Controller {
    public function islegal($userid){
       if($_SESSION[$userid]==1) return TRUE ;
        else return FALSE;        
    }
    
    public function test(){
        $username="zdr";
        $pwd="123";
        $result = D('user')->userLogin($username, $pwd);
        var_dump($result);
    }

    //登录
    public function login($username, $pwd) {
        $result = D('user')->userLogin($username, $pwd);
        $data['username'] = $username;
        $_SESSION['username'] = $username;
//        echo $_SESSION['username'];
        $re=M('user')->where($data)->find();
        $userid = $re['userid'];
        $_SESSION[$userid]=1;
        $json = (new JsonEvent())->encapsulate($result, "userInfo");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    //退出登录
    public function logout($userid){
        if($this->islegal($userid)){
            session($userid,null);
            $json['returnCode'] = 1;
            $json['msg'] = "success";
        }else{
            $json['returnCode'] = 0;
            $json['msg'] = "fail";

        }
            header('Content-type:text/json');
            echo json_encode($json,JSON_UNESCAPED_UNICODE); 
        
        
    }
    //获取好友列表
    public function getFriends($userid){
        $re1=D('friends')->getUserFriends1($userid);// 获取到userid的多条freid
        $re2=D('friends')->getUserFriends2($userid);
        
        $result=  array_merge($re1,$re2);
        
//        var_dump($re1);
//        var_dump($re2);
        
//        var_dump($result);
        $json = (new JsonEvent())->encapsulate($result, "friends");
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }

    
        public function changeUserpwd($userid,$oldpwd,$oldpwd){
//        $result=(new UserModel())->changeUserInfo($userid, $oldpwd, $username);
//       $_POST['oldpwd']=$oldpwd;
//       $_POST['newpwd']=$newpwd;
       
       $pwd=(new UserModel())->userPwd($userid);
       if($pwd==$oldpwd){
            $result=(new UserModel())->changePwd($userid, $newpwd);
       }
        $json=(new JsonEvent())->encapsulate($result, 'userinfo');
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE); 
    }
    //注册
    public function register($password,$clearPwd,$username,$quetionOne,$answerOne,$quetionTwo,$answerTwo){


         $result = D('user')->userSignup($password,$username,$quetionOne,$answerOne,$quetionTwo,$answerTwo);
        switch ($result) {
        case -1:
            $json['returnCode'] = 0;
            $json['msg'] = "用户名已存在！";
            break;
        case 0:
            $json['returnCode'] = 0;
            $json['msg'] = "系统错误，注册失败！";
            break;
        default:
            $json['returnCode'] = 1;
            $json['msg'] = "success";
            $json['userid'] = $result;
            break;
        }
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    public function addFriend($friendId){

        $username=$_SESSION['username'];
//        var_dump($username);
//        echo 'yonghu:'.$username;
        $data['username'] = $username;
        $re=M('user')->where($data)->find();
        $userid = $re['userid'];
        $find1=(new FriendsModel())->findFriends1($userid, $friendId);
        $find2=(new FriendsModel())->findFriends2($userid, $friendId);
//        var_dump($find2);
//        var_dump($find1);
        if($find1||$find2){
            $json['returnCode'] = 0;
            $json['msg'] = "fail";
        }else{

            $re=M('friends')->where($data)->find();
            $frename = $re['username'];
            $result=(new FriendsModel())->addFriends($userid,$username,$friendId,$frename);
            $json['returnCode'] = 1;
            $json['msg'] = "success";
        }
        
        header('Content-type:text/json');
        echo json_encode($json,JSON_UNESCAPED_UNICODE);
    }
    
    /**
     * 获取个人信息
     */
    public function getUserMsg($userid){
        // 判断是否登录
        if($this->islegal($userid)){
            $result = D('user') ->findUser($userid);
            // 测试输出
            var_dump($result);
            if($result){
                $data['id'] = $result['id'];
                $data['userid'] = $result['userid'];
                $data['username'] = $result['username'];
                $data['pic'] = $result['pic'];
                $json = (new JsonEvent())->encapsulate($data, "userInfo");
                header('Content-type:text/json');
                echo json_encode($json,JSON_UNESCAPED_UNICODE);
            }
        }else{
            $json['returnCode'] = 0;
            $json['msg'] = "unlogin";
        }
    }

    /**
     * 修改个人信息
     */
    public function changeUserMsg($userid,$username){
        if($this->islegal($userid)){
            $result1 = D('user') ->changeUserInfo($userid,$username);
            var_dump($result);
            if($result1){
                $result2 = D('user') ->findUser($userid);
                $data['id'] = $result2['id'];
                $data['userid'] = $result2['userid'];
                $data['username'] = $result2['username'];
                $data['pic'] = $result2['pic'];
                $json = (new JsonEvent())->encapsulate($data, "userInfo");
                header('Content-type:text/json');
                echo json_encode($json,JSON_UNESCAPED_UNICODE);
            }else{
                $json['returnCode'] = 0;
                $json['msg'] = "change fail";
            }
        }
        else{
            $json['returnCode'] = 0;
            $json['msg'] = "unlogin";
        }
    }

    /**
     * 获取用户相册
     */
    public function getUserShare($userid){
        if($this->islegal($userid)){
            $result = D('moment') ->findMoment($userid);
            // 测试结果
            var_dump($result);
            if($result){
                $data['id'] = $result['id'];
                $data['userid'] = $result['userid'];
                $data['words'] = $result['words'];
                $data['image'] = $result2['image'];
                $data['time'] = $result2['time'];
                $json = (new JsonEvent())->encapsulate($data, "userMoment");
                header('Content-type:text/json');
                echo json_encode($json,JSON_UNESCAPED_UNICODE);
            }
        }else{
            $json['returnCode'] = 0;
            $json['msg'] = "unlogin";
        }
    }
}