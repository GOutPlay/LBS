## LBS(基站定位)
地理位置定位技术，并没有一个良好的开源实现，并在各大平台有不同程度的偏移量（不可预测）。为确保App在特定场景获取精确的LBS,而开发的一个系统

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

[考资料](ttp://m.wendangku.net/doc/38819619a2161479171128cf.html)

一、 什么是LAC：
Location Area Code(LAC)地区区域码，用来划分区域 

二、 什么是CellID:
Cell Tower ID(Cid)CellID代表一个移动基站 
LAC码、Cid码是基站定位的必要参数，这两个缺一不可，否则不能基站定位；


通过TelephonyManager 获取lac:mcc:mnc:cell-id（基站信息）的解释：
*1. MCC，Mobile Country Code，移动国家代码（中国的为460）；

*2. MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）； 

*3. LAC，Location Area Code，位置区域码；

*4. CID，Cell Identity，基站编号；

*5. BSSS，Base station signal strength，基站信号强度。

MCC参考[wiki](https://zh.wikipedia.org/wiki/%E8%A1%8C%E5%8B%95%E8%A3%9D%E7%BD%AE%E5%9C%8B%E5%AE%B6%E4%BB%A3%E7%A2%BC)

实现需要的数据:中国电信CDMA SID NID对应表\联通\移动

### [中国联通](http://doc.mbalib.com/view/830d311cf94560e0bdcd895ae6b49a8d.html)

### [开放基站数据](http://opencellid.org/)

### 收集基站数据原理
卫星GPS结合多基站信号强度的定位方式，以解决基站在数据库中不存在时，可以提示用户开启GPS。然后将定位数据以及基础一同提交数据库，以完善基站数据库。


###### 参考资料
[如何通过AT指令获取基站定位](http://www.mamicode.com/info-detail-381838.html)
