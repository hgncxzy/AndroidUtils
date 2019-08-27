### 简介

Android 封装好的工具类代码合集. 持续更新优化工具类的实现。

###  工具列表

| 类名                                                         | 描述                                                         | 用法 |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---- |
| [ActivityUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/activity/ActivityUtils.java) | 包含 Activity 组件的启动、结束、跳转(含动画)、获取栈顶、判断是否是 Activity 等方法。 |      |
| [AdaptScreenUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/adaptscreen/AdaptScreenUtils.java) | 横竖屏适配。                                                 |      |
| [AppUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/app/AppUtils.java) | App 的安装、启动、卸载、状态获取等。                         |      |
| [ArrayUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/array/ArrayUtils.java) | 包含数组常用操作方法。                                       |      |
| [AssertProvider](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/assert/AssertProvider.java) | 公开访问 assets 目录下面的资源文件。                         |      |
| [BarUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/bar/BarUtils.java) | 栏相关的工具类。隐藏/显示状态栏、设置/获取高度、颜色、自定义状态栏等方法。 |      |
| [BrightnessUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/brightness/BrightnessUtils.java) | 亮度设置、获取、自动获取亮度等方法。                         |      |
| [BroadcastUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/broadcast/BroadcastUtils.java) | 广播发送、注册、反注册、获取 intent。                        |      |
| [CacheDiskStaticUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/cache/CacheDiskStaticUtils.java) | 磁盘缓存相关工具类。                                         |      |
| [CacheDiskUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/cache/CacheDiskUtils.java) | 磁盘缓存相关工具类。                                         |      |
| [CacheDoubleStaticUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/cache/CacheDoubleStaticUtils.java) | 二级缓存相关工具类。                                         |      |
| [CacheDoubleUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/cache/CacheDoubleUtils.java) | 二级缓存相关工具类。                                         |      |
| [CacheMemoryStaticUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/cache/CacheMemoryStaticUtils.java) | 内存缓存相关工具类。                                         |      |
| [CacheMemoryUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/cache/CacheMemoryUtils.java) | 内存缓存相关工具类。                                         |      |
| [CalendarUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/calendar/CalendarUtils.java) | 获取农历日期相关、农历阳历转换、闰月、生肖等。               |      |
| [CameraUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/camera/CameraUtils.java) | 相机工具类、调用相机拍照、调用相机录像并保存到目录。         |      |
| [CleanUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/clean/CleanUtils.java) | 清理目录、缓存、数据库、文件等。                             |      |
| [ClickUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/click/ClickUtils.java) | 点击应用缩放动画、消除抖动(单次防抖、全局防抖)。             |      |
| [CloneUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/clone/CloneUtils.java) | 深度克隆。                                                   |      |
| [ColorUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/color/ColorUtils.java) | 设置红、绿、蓝、透明度、int2Argb、string2Int                 |      |
| [CollectionUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/connection/CollectionUtils.java) | 集合相关工具类。                                             |      |
| [ContainerUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/container/ContainerUtils.java) | 判断 Map、Array、Connection 是否为空。                       |      |
| [ConvertUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/convert/ConvertUtils.java) | 流转换、字节转换、px 转换、进制转换、图片转换等方法。        |      |
| [CountdownUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/countdown/CountdownUtils.java) | 倒计时工具类。                                               |      |
| [CrashHandler](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/crash/CrashHandler.java) | UncaughtException 处理类,当程序发生未捕获到异常的时候,由该类来接管程序, 并记录发送错误报告. 需要在 Application 中注册，为了要在程序启动时期就监控整个程序。使用方式  在Application 中初始化 CrashHandler#getInstance#init |      |
| [CrashUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/crash/CrashUtils.java) | 崩溃捕获相关工具类，将日志写入文件等。                       |      |
| [DateUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/date/DateUtils.java) | 日期解析、格式化、当前时间、日期对比操作等。                 |      |
| [DensityUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/density/DensityUtils.java) | 屏幕分辨率辅助类，px、sp、dp 相互转换。                      |      |
| [DeviceInfoUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/deviceinfo/DeviceInfoUtils.java) | 获取 Android 设备软硬件相关的设备信息。                      |      |
| [DeviceStatusUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/devicestatus/DeviceStatusUtils.java) | 手机状态工具类 主要包括网络、蓝牙、屏幕亮度、飞行模式、音量等。 |      |
| [EncodeUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/encode/EncodeUtils.java) | bin、html、base64、url 编码和解码。                          |      |
| [EncryptUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/encryp/EncryptUtils.java) | 加密和解密工具类。                                           |      |
| [FileUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/file/FileUtils.java) | 文件操作工具类。                                             |      |
| [FlashlightUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/flashlight/FlashlightUtils.java) | 闪光灯操作工具类。                                           |      |
| [FragmentUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/fragment/FragmentUtils.java) | Fragment 通用操作工具类。                                    |      |
| [GsonUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/gson/GsonUtils.java) | Gson 与对象转换工具类。                                      |      |
| [HttpUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/http/HttpUtils.java) | java、apache api实现的 http 工具类，包含 post、get 请求。    |      |
| [ImageUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/image/ImageUtils.java) | 圆角、水印、生成二维码、缩放、旋转、图片类型判断、压缩、保存等方法。 |      |
| [IntentUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/intent/IntentUtils.java) | 意图相关工具类。                                             |      |
| [IOUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/io/IOUtils.java) | 文件读写、流与字节的转换。                                   |      |
| [JsonUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/json/JsonUtils.java) | 从网络得到一段 json，并解析不同格式的 json 。                |      |
| [KeyboardUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/keyboard/KeyboardUtils.java) | 键盘工具类。                                                 |      |
| [LanguageUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/language/LanguageUtils.java) | 应用某种语言、引用系统语言、更新语言等方法。                 |      |
| [L](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/log/L.java) | 日志打印、日志写入磁盘。                                     |      |
| [LogUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/log/LogUtils.java) | 日志工具类。                                                 |      |
| [MapUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/map/MapUtils.java) | HashMap、LinkedHashMap、TreeMap、HashTable、判空、尺寸、Map 转换 |      |
| [MaskUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/mask/MaskUtils.java) | 遮罩层工具类。                                               |      |
| [MatchUtil](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/match/MatchUtil.java) | 正则匹配、数据校验工具类。                                   |      |
| [MD5Utils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/md5/MD5Utils.java) | 给定字符串，生成 md5。                                       |      |
| [MetaDataUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/metadata/MetaDataUtils.java) | 获取 App、activity、service 、receiver 的 meta-data 数据。   |      |
| [NetworkUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/networkj/NetworkUtils.java) | 网络状态判断、网络设置等方法。                               |      |
| [NfcUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/nfc/NfcUtils.java) | Nfc 工具类。                                                 |      |
| [ObjectUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/object/ObjectUtils.java) | 对象判空。                                                   |      |
| [PathUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/path/PathUtils.java) | 获取各种路径。                                               |      |
| [PermissionUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/permission/) | 权限工具类。                                                 |      |
| [PhoneUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/phone/PhoneUtils.java) | 与通讯录有关的工具类、包含获取设备 id，保存联系人、拨打电话、发送短信、获取序列号、IMEI 等。 |      |
| [PinyinUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/pinyin/PinyinUtils.java) | 根据名字获取姓氏的拼音、                                     |      |
| [ProcessUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/process/ProcessUtils.java) | 获取进程名、后台进程、当前进程、主进程、杀死进程等方法。     |      |
| [ReflectUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/reflect/ReflectUtils.java) | 反射相关的工具类。                                           |      |
| [ResourceUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/resource/ResourceUtils.java) | 流转文件、raw、assets 文件读取与操作等。                     |      |
| [RomUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/rom/RomUtils.java) | 判断是哪个厂商的系统。                                       |      |
| [ScreenUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/screen/ScreenUtils.java) | 获取屏幕宽高、状态栏宽高、当前屏幕截屏、App 宽高、屏幕密度、旋转度、横竖屏判断等方法。 |      |
| [SDCardUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/sdcard/SDCardUtils.java) | 获取 SD 卡路径、信息。                                       |      |
| [ServiceUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/service/ServiceUtils.java) | 服务的停止、启动、正在运行的服务、服务状态判断。             |      |
| [ShakeUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/shake/ShakeUtils.java) | 摇一摇工具类。                                               |      |
| [ShapeUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/shape/ShapeUtils.java) | 利用 Shape 绘制圆角矩形、圆形。                              |      |
| [ShellUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/shell/ShellUtils.java) | Shell 工具类，辅助执行 shell 命令。                          |      |
| [SizeUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/size/SizeUtils.java) | 尺寸相关的工具类。                                           |      |
| [SnackbarUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/snackbar/SnackbarUtils.java) | Snackbar 的用法。                                            |      |
| [SPUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/sp/SPUtils.java) | 各种类型的 put 与 get。                                      |      |
| [SpanUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/span/SpanUtils.java) | 富文本显示效果工具类。                                       |      |
| [StorageUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/storage/StorageUtils.java) | 和存储有关的工具类。                                         |      |
| [StringUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/string/StringUtils.java) | 字符串操作工具类，包含大小写转换、反转等方法。               |      |
| [ThemeUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/theme/ThemeUtils.java) | 解析主题的 Color、Boolean、Dimension 、Drawable、String 等属性。 |      |
| [ThreadUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/thread/ThreadUtils.java) | 线程相关的工具类。                                           |      |
| [ThreadPoolExecutors](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/thread/ThreadPoolExecutors.java) | 应用的全局线程池 （包括单线程池的磁盘io，多线程池的网络io和主线程）。 |      |
| [ThrowableUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/throwable/ThrowableUtils.java) | Throwable 相关的工具类。获取堆栈信息等。                     |      |
| [TimeUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/time/TimeUtils.java) | 时间处理工具类。                                             |      |
| [ToastUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/toast/ToastUtils.java) | 传统的 Toast 和自定义 Toast。                                |      |
| [UriUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/uri/UriUtils.java) | File 和 Uri 之间的转换操作。                                 |      |
| [VibrateUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/vibrate/VibrateUtils.java) | 震动相关的工具类。                                           |      |
| [VideoUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/video/VideoUtils.java) | 视频处理相关工具类。                                         |      |
| [ViewUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/view/ViewUtils.java) | 设置 View 可用与否、(延迟)运行在主线程、用于解决ScrollView嵌套ListView/GridView/WebView/RecyclerView等无法置顶问题 |      |
| [XmlUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/xml/XmlUtils.java) | XML 解析工具类。                                             |      |
| [ZipUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/zip/ZipUtils.java) | 压缩相关工具类。                                             |      |



### 参考项目

- [https://github.com/Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
- [https://github.com/xuexiangjys/XUtil](https://github.com/xuexiangjys/XUtil)

### 特别感谢

- [https://github.com/Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)

### 联系

1. ID : hgncxzy
2. 邮箱：[hgncxzy@qq.com](mailto:hgncxzy@qq.com)
3. 项目地址：https://github.com/hgncxzy/AndroidUtils


