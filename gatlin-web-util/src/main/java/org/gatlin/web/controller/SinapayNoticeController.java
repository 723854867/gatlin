package org.gatlin.web.controller;

import org.gatlin.web.SinapayCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新浪支付通知
 * 
 * @author lynn
 */
@Controller
@RequestMapping("sinapay/notice")
@Conditional(SinapayCondition.class)
public class SinapayNoticeController {

}
