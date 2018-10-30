package com.micro.useredge.utils;

import com.micro.useredge.VO.ResultVO;
import com.micro.useredge.enums.ResultEnum;

/**
 * author: mSun
 * date: 2018/10/26
 */
public class ResultVOUtil {

    // 成功，不带数据返回
    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    // 成功，带数据返回
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    // 失败
    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }

    // 失败 带报错信息详情
    public  static ResultVO error(ResultEnum resultEnum, String detailMessage) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage()+":"+detailMessage);
        return resultVO;
    }


}
