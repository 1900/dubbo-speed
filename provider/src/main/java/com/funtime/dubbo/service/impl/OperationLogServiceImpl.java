package com.funtime.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.funtime.dubbo.entity.OperationLog;
import com.funtime.dubbo.service.IOperationLogService;
import com.funtime.dubbo.mapper.OperationLogMapper;

/**
 * <p>
 * 操作行为 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2018-09-20
 */
@Service(version = "1.0.0", timeout = 60000)
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
