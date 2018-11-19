package com.funtime.dubbo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作行为 前端控制器
 * </p>
 *
 * @author Steven
 * @since 2018-09-20
 */
@RestController
@RequestMapping("/operationLog")
public class OperationLogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}

