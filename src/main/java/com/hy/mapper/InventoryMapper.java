package com.hy.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.bean.Inventory;
import com.hy.bean.Purchase;
import com.hy.util.InventorySql;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InventoryMapper extends BaseMapper<Inventory> {
    @Select("select * from inventory order by amount desc")
    public IPage<Inventory> Inventory(Page page);

    @SelectProvider(type = InventorySql.class,method = "query")
    public List<Inventory> queryBy(Page page, @Param("em")Inventory inventory);

    @Insert("insert into inventory(number,name,cas,create_time,update_time,amount,remark) values(#{number},#{name},#{cas},now(),now(),#{amount},#{remark})")
    public int insertinventory(Inventory inventory);

    @Select("select * from inventory where number=#{number}")
    public Inventory selectnumber(@Param("number")String number);

    @Select("select * from inventory where amount<=0")
    public IPage<Inventory> select1(Page page);


    @Select("select * from inventory where cas=#{cas}")
    public Inventory  selectcas(@Param("cas")String cas);

    @Select("select * from inventory where cas=#{cas} and number=#{number}")
    public Inventory select(@Param("number")String number,@Param("cas")String cas);

    @Select("select * from inventory where cas=#{cas} and number=#{number}")
    public Inventory selecttwo(@Param("number")String number,@Param("cas")String cas);

    @Update("update inventory set amount=#{amount} where number=#{number}")
    public int updateinventory(@Param("amount") Double amount,@Param("number")String number);

    @Delete("delete from inventory where number=#{number}")
     public Inventory detelep(@Param("number")String number);


    @Update("update inventory set amount=amount+#{amount}  where kid=#{kid}")
    public boolean autoUpdate(@Param("amount") Double amount, @Param("kid")Integer kid);

    @Update("update inventory set amount=amount-#{amount}  where kid=#{kid}")
    public boolean jUpdate(@Param("amount") Double amount, @Param("kid")Integer kid);

    @Update("update inventory set amount=amount-#{amount}  where number=#{number}")
    public boolean aUpdate(@Param("amount") Double amount, @Param("number")String number);



    @Update("update inventory set remark=#{remark}  where kid=#{kid}")
    public boolean UpdateRe(@Param("remark") String remark, @Param("kid")Integer kid);

}
