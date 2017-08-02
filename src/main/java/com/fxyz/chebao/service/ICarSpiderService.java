package com.fxyz.chebao.service;

import com.fxyz.chebao.pojo.carSpider.CarSeries;
import com.fxyz.chebao.pojo.carSpider.CarSeriesTemp;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */
public interface ICarSpiderService {
    /**
     * 根据首字母取得汽车品牌 厂商 车系
     * @param lettter
     */
    public void getCarBrandByCaption( String lettter);

    /**
     * 更新图片状态
     */
    public void getSeriesImgUrlById(int SeriesId);


        /**
         * 根据车系id取得停产车系的车型信息
         * @param SeriesId
         */
    public void getCarTypeStopSeriesById(int SeriesId);

    /**
     * 根据车系id取得在售车系停产车型的车型信息
     * @param SeriesId
     */
    public void getCarTypeStopSaleById(int SeriesId);

    /**
     * 根据车系id取得在售车系在售车型的车型信息
     * @param SeriesId
     */
    public void getCarTypeOnSaleById( int SeriesId );


    /**
     * 获取数据库临时表中所有的车系信息
     * @return
     */
    public List<CarSeriesTemp> getAllSeriesTemp();

    /**
     * 获取数据库正式表中所有的车系信息
     * @return
     */
    public List<CarSeries> getAllSeries();

    /**
     * 将图片保存本地
     * @param filepath  文件保存路径
     * @param filename  文件名
     * @param imgurl  抓取url地址
     */
    public void copySeriesUrlById(String filepath,String filename ,String imgurl);

    Document sendSeriesImgUrlById(int seriesId) throws IOException;

    void deocdeSeriesImgUrlById(Document doc,Integer seriesId );

    void decodeCarTypeStopSaleById(Document doc,Integer seriesId) throws Exception;

    Document sendCarType(int seriesId) throws IOException;

    void decodeCarTypeOnSaleById(Document doc,int seriesId);

    void decodeCarTypeStopSeriesById(Document doc,int seriesId);
}
