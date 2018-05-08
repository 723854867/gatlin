package org.gatlin.web.controller;

import javax.validation.Valid;

import org.gatlin.sdk.alipay.notice.TradeNotice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("notice/alipay")
public class AlipayController {

	@ResponseBody
	@RequestMapping("recharge")
	public Object recharge(@RequestBody @Valid TradeNotice param) {
		return null;
	}
}
