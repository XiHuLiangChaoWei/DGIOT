package cn.zz.dgcc.DGIOT.mapper;


import cn.zz.dgcc.DGIOT.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface QuartzJobMapper {

    void saveQuartzJob(QuartzJob quartzJob);

    List<QuartzJob> selectAll();

    QuartzJob selectQuartzJobById(String id);

    void deleteQuartzJob(String id);

    void updateQuartzJob(QuartzJob quartzJob);

}