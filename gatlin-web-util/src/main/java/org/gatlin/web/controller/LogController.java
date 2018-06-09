package org.gatlin.web.controller;

import javax.validation.Valid;

import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.soa.log.api.LogService;
import org.gatlin.web.util.WebCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("log")
public class LogController {
	
	@Autowired(required = false)
	private LogService logService;

	@ResponseBody
	@RequestMapping("request")
	public Object request(@RequestBody @Valid SoaParam param) {
		Assert.notNull(WebCode.LOG_REQUEST_UNSUPPORT, logService);
		return logService.requests(param.query());
	}
}
