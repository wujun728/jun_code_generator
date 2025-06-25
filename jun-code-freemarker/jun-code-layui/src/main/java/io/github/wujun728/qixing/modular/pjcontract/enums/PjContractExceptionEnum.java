//
//package io.github.wujun728.qixing.modular.pjcontract.enums;
//
//import annotion.io.github.wujun728.core.ExpEnumType;
//import abs.enums.exception.io.github.wujun728.core.AbstractBaseExceptionEnum;
//import factory.io.github.wujun728.core.ExpEnumCodeFactory;
//import io.github.wujun.sys.core.consts.SysExpEnumConstant;
//
///**
// * 业务约定书
// * @author wujun
// * @date 2025-06-25 00:58:22
// */
//@ExpEnumType(module = SysExpEnumConstant.QIXING_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_POS_EXCEPTION_ENUM)
//public enum PjContractExceptionEnum implements AbstractBaseExceptionEnum {
//
//    /**
//     * 数据不存在
//     */
//    NOT_EXIST(1, "此数据不存在");
//
//    private final Integer code;
//
//    private final String message;
//        PjContractExceptionEnum(Integer code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    @Override
//    public Integer getCode() {
//        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
//    }
//
//    @Override
//    public String getMessage() {
//        return message;
//    }
//
//}
