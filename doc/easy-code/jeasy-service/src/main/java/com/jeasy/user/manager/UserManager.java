package com.jeasy.user.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.impl.BaseManagerImpl;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.AbstractConverter;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.exception.MessageException;
import com.jeasy.organization.dto.OrganizationDTO;
import com.jeasy.organization.manager.OrganizationManager;
import com.jeasy.role.dto.RoleDTO;
import com.jeasy.role.manager.RoleManager;
import com.jeasy.user.biz.UserBiz;
import com.jeasy.user.dao.UserDAO;
import com.jeasy.user.dto.*;
import com.jeasy.user.entity.UserEntity;
import com.jeasy.userorg.dto.UserOrgDTO;
import com.jeasy.userorg.manager.UserOrgManager;
import com.jeasy.userrole.dto.UserRoleDTO;
import com.jeasy.userrole.manager.UserRoleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class UserManager extends BaseManagerImpl<UserDAO, UserEntity, UserDTO> {

    /**
     * this is a converter demo only for BeanKit.copyProperties
     *
     * @see BeanKit#copyProperties(Object source, Object target, AbstractConverter... converters)
     */
    private static final AbstractConverter<String, String> DEMO_CONVERTER = new AbstractConverter<String, String>("filed1", "filed2") {
        @Override
        public String convert(final String val) {
            return val;
        }
    };

    public static UserManager me() {
        return SpringContextHolder.getBean(UserManager.class);
    }

    public List<UserListResDTO> list(final UserListReqDTO userListReqDTO) {
        UserDTO userParamsDTO = new UserDTO();
        if (!Func.isEmpty(userListReqDTO)) {
            BeanKit.copyProperties(userListReqDTO, userParamsDTO, DEMO_CONVERTER);
        }

        List<UserDTO> userDTOList = super.findList(userParamsDTO);

        if (!Func.isEmpty(userDTOList)) {
            List<UserListResDTO> items = Lists.newArrayList();
            for (UserDTO userDTO : userDTOList) {
                UserListResDTO userListResDTO = new UserListResDTO();
                BeanKit.copyProperties(userDTO, userListResDTO, DEMO_CONVERTER);
                items.add(userListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<UserListResDTO> listByVersion1(final UserListReqDTO userListReqDTO) {
        return list(userListReqDTO);
    }

    public List<UserListResDTO> listByVersion2(final UserListReqDTO userListReqDTO) {
        return list(userListReqDTO);
    }

    public List<UserListResDTO> listByVersion3(final UserListReqDTO userListReqDTO) {
        return list(userListReqDTO);
    }

    public UserListResDTO listOne(final UserListReqDTO userListReqDTO) {
        UserDTO userParamsDTO = new UserDTO();
        if (!Func.isEmpty(userListReqDTO)) {
            BeanKit.copyProperties(userListReqDTO, userParamsDTO, DEMO_CONVERTER);
        }

        UserDTO userDTO = super.findOne(userParamsDTO);
        if (!Func.isEmpty(userDTO)) {
            UserListResDTO userListResDTO = new UserListResDTO();
            BeanKit.copyProperties(userDTO, userListResDTO, DEMO_CONVERTER);
            return userListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<UserPageResDTO> pagination(final UserPageReqDTO userPageReqDTO, final Integer current, final Integer size) {
        QueryWrapper<UserEntity> queryWrapper = UserBiz.me().transferUserQueryWrapper(userPageReqDTO);
        PageDTO<UserDTO> userDTOPage = super.findPage(queryWrapper, current, size);

        if (Func.isNotEmpty(userDTOPage) && Func.isNotEmpty(userDTOPage.getRecords())) {
            List<UserPageResDTO> userPageResDTOs = Lists.newArrayList();
            for (UserDTO userDTO : userDTOPage.getRecords()) {
                userPageResDTOs.add(UserBiz.me().transferUserPageResDTO(userDTO));
            }
            PageDTO<UserPageResDTO> userPageResDTOPage = new PageDTO<>();
            userPageResDTOPage.setRecords(userPageResDTOs);
            userPageResDTOPage.setTotal(userDTOPage.getTotal());
            return userPageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<UserPageRoleResDTO> pagination(final UserPageRoleReqDTO userPageRoleReqDTO, final Integer current, final Integer size) {
        PageDTO<RoleDTO> roleDTOPage = RoleManager.me().findPage(new RoleDTO(), current, size);

        UserRoleDTO userRoleDTO = UserBiz.me().transferUserRoleDTO(userPageRoleReqDTO);
        List<UserRoleDTO> userRoleDTOList = UserRoleManager.me().findList(userRoleDTO);
        Set<Long> userRoleIdSet = UserBiz.me().buildUserRoleIdSet(userRoleDTOList);

        PageDTO<UserPageRoleResDTO> userPageRoleResDTOPage = new PageDTO<>();
        List<UserPageRoleResDTO> records = Lists.newArrayList();
        for (RoleDTO roleDTO : roleDTOPage.getRecords()) {
            records.add(UserBiz.me().transferUserPageRoleResDTO(roleDTO, userRoleIdSet));
        }
        userPageRoleResDTOPage.setRecords(records);
        userPageRoleResDTOPage.setTotal(roleDTOPage.getTotal());
        return userPageRoleResDTOPage;
    }

    public List<UserListRoleResDTO> listRole(final UserListRoleReqDTO userListRoleReqDTO) {
        UserRoleDTO userRoleDTO = UserBiz.me().transferUserRoleDTO(userListRoleReqDTO);
        List<UserRoleDTO> userRoleDTOList = UserRoleManager.me().findList(userRoleDTO);
        return UserBiz.me().transferUserListRoleResDTO(userRoleDTOList);
    }

    public List<UserListOrganizationResDTO> listOrganization(final UserListOrganizationReqDTO userListOrganizationReqDTO) {
        UserOrgDTO userOrgDTO = UserBiz.me().transferUserOrgDTO(userListOrganizationReqDTO);
        List<UserOrgDTO> userOrgDTOList = UserOrgManager.me().findList(userOrgDTO);
        List<OrganizationDTO> organizationDTOList = OrganizationManager.me().findList(new OrganizationDTO());
        return UserBiz.me().transferUserListOrganizationResDTO(userOrgDTOList, organizationDTOList);
    }

    public Boolean add(final UserAddReqDTO userAddReqDTO) {
        if (Func.isEmpty(userAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        QueryWrapper<UserEntity> queryWrapper = UserBiz.me().transferUserQueryWrapper(userAddReqDTO);
        Integer count = UserManager.me().findCount(queryWrapper);
        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "登录名称已存在");
        }

        return super.saveDTO(UserBiz.me().transferUserDTO(userAddReqDTO));
    }

    public Boolean addAllColumn(final UserAddReqDTO userAddReqDTO) {
        if (Func.isEmpty(userAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserDTO userDTO = new UserDTO();
        BeanKit.copyProperties(userAddReqDTO, userDTO, DEMO_CONVERTER);
        return super.saveAllColumn(userDTO);
    }

    public Boolean addBatchAllColumn(final List<UserAddReqDTO> userAddReqDTOList) {
        if (Func.isEmpty(userAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<UserDTO> userDTOList = Lists.newArrayList();
        for (UserAddReqDTO userAddReqDTO : userAddReqDTOList) {
            UserDTO userDTO = new UserDTO();
            BeanKit.copyProperties(userAddReqDTO, userDTO, DEMO_CONVERTER);
            userDTOList.add(userDTO);
        }
        return super.saveBatchAllColumn(userDTOList);
    }

    public UserShowResDTO show(final Long id) {
        UserDTO userDTO = super.findById(id);
        List<RoleDTO> roleDTOList = findRoleList(id);
        List<OrganizationDTO> organizationDTOList = findOrganizationList(id);

        if (!Func.isEmpty(userDTO)) {
            return UserBiz.me().transferUserShowResDTO(userDTO, roleDTOList, organizationDTOList);
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    private List<OrganizationDTO> findOrganizationList(final Long id) {
        List<OrganizationDTO> organizationDTOList = Lists.newArrayList();

        UserOrgDTO userOrgDTO = new UserOrgDTO();
        userOrgDTO.setUserId(id);
        List<UserOrgDTO> userOrgDTOList = UserOrgManager.me().findList(userOrgDTO);
        if (Func.isNotEmpty(userOrgDTOList)) {
            Set<Long> orgIdSet = Sets.newHashSet();
            for (UserOrgDTO userOrg : userOrgDTOList) {
                orgIdSet.add(userOrg.getOrgId());
            }

            if (Func.isNotEmpty(orgIdSet)) {
                organizationDTOList = OrganizationManager.me().findBatchIds(Lists.newArrayList(orgIdSet));
            }
        }

        return organizationDTOList;
    }

    private List<RoleDTO> findRoleList(final Long id) {
        List<RoleDTO> roleDTOList = Lists.newArrayList();

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(id);
        List<UserRoleDTO> userRoleDTOList = UserRoleManager.me().findList(userRoleDTO);
        if (Func.isNotEmpty(userRoleDTOList)) {
            Set<Long> roleIdSet = Sets.newHashSet();
            for (UserRoleDTO userRole : userRoleDTOList) {
                roleIdSet.add(userRole.getRoleId());
            }

            if (Func.isNotEmpty(roleIdSet)) {
                roleDTOList = RoleManager.me().findBatchIds(Lists.newArrayList(roleIdSet));
            }
        }

        return roleDTOList;
    }

    public List<UserShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<UserDTO> userDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(userDTOList)) {
            List<UserShowResDTO> userShowResDTOList = Lists.newArrayList();
            for (UserDTO userDTO : userDTOList) {
                UserShowResDTO userShowResDTO = new UserShowResDTO();
                BeanKit.copyProperties(userDTO, userShowResDTO, DEMO_CONVERTER);
                userShowResDTOList.add(userShowResDTO);
            }
            return userShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final UserModifyReqDTO userModifyReqDTO) {
        if (Func.isEmpty(userModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        QueryWrapper<UserEntity> queryWrapper = UserBiz.me().transferUserQueryWrapper(userModifyReqDTO);
        Integer count = UserManager.me().findCount(queryWrapper);
        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "登录名称已存在");
        }

        UserDTO userDTO = UserBiz.me().transferUserDTO(userModifyReqDTO);
        return super.modifyById(userDTO);
    }

    public Boolean modifyAllColumn(final UserModifyReqDTO userModifyReqDTO) {
        if (Func.isEmpty(userModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserDTO userDTO = new UserDTO();
        BeanKit.copyProperties(userModifyReqDTO, userDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(userDTO);
    }

    public Boolean removeByParams(final UserRemoveReqDTO userRemoveReqDTO) {
        if (Func.isEmpty(userRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserDTO userParamsDTO = new UserDTO();
        BeanKit.copyProperties(userRemoveReqDTO, userParamsDTO, DEMO_CONVERTER);
        return super.remove(userParamsDTO);
    }

    public Boolean modifyRole(UserModifyRoleReqDTO userModifyRoleReqDTO) {
        UserDTO userDTO = UserManager.me().findById(userModifyRoleReqDTO.getUserId());

        if (Func.isEmpty(userDTO)) {
            throw new MessageException(ModelResult.CODE_200, "用户ID参数错误");
        }

        UserRoleDTO userRoleDTO = UserBiz.me().transferUserRoleDTO(userModifyRoleReqDTO);
        UserRoleManager.me().remove(userRoleDTO);

        if (Func.isNotEmpty(userModifyRoleReqDTO.getRoleIds())) {
            List<RoleDTO> roleDTOList = RoleManager.me().findBatchIds(userModifyRoleReqDTO.getRoleIds());
            List<UserRoleDTO> userRoleDTOList = UserBiz.me().buildUserRoleDTOList(userDTO, roleDTOList);
            return UserRoleManager.me().saveBatchAllColumn(userRoleDTOList);
        }
        return true;
    }

    public Boolean modifyOrganization(final UserModifyOrganizationReqDTO userModifyOrganizationReqDTO) {
        UserDTO userDTO = UserManager.me().findById(userModifyOrganizationReqDTO.getUserId());

        if (Func.isEmpty(userDTO)) {
            throw new MessageException(ModelResult.CODE_200, "用户ID参数错误");
        }

        UserOrgDTO userOrgDTO = UserBiz.me().transferUserOrgDTO(userModifyOrganizationReqDTO);
        UserOrgManager.me().remove(userOrgDTO);

        if (Func.isNotEmpty(userModifyOrganizationReqDTO.getOrgIds())) {
            List<OrganizationDTO> organizationDTOList = OrganizationManager.me().findBatchIds(userModifyOrganizationReqDTO.getOrgIds());
            List<UserOrgDTO> userOrgDTOList = UserBiz.me().buildUserOrgDTOList(userDTO, organizationDTOList);
            return UserOrgManager.me().saveBatchAllColumn(userOrgDTOList);
        }
        return true;
    }

    @Override
    protected List<UserDTO> entityToDTOList(final List<UserEntity> userEntityList) {
        List<UserDTO> userDTOList = null;
        if (!Func.isEmpty(userEntityList)) {
            userDTOList = Lists.newArrayList();
            for (UserEntity userEntity : userEntityList) {
                userDTOList.add(entityToDTO(userEntity));
            }
        }
        return userDTOList;
    }

    @Override
    protected UserDTO entityToDTO(final UserEntity userEntity) {
        UserDTO userDTO = null;
        if (!Func.isEmpty(userEntity)) {
            userDTO = new UserDTO();
            BeanKit.copyProperties(userEntity, userDTO);
        }
        return userDTO;
    }

    @Override
    protected List<UserEntity> dtoToEntityList(final List<UserDTO> userDTOList) {
        List<UserEntity> userEntityList = null;
        if (!Func.isEmpty(userDTOList)) {
            userEntityList = Lists.newArrayList();
            for (UserDTO userDTO : userDTOList) {
                userEntityList.add(dtoToEntity(userDTO));
            }
        }
        return userEntityList;
    }

    @Override
    protected UserEntity dtoToEntity(final UserDTO userDTO) {
        UserEntity userEntity = null;
        if (!Func.isEmpty(userDTO)) {
            userEntity = new UserEntity();
            BeanKit.copyProperties(userDTO, userEntity);
        }
        return userEntity;
    }

    @Override
    protected UserEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new UserEntity();
        }
        return (UserEntity) MapKit.toBean(map, UserEntity.class);
    }

    @Override
    protected UserDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new UserDTO();
        }
        return (UserDTO) MapKit.toBean(map, UserDTO.class);
    }
}
