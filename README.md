## LBS
地理位置定位技术，并没有一个良好的开源实现，并在各大平台有不同程度的偏移量（不可预测）。为确保App在特定场景获取精确的LBS,而开发的一个系统

### 系统实现
整个系统分两部分，一为Android系统获取基站信息，二为后端通过某些算法（三边测量、三角定位等）进行计算，返回给客户端

### GPS定位

### 基站定位
通过TelephonyManager 获取lac:mcc:mnc:cell-id（基站信息）的解释：
*1. MCC，Mobile Country Code，移动国家代码（中国的为460）；
*2. MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）； 
*3. LAC，Location Area Code，位置区域码；
*4. CID，Cell Identity，基站编号；
*5. BSSS，Base station signal strength，基站信号强度。

MCC参考(wiki)https://zh.wikipedia.org/wiki/%E8%A1%8C%E5%8B%95%E8%A3%9D%E7%BD%AE%E5%9C%8B%E5%AE%B6%E4%BB%A3%E7%A2%BC]
实现需要的数据:中国电信CDMA SID NID对应表\联通\移动
