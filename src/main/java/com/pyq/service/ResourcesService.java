package com.pyq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pyq.entity.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */

public interface ResourcesService extends IService<Resources> {

    IPage<Resources> selectByPage(Resources resources, int start, int length);

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String,Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);

}
