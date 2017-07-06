<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Home\Model;

/**
 * Description of MomentModel
 *
 * @author asus-pc
 */
class MomentModel {
    //put your code here
    /**
     * [查找朋友圈中信息，个人中为相册信息]
     * @param  [int] $userid [用户id]
     * @return [查询结果]
     */
    public function findMoment($userid){
        $user=M('moment');
        $data['userid']=$userid;
        $result=$user->where($data)->find()->order('time desc');
        return $result;
    }

}
