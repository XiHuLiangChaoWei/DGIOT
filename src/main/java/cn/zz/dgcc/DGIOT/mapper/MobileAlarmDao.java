package cn.zz.dgcc.DGIOT.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MobileAlarmDao {

    ArrayList getSel();
}
