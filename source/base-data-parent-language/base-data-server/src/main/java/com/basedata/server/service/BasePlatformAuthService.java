package com.basedata.server.service;

import com.github.pagehelper.PageInfo;
import com.basedata.common.dto.BasePlatformAuthDto;
import com.basedata.common.dto.BasePlatformAuthReqDTO;
import com.basedata.common.dto.UpdateStatusDto;
import com.basedata.common.vo.BasePlatformAuthQueryVo;
import com.basedata.common.vo.BasePlatformAuthVo;
import com.basedata.server.entity.BasePlatformAuth;
import com.basedata.server.vo.BasePlatformAuthUpdateVo;

import java.util.List;

/**
 * <p>
 * 货主平台授权信息表 服务类
 * </p>
 */
public interface BasePlatformAuthService {
    /**
     * 新增
     *
     * @param updateVo
     * @return
     */
    void save(BasePlatformAuthUpdateVo updateVo);

    /**
     * 更新
     *
     * @param updateVo
     * @return
     */
    void update(BasePlatformAuthUpdateVo updateVo);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteByIds(List<Long> ids);

    /**
     * 分页查询
     *
     * @param queryVo
     * @return
     */
    PageInfo<BasePlatformAuthVo> queryPageList(BasePlatformAuthQueryVo queryVo) throws Exception;

    /**
     * 查询列表（不分页）
     *
     * @param reqDTO
     * @return
     */
    List<BasePlatformAuth> queryValidList(BasePlatformAuthReqDTO reqDTO);

    /**
     * 批量启用停用
     *
     * @param statusDto
     */
    boolean batchEnableOrDisable(UpdateStatusDto statusDto);

    /**
     * 生成平台授权链接
     *
     * @param id
     * @return
     */
    String genPlatformAuthUrl(Long id);

    BasePlatformAuth findOneById(Long id);

    BasePlatformAuth findOne(Long serviceProviderId, Long ownerId, String platformCode);

    /**
     * 刷新token（从接口平台更新token）
     *
     * @param id
     * @return
     */
    boolean refreshToken(Long id);

    BasePlatformAuthDto queryByOwnerIdAndPlatformName(BasePlatformAuthQueryVo queryVo);

    /**
     * 授权后更新token相关信息
     *
     * @param basePlatformAuthDto
     */
    Boolean updateToken(BasePlatformAuthDto basePlatformAuthDto);
}
