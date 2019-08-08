package com.sft.simulate.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sft.simulate.entity.Goods;
import com.sft.simulate.enums.common.ResponseEnum;
import com.sft.simulate.pojo.GoodsRequest;
import com.sft.simulate.pojo.Response;
import com.sft.simulate.service.GoodsService;
import com.sft.simulate.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.sft.simulate.pojo.CurrencyConstant.ENCODING;
import static com.sft.simulate.pojo.CurrencyConstant.PULLOUT_GOODS_URL;

@RestController
@Slf4j
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 获取商品数据
     * @return
     */
    @GetMapping("/pull")
    public Response pullOutGoodsInfo(){
        try {
            String result = HttpClientUtil.get(PULLOUT_GOODS_URL,null,ENCODING);
            Response response = JSONObject.parseObject(result,Response.class);
            if(response.getCode()!= ResponseEnum.SUCCESS.getCode()){
                return Response.fail(response.getMsg());
            }
            List<GoodsRequest> results = JSONArray.parseArray
            (JSON.toJSONString(response.getData()),GoodsRequest.class);
            Iterator<Goods> it = goodsService.getGoodsList();
            saveGoods(results,it);
        } catch (Exception e) {
            log.error("系统异常:{}",e);
            return Response.fail("系统异常");
        }
        return Response.success();
    }


    private void saveGoods(List<GoodsRequest> results,Iterator<Goods> it){
        List<Goods> goodsList = new ArrayList<>();
        for(GoodsRequest gr : results){
            while (it.hasNext()) {
                Goods gs = it.next();
                if(StringUtils.equals(gs.getId(),gr.getGoods_id())){
                    continue;
                }
            }
            Goods goods = new Goods();
            goods.setGoodsId(gr.getGoods_id());
            goods.setName(gr.getName());
            goods.setPrice(gr.getPrice());
            goods.setBrief(gr.getBrief());
            goods.setAddDate(new Date());
            goodsList.add(goods);
        }
        goodsService.saveGoodsList(goodsList);
    }


}
