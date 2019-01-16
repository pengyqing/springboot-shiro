package com.pyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyq.entity.Resources;
import com.pyq.mapper.ResourcesMapper;
import com.pyq.service.ResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
@Service("resourcesService")
@Slf4j
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements ResourcesService {

    @Resource
    private ResourcesMapper resourcesMapper;

    @Override
    public IPage<Resources> selectByPage(Resources resources, int start, int length) {
        Page<Resources> page = new Page<>(start, length);
        log.info("总条数:"+start);
        IPage<Resources> resourcesList = resourcesMapper.selectPage(page,new QueryWrapper<Resources>(resources));
        log.info("总条数:"+resourcesList.getTotal());
        return resourcesList;
    }

    @Override
    public List<Resources> queryAll() {
        return resourcesMapper.queryAll();
    }

    @Override
    public List<Resources> loadUserResources(Map<String, Object> map) {
        return resourcesMapper.loadUserResources(map);
    }

    @Override
    public List<Resources> queryResourcesListWithSelected(Integer rid) {
        return resourcesMapper.queryResourcesListWithSelected(rid);
    }
}
