package com.jeasy.extension;

import com.alibaba.dubbo.rpc.protocol.rest.RestConstraintViolation;
import com.alibaba.dubbo.rpc.protocol.rest.RpcExceptionMapper;
import com.alibaba.dubbo.rpc.protocol.rest.ViolationReport;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class MyValidationExceptionMapper extends RpcExceptionMapper {

    @Override
    protected Response handleConstraintViolationException(ConstraintViolationException cve) {
        ViolationReport report = new ViolationReport();
        for (ConstraintViolation cv : cve.getConstraintViolations()) {
            report.addConstraintViolation(new RestConstraintViolation(
                cv.getPropertyPath().toString(),
                cv.getMessage(),
                cv.getInvalidValue() == null ? "null" : cv.getInvalidValue().toString()));
        }
        // 采用json输出代替xml输出
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(report).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}
