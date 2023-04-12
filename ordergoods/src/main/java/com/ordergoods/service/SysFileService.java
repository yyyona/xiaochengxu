package com.ordergoods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ordergoods.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 附件表，存放文件 服务类
 * </p>
 *
 * @author yan
 * @since 2023-02-10
 */
public interface SysFileService extends IService<SysFile> {

    SysFile saveOrUpdateFile(SysFile sysFile, MultipartFile file);

    SysFile saveOnly(SysFile sysFile, MultipartFile file);

}
