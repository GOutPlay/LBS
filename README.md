## LBS(基站定位)
基站定位是指手机发射基站根据与手机的距离来计算手机坐标地理位置的一种功能，基站定位一般应用于手机用户，手机基站定位服务又叫做移动位置服务（LBS服务），它是通过电信移动运营商的网络（如GSM网）获取移动终端用户的位置信息（经纬度坐标），在电子地图平台的支持下，为用户提供相应服务的一种增值业务。 

>如何查看手机基站信息： 1. Android智能机：在拨打电话界面输入\*#\*#4636#\*#\* 查看相应的基站信息，一般为16进制，请选择1进制进行查询。 2. IPhone：在拨打电话界面输入*3001#12345#*查看相应的基站信息

* Android 获取基站信息 参考[TelephonyManager](https://developer.android.google.cn/reference/android/telephony/TelephonyManager.html)

## 三大网络运营商的网络制式对应如下：
移动2G 网 --> GSM

移动3G 网 --> TD-SCDMA

电信2G 网 --> CDMA

电信3G 网 --> CDMA2000

联通2G 网 --> GSM

联通3G 网 --> WCDMA

由此可见移动，联通2G 网都可使用GsmCellLocation,电信2G,3G网则使用CdmaCellLocation,那么移动3G和联通3G又当如何,移动3G网也可使用GsmCellLocation，听说是TD-SCDMA衍生于GSM.

[参考资料](ttp://m.wendangku.net/doc/38819619a2161479171128cf.html)

一、 什么是LAC：
Location Area Code(LAC)地区区域码，用来划分区域 

二、 什么是CellID:
Cell Tower ID(Cid)CellID代表一个移动基站 
LAC码、Cid码是基站定位的必要参数，这两个缺一不可，否则不能基站定位；


通过TelephonyManager 获取lac:mcc:mnc:cell-id（基站信息）的解释：

* 1. MCC，Mobile Country Code，移动国家代码（中国的为460）；

* 2. MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）； 

* 3. LAC，Location Area Code，位置区域码；

* 4. CID，Cell Identity，基站编号；

* 5. BSSS，Base station signal strength，基站信号强度。

MCC参考[wiki](https://zh.wikipedia.org/wiki/%E8%A1%8C%E5%8B%95%E8%A3%9D%E7%BD%AE%E5%9C%8B%E5%AE%B6%E4%BB%A3%E7%A2%BC)

实现需要的数据:中国电信CDMA SID NID对应表\联通\移动

### [中国联通](http://doc.mbalib.com/view/830d311cf94560e0bdcd895ae6b49a8d.html)

### [开放基站数据](http://opencellid.org/downloads/?apiKey=efad37b3-aaba-4c5f-96e2-f16be564c49f)

### 收集基站数据原理
卫星GPS结合多基站信号强度的定位方式，以解决基站在数据库中不存在时，可以提示用户开启GPS。然后将定位数据以及基础一同提交数据库，以完善基站数据库。


###### 参考资料
[如何通过AT指令获取基站定位](http://www.mamicode.com/info-detail-381838.html)

## 天朝坐标相关转换算法

### WGS(GPS) 转换火星坐标
```c++
using System;

namespace Navi
{
    class EvilTransform
    {
        const double pi = 3.14159265358979324;

        //
        // Krasovsky 1940
        //
        // a = 6378245.0, 1/f = 298.3
        // b = a * (1 - f)
        // ee = (a^2 - b^2) / a^2;
        const double a = 6378245.0;
        const double ee = 0.00669342162296594323;

        //
        // World Geodetic System ==> Mars Geodetic System
        public static void transform(double wgLat, double wgLon, out double mgLat, out double mgLon)
        {
            if (outOfChina(wgLat, wgLon))
            {
                mgLat = wgLat;
                mgLon = wgLon;
                return;
            }
            double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
            double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
            double radLat = wgLat / 180.0 * pi;
            double magic = Math.Sin(radLat);
            magic = 1 - ee * magic * magic;
            double sqrtMagic = Math.Sqrt(magic);
            dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
            dLon = (dLon * 180.0) / (a / sqrtMagic * Math.Cos(radLat) * pi);
            mgLat = wgLat + dLat;
            mgLon = wgLon + dLon;
        }

        static bool outOfChina(double lat, double lon)
        {
            if (lon < 72.004 || lon > 137.8347)
                return true;
            if (lat < 0.8293 || lat > 55.8271)
                return true;
            return false;
        }

        static double transformLat(double x, double y)
        {
            double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.Sqrt(Math.Abs(x));
            ret += (20.0 * Math.Sin(6.0 * x * pi) + 20.0 * Math.Sin(2.0 * x * pi)) * 2.0 / 3.0;
            ret += (20.0 * Math.Sin(y * pi) + 40.0 * Math.Sin(y / 3.0 * pi)) * 2.0 / 3.0;
            ret += (160.0 * Math.Sin(y / 12.0 * pi) + 320 * Math.Sin(y * pi / 30.0)) * 2.0 / 3.0;
            return ret;
        }

        static double transformLon(double x, double y)
        {
            double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.Sqrt(Math.Abs(x));
            ret += (20.0 * Math.Sin(6.0 * x * pi) + 20.0 * Math.Sin(2.0 * x * pi)) * 2.0 / 3.0;
            ret += (20.0 * Math.Sin(x * pi) + 40.0 * Math.Sin(x / 3.0 * pi)) * 2.0 / 3.0;
            ret += (150.0 * Math.Sin(x / 12.0 * pi) + 300.0 * Math.Sin(x / 30.0 * pi)) * 2.0 / 3.0;
            return ret;
        }
    }
}
```

### 火星坐标到百度坐标的转换

```C++
const double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

//将 GCJ-02 坐标转换成 BD-09 坐标
void bd_encrypt(double gg_lat, double gg_lon, double &bd_lat, double &bd_lon)
{
    double x = gg_lon, y = gg_lat;
    double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
    double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
    bd_lon = z * cos(theta) + 0.0065;
    bd_lat = z * sin(theta) + 0.006;
}

//将 BD-09 坐标转换成  GCJ-02坐标
void bd_decrypt(double bd_lat, double bd_lon, double &gg_lat, double &gg_lon)
{
    double x = bd_lon - 0.0065, y = bd_lat - 0.006;
    double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
    double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
    gg_lon = z * cos(theta);
    gg_lat = z * sin(theta);
}
```

# EXIF2.31
> 在手机中，打开文件夹软件可以看到照片是按日期排序的，但是如何按GPS\日期排序呢？以下是照片EXIF中存放的信息:
```javascript
{
    "Compression": {"value": "6"},
    "DateTime": {"value": "2015:02:11 15:38:27"},
    "ExifTag": {"value": "2212"},
    "FileSize": {"value": "23471"},
    "Format": {"value": "jpg"},
    "GPSLatitude": {"value": "0deg "},
    "GPSLatitudeRef": {"value": "North"},
    "GPSLongitude": {"value": "0deg "},
    "GPSLongitudeRef": {"value": "East"},
    "GPSMapDatum": {"value": "WGS-84"},
    "GPSTag": {"value": "4292"},
    "GPSVersionID": {"value": "2 2 0 0"},
    "ImageHeight": {"value": "333"},
    "ImageWidth": {"value": "424"},
    "JPEGInterchangeFormat": {"value": "4518"},
    "JPEGInterchangeFormatLength": {"value": "3232"},
    "Orientation": {"value": "7"},
    "ResolutionUnit": {"value": "2"},
    "Software": {"value": "Microsoft Windows Photo Viewer 6.1.7600.16385"},
    "XResolution": {"value": "96/1"},
    "YResolution": {"value": "96/1"}
}
```
### [EXIF  具体函意](http://code.ciaoca.com/javascript/exif-js/)
### 查看EXIF信息
1. vi -b datafile
2. vim command model: %!xxd
```
00000000: ffd8 ffe0 0010 4a46 4946 0001 0100 0001  ......JFIF......
00000010: 0001 0000 ffe1 0008 4578 6966 0000 ffdb  ........Exif....
00000020: 0043 0005 0304 0404 0305 0404 0405 0505  .C..............
00000030: 0607 0c08 0707 0707 0f0b 0b09 0c11 0f12  ................
00000040: 1211 0f11 1113 161c 1713 141a 1511 1118  ................
```
### 利用exif解决ios手机上传竖拍照片旋转90度及视频问题
### Exif是空的
通过QQ空间，微博，微信朋友圈等上传的照片的exif信息是会被去除掉的
# 参考资料 

* [地球坐标-火星坐标-百度坐标及之间的转换算法 C# - kelite](http://www.tuicool.com/articles/JzAVj2)

* [GOOGLE wgtochina_lb](https://www.google.com.tw/search?q=wgtochina_lb&oq=wgtochina_lb&aqs=chrome..69i57&sourceid=chrome&ie=UTF-8)

* [OSS文档](https://help.aliyun.com/document_detail/32250.html?spm=a2c4g.11186623.6.1039.4jJRBR)

* [ExifInfo](http://oss-attachment.cn-hangzhou.oss.aliyun-inc.com/DC-008-Translation-2016-E.pdf?spm=a2c4g.11186623.2.4.hKAmfz&file=DC-008-Translation-2016-E.pdf)

* [地理标记图像](https://www.exif.org/geotagging.html)

* exif.org
