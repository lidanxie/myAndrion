<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of UserModel
 *
 * @author jianhong
 */
namespace Home\Model;
use Think\Model;
class UserModel extends Model {
    private $insertID;
    public function __construct() {
        $user = M('user');
        $result=$user->field('id')->order('id desc')->find();
        $this->insertID=(int)$result['id'];
    }

    

    public function userLogin($username, $pwd) {
        $user = M('user');
        $data['username'] = $username;
        $data['password'] = $pwd;
        $result = $user->where($data)->find();
        return $result;
    }
    
     public function userPwd($userid) {
        $user = M('user');
        $data['userid'] = $userid;
        $data['password'] = $pwd;
        $result = $user->query("select `password` from `user` where userid='$userid' ");
        return $result;
    }
    
    public function changePwd($userid,$newpwd){
        $user = M('user');
        $data['password'] = $newpwd;
        $result = $user->where("userid='$userid'")->save($data);
        return $result;
    }

    public function changeUserInfo($userid,$username){
        $user=M('user');
        $data['userid']=$userid;
        //$data['passward']=$pwd;
        $data['username']=$username;
        $result=$user->where($data)->save();
        return $result;
    }
    
    public function findUser($userid){
        $user=M('user');
        $data['userid']=$userid;
        $result=$user->where($data)->find();
        return $result;
    }

    public function userSignup($password,$username,$quetionOne,$answerOne,$quetionTwo,$answerTwo){
        $user=M('user');
        $data['username']=$username;
        if($user->where($data)->find()) return -1;
        
        $this->insertID+=1;
        $userid=  str_pad($this->insertID, 6, '0', STR_PAD_LEFT);
        $data['userid']=$userid;
        $data['passward']=$password;
        $data['Q1']=$quetionOne;
        $data['A1']=$answerOne;
        $data['Q2']=$quetionTwo;
        $data['A2']=$answerTwo;
        $result=$user->data($data)->add();
        if($result) return $userid;
        else return 0;
    }
    
}
