package com.bjc.lcp.common.base;


/**
 * 错误码
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
public enum BaseResponseCode implements ResponseCodeInterface {
    /**
     * 错误码
     */
    SUCCESS(0, "操作成功"),
    SYSTEM_BUSY(500001, "系统繁忙，请稍候再试"),
    OPERATION_ERRO(500002, "操作失败"),

    TOKEN_ERROR(401001, "登录凭证已过期，请重新登录"),
    OUT_AUTH_ERROR(401002, "越权查询"),
    DATA_ERROR(401003, "传入数据异常"),
    NOT_ACCOUNT(401004, "该用户不存在,请先注册"),
    USER_LOCK(401005, "该用户已被锁定，请联系运营人员"),
    PASSWORD_ERROR(401006, "用户名或密码错误"),
    METHODARGUMENTNOTVALIDEXCEPTION(401007, "方法参数校验异常"),  //@TODO 修改定义描述
    UNAUTHORIZED_ERROR(401008, "权鉴校验不通过"),
    ROLE_PERMISSION_RELATION(401009, "该菜单权限存在子集关联，不允许删除"),
    OLD_PASSWORD_ERROR(401010, "旧密码不正确"),
    NOT_PERMISSION_DELETED_DEPT(401011, "该组织机构下还关联着用户，不允许删除"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(401012, "操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(401013, "操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(401013, "操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(401015, "菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(401016, "菜单权限的标识符不能为空"),
    NAME_ERROR(401017, "名称不能重复"),
    QUOTED_ERROR(401018, "有关联关系，不允许删除"),
    ID_NOT_NULL_ERRPR(401019, "id不能为空"),
    NAME_NOT_NULL_ERRPR(401020, "名称不能为空"),
    IS_COMMON(401021,"请选择是否公共"),
    PURPOSE_NOT_NULL_ERRPR(401022, "目的不能为空"),
    DEFINITION_NOT_NULL_ERRPR(401023, "定义不能为空"),
    SCOPE_NOT_NULL_ERRPR(401024, "范围不能为空"),
    GROUP_ID_NOT_NULL_ERRPR(401025, "分组id不能为空"),
    GROUP_NAME_NOT_NULL_ERRPR(401026, "分组名称不能为空"),
    METHODARGUMENTEXCEPTION(401027, "方法参数校验异常"),
    NOT_APP_RESC(401028, "未查到应用资源"),
    NOT_APP_FOUND(401028, "未查询到应用信息"),
    NOT_NULL_PRIVATE_APPNO_RESC(401029, "资源类型-私有，appNo、refs不可为空"),
    NO_MASTER_BRANCH(401030, "master分支不存在"),
    PUSH_FAIL(401031, "PUSH失败"),
    COMMIT_FAIL(401032, "COMMIT失败"),
    NOT_NULL_TENAT_ID(401033, "机构号不能为空"),
    NOT_NULL_APP_NO(401034, "APPNO不能为空"),
    CBSP_GET_ORG_FAIL(401035, "CBSP获取机构信息异常"),
    CBSP_GET_USER_FAIL(401035, "CBSP获取人员信息异常"),
    NOT_NULL_REFS(401036, "分支不能为空"),
    NOT_NULL_BASC_ID(401037, "组件ID不能为空"),
    AUTH_ERROR(401038, "应用鉴权异常"),
    AUTH_APP_FAIL(401039, "应用鉴权失败，当前用户无权调用此接口"),
    AUTH_BASE_FAIL(401040, "基础鉴权失败，当前用户无权调用此接口"),
    USER_EMPTY(401041, "更新用户不能为空"),
    RESC_NOT_UPDATE(401042, "资源不在可修改状态"),
    NOT_RESC(401043, "更新的资源不存在"),
    NO_DATA(401044, "数据不存在"),
    NOT_NULL_SCEN_ID(401045, "scenId不能为空"),
    NOT_NULL_TRN_ID(401045, "trnId不能为空"),
    NOT_NULL_AQID(401046, "scenId不能为空"),
    NOT_NULL_MESSAGE_NAME(401047, "messageName不能为空"),

    LOGIN_INFO_ERROR(401048, "获取当前登录用户信息失败"),
    ENUMCODE_CHECK(401049, "枚举资源原始码重复或枚举资源默认缺省值不唯一"),
    WINFILENUM_CHECK(401050, "使用已有编号，文件编号不能为空"),
    FILNUMFLG_CHECK(401051, "文件出入库修改不能修改文件编号属性"),
    WINFIO_CHECK(401052, "该资源不能删除，文件编号被其它资源引用"),
    APP_BRAH_ARG_CHECK(401053,"应用号分支名不能为空"),
    APP_CHECK(401054,"应用号不能为空"),
    TENATID_ERROR(401055,"根据应用号获取租户ID时失败"),
    ORG_CACHE_IS_DOING(401056,"机构信息正在缓存中，请稍后再试"),
    UPLOAD_RESC_CHECH(401057,"清单文件不能为空"),
    RESC_LIST_CHECH(401058,"列表不能为空"),
    RELEASE_RESC_FLG_CHECH(401059,"删除待提交资源不能上架"),
    RELEASE_RESC_LIST_CHECH(401060,"删除待提交资源不能上架"),
    RELEASE_RESC_LIST_ERROR(401061,"资源上架失败"),
    GET_DB_ERROR(401062,"读取数据库中对应的对象失败"),
    GET_LOCAL_ERROR(401063,"读取仓库中对应的对象失败"),
    NO_RELEASE_SHELF_RESC(401064,"数据库中不存在发布版本"),
    NO_RESC(401065, "资源不存在"),
    DELETE_FILE_ERROR(401066, "删除文件失败"),
    WRITE_FILE_ERROR(401067, "生成文件失败"),
    READ_FILE_ERROR(401068, "读文件失败(T_RESC_STA)"),
    NO_DB_RESC_DATA(401069, "数据库资源不存在"),
    UPDATE_RESC_ERROR(401070, "更新资源失败"),
    ADD_RESC_ERROR(401071, "新增资源失败"),
    NO_LOCAL_RESC_DATA(401072, "本地仓库资源检出中，请稍候再试"),
    NOT_ROLLBACK(401073, "新建待提交资源不能回滚"),
    EXIST_MERGE_LIST(401074, "存在合并列表"),
    COMMIT_FILE_FAIL(401075, "提交文件失败"),
    ROLLBACK_FILE_FAIL(401076, "回滚文件失败"),
    CAN_NOT_UPDATE_RESC(401077,"除应用资源外，release_self/master分支资源不可修改"),
    CNT_COUNT_ERR(401078,"批量生成ID异常，数量必须大于0"),
    CNT_TYPE_ERR(401079,"未知的ID类型"),
    USER_APP_REPEAT(401080,"应用成员重复添加异常"),
    RESC_COPY_ERR(401081,"资源复制异常"),
    REQUEST_NO_SUPOSED(402001, "请求数字类参数范围系统不支持，请调整输入参数"),
    BRANCH_CREATE_SUCESS(402002, "分支创建成功"),
    BRANCH_CREATE_FAILD(402003, "分支创建失败"),
    ALOW_USER_DELETE_BRANCH_CHECK(402004, "当前用户非当前APP的应用管理员，禁止删除分支"),
    BRANCH_CLEAN_ACTION_CHECK(402005, "分支清除zone操作不合法，请校验后重新输入"),
    RESC_PATH_EXPORT_CHECK_MSG(402006, "资源导出文件路径有误，请检查配置文件路径"),
    FILE_EMPTY_CHECK_MSG(402007, "文件为空"),
    FILE_IMPORT_TIMEOUT_OR_NOEXISTS_MSG(402008, "文件导入记录过期或不存在"),
    BRANCH_NO_CONFLICT_AND_MERGE_SUCESS(402009, "分支无冲突，合并分支成功"),
    CONFLICT_RESC_TYPE_ERR_MSG(402010, "冲突资源类型有误"),
    REPOSTORY_INIT_FAILD_MSG(402011, "文件仓库初始化失败"),
    GITCONFIG_INSERT_FAILD_MSG(402012, "插入保存应用仓库信息失败"),
    GITCONFIG_EXISTS_INSERT_FAILD_MSG(402013, "当前应用号已存在仓库信息，无法保存。"),
    APP_GITCONFIG_EXISTS_INSERT_FAILD_MSG(402014, "当前应用号不存在仓库信息，无法执行更新操作。"),
    UPDATE_APP_GITCONFIG_FAILD_MSG(402015, "修改应用仓库信息时失败"),
    APP_GITCONFIG_QUERY_FAILD_MSG(402016, "没有查询到应用的仓库信息"),
    APP_GIT_USERNAME_PASSWORD_NO_CONFIG_MSG(402017, "用户的Git账号密码未配置"),
    APP_GIT_CONFIG_FAILD_MSG(402018, "应用Git仓库未配置或配置有误或Gitlab(Coding)仓库没有配置权限"),
    CREATE_MASTER_RELEASE_SHELF_BRANCH_FAILD_MSG(402019, "创建master及release_shelf分支失败"),
    DEVLOPER_CONFIG_ENUM_CONFLICT_FAILD_MSG(402020, "开发人员配置错误，资源类型未配置对应的RescKdConflictEnum枚举参数，请联系开发人员！"),
    UPLOAD_FAILD_MSG(402021, "上传文件失败"),
    GET_GIT_HANDLE_FAILD_MSG(402022, "获取的Git对象失败"),
    GIT_USERNAME_PASSWD_CHECK_FAILD_MSG(402023, "Git用户密码认证失败，请重新配置Git用户及密码"),
    NO_FOUND_APP_GIT_REPOSTORY_CHECK_CHANGE_TRY_INIT_GIT_REPOSTORY_FAILD_MSG(402024, "找不到应用分支下面的仓库，请重置并重新初始化应用的仓库"),
    CLONE_REPOSTORY_FAILD_MSG(402025, "克隆远程仓库失败，当前仓库配置有误或权限不够或网络异常，请重新配置仓库"),
    APP_GIT_URL_CONFIG_ERR_MSG(402026, "应用的URL配置不正确，请重新配置URL"),
    BRANCH_NO_EXISTS_ERR_MSG(402027, "分支不存在"),
    BRANCH_SWITCH_ERR_MSG(402028, "切换分支异常"),
    BRANCH_PULL_ERR_MSG(402029, "调用Git接口异常（Git pull异常）"),
    BRANCH_FETCH_ERR_MSG(402029, "调用Git接口异常（Git fetch异常）"),
    BRANCH_CREATE_ERR_CREATEFROM_NO_EXISTS_MSG(402030, "创建失败，引用分支不存在"),
    BRANCH_CREATE_ERR_NEW_BRANCH_EXISTS_MSG(402031, "创建失败，新建分支已存在"),
    BRANCH_CREATE_INVALID_REFNAME_MSG(402032, "创建失败，分支名称不合法"),
    BRANCH_GIT_API_FAILD_MSG(402033, "创建失败，分支接口调用失败！"),
    DELETE_FILES_FAILD_MSG(402034, "删除文件失败，请检查文件配置"),
    IMPORT_FILES_FAILD_MSG(402035, "导入失败"),
    IMPORT_FILES_SUCESS_MSG(402036, "导入成功"),
    UPLOAD_FILE_IS_NULL(402037, "上传文件不能为空！"),
    INVALID_REMOTE_REPOSTORY_GIT_NO_PROMISSION(402038, "git用户权限不够（Git用户无远程仓库权限）！"),
    GITLAB_IS_NOT_ALIVE(402039, "Gitlab服务宕机，无法访问，不支持当前操作，待Gitlab服务恢复后，请重试！"),
    BRANCH_OBJID_API_FAILD_MSG(402040, "创建失败，commitId获取objId失败！"),
    UNDISPOSED_ERROR(500, "服务器内部未处理的异常")
    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误消息
     */
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
