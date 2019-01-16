package com.pyq.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("roleId")
    private Integer roleId;

    @TableField("resourcesId")
    private String resourcesId;


}
