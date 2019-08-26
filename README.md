### 简介

Android 封装好的工具类代码合集. 持续更新优化工具类的实现。

###  工具列表

| 类名                                                         | 描述                                                         | 用法 |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---- |
| [ActivityUtils](https://github.com/hgncxzy/AndroidUtils/blob/master/lib_util/src/main/java/com/xzy/utils/activity/ActivityUtils.java) | 包含 Activity 组件的启动、结束、跳转(含动画)、获取栈顶、判断是否是 Activity 等方法。 |      |
| AdaptScreenUtils                                             | 横竖屏适配。                                                 |      |
| AppUtils                                                     | App 的安装、启动、卸载、状态获取等。                         |      |
| ArrayUtils                                                   | 包含数组常用操作方法。                                       |      |
| AssertProvider                                               | 公开访问 assets 目录下面的资源文件。                         |      |
| BarUtils                                                     | 栏相关的工具类。隐藏/显示状态栏、设置/获取高度、颜色、自定义状态栏等方法。 |      |
| BrightnessUtils                                              | 亮度设置、获取、自动获取亮度等方法。                         |      |
| BroadcastUtils                                               | 广播发送、注册、反注册、获取 intent。                        |      |
| CacheDiskStaticUtils                                         | 磁盘缓存相关工具类。                                         |      |
| CacheDiskUtils                                               | 磁盘缓存相关工具类。                                         |      |
| CacheDoubleStaticUtils                                       | 二级缓存相关工具类。                                         |      |
| CacheDoubleUtils                                             | 二级缓存相关工具类。                                         |      |
| CacheMemoryStaticUtils                                       | 内存缓存相关工具类。                                         |      |
| CacheMemoryUtils                                             | 内存缓存相关工具类。                                         |      |
| CalendarUtils                                                | 获取农历日期相关、农历阳历转换、闰月、生肖等。               |      |
| CameraUtils                                                  | 相机工具类、调用相机拍照、调用相机录像并保存到目录。         |      |
| CleanUtils                                                   | 清理目录、缓存、数据库、文件等。                             |      |
| ClickUtils                                                   | 点击应用缩放动画、消除抖动(单次防抖、全局防抖)。             |      |
| CloneUtils                                                   | 深度克隆。                                                   |      |
| ColorUtils                                                   | 设置红、绿、蓝、透明度、int2Argb、string2Int                 |      |
| CollectionUtils                                              | 集合相关工具类。                                             |      |
| ContainerUtils                                               | 判断 Map、Array、Connection 是否为空。                       |      |
| ConvertUtils                                                 | 流转换、字节转换、px 转换、进制转换、图片转换等方法。        |      |
| CountdownUtils                                               | 倒计时工具类。                                               |      |
| CrashHandler                                                 | UncaughtException 处理类,当程序发生未捕获到异常的时候,由该类来接管程序, 并记录发送错误报告. 需要在 Application 中注册，为了要在程序启动时期就监控整个程序。使用方式  在Application 中初始化 CrashHandler#getInstance#init |      |
| CrashUtils                                                   | 崩溃捕获相关工具类，将日志写入文件等。                       |      |
| DateUtils                                                    | 日期解析、格式化、当前时间、日期对比操作等。                 |      |
| DensityUtils                                                 | 屏幕分辨率辅助类，px、sp、dp 相互转换。                      |      |
| DeviceInfoUtils                                              | 获取 Android 设备软硬件相关的设备信息。                      |      |
| DeviceStatusUtils                                            | 手机状态工具类 主要包括网络、蓝牙、屏幕亮度、飞行模式、音量等。 |      |
| EncodeUtils                                                  | bin、html、base64、url 编码和解码。                          |      |
| EncryptUtils                                                 | 加密和解密工具类。                                           |      |
| FileUtils                                                    | 文件操作工具类。                                             |      |
| FlashlightUtils                                              | 闪光灯操作工具类。                                           |      |
| FragmentUtils                                                | Fragment 通用操作工具类。                                    |      |
| GsonUtils                                                    | Gson 与对象转换工具类。                                      |      |
| HttpUtils                                                    | java、apache api实现的 http 工具类，包含 post、get 请求。    |      |
| ImageUtils                                                   | 圆角、水印、生成二维码、缩放、旋转、图片类型判断、压缩、保存等方法。 |      |
| IntentUtils                                                  | 意图相关工具类。                                             |      |
| IOUtils                                                      | 文件读写、流与字节的转换。                                   |      |
| JsonUtils                                                    | 从网络得到一段 json，并解析不同格式的 json 。                |      |
| KeyboardUtils                                                | 键盘工具类。                                                 |      |
| LanguageUtils                                                | 应用某种语言、引用系统语言、更新语言等方法。                 |      |
| L                                                            | 日志打印、日志写入磁盘。                                     |      |
| LogUtils                                                     | 日志工具类。                                                 |      |
| MapUtils                                                     | HashMap、LinkedHashMap、TreeMap、HashTable、判空、尺寸、Map 转换 |      |
| MaskUtils                                                    | 遮罩层工具类。                                               |      |
| MatchUtil                                                    | 正则匹配、数据校验工具类。                                   |      |
| MD5Utils                                                     | 给定字符串，生成 md5。                                       |      |
| MetaDataUtils                                                | 获取 App、activity、service 、receiver 的 meta-data 数据。   |      |
| NetworkUtils                                                 | 网络状态判断、网络设置等方法。                               |      |
| NfcUtils                                                     | Nfc 工具类。                                                 |      |
| ObjectUtils                                                  | 对象判空。                                                   |      |
| PathUtils                                                    | 获取各种路径。                                               |      |
| PermissionUtils                                              | 权限工具类。                                                 |      |
| PhoneUtils                                                   | 与通讯录有关的工具类、包含获取设备 id，保存联系人、拨打电话、发送短信、获取序列号、IMEI 等。 |      |
| PinyinUtils                                                  | 根据名字获取姓氏的拼音、                                     |      |
| ProcessUtils                                                 | 获取进程名、后台进程、当前进程、主进程、杀死进程等方法。     |      |
| ReflectUtils                                                 | 反射相关的工具类。                                           |      |
| ResourceUtils                                                | 流转文件、raw、assets 文件读取与操作等。                     |      |
| RomUtils                                                     | 判断是哪个厂商的系统。                                       |      |
| ScreenUtils                                                  | 获取屏幕宽高、状态栏宽高、当前屏幕截屏、App 宽高、屏幕密度、旋转度、横竖屏判断等方法。 |      |
| SDCardUtils                                                  | 获取 SD 卡路径、信息。                                       |      |
| ServiceUtils                                                 | 服务的停止、启动、正在运行的服务、服务状态判断。             |      |
| ShakeUtils                                                   | 摇一摇工具类。                                               |      |
| ShapeUtils                                                   | 利用 Shape 绘制圆角矩形、圆形。                              |      |
| ShellUtils                                                   | Shell 工具类，辅助执行 shell 命令。                          |      |
| SizeUtils                                                    | 尺寸相关的工具类。                                           |      |
| SnackbarUtils                                                | Snackbar 的用法。                                            |      |
| SPUtils                                                      | 各种类型的 put 与 get。                                      |      |
| SpanUtils                                                    | 富文本显示效果工具类。                                       |      |
| StorageUtils                                                 | 和存储有关的工具类。                                         |      |
| StringUtils                                                  | 字符串操作工具类，包含大小写转换、反转等方法。               |      |
| ThemeUtils                                                   | 解析主题的 Color、Boolean、Dimension 、Drawable、String 等属性。 |      |
| ThreadUtils                                                  | 线程相关的工具类。                                           |      |
| ThreadPoolExecutors                                          | 应用的全局线程池 （包括单线程池的磁盘io，多线程池的网络io和主线程）。 |      |
| ThrowableUtils                                               | Throwable 相关的工具类。获取堆栈信息等。                     |      |
| TimeUtils                                                    | 时间处理工具类。                                             |      |
| ToastUtils                                                   | 传统的 Toast 和自定义 Toast。                                |      |
| UriUtils                                                     | File 和 Uri 之间的转换操作。                                 |      |
| VibrateUtils                                                 | 震动相关的工具类。                                           |      |
| VideoUtils                                                   | 视频处理相关工具类。                                         |      |
| ViewUtils                                                    | 设置 View 可用与否、(延迟)运行在主线程、用于解决ScrollView嵌套ListView/GridView/WebView/RecyclerView等无法置顶问题 |      |
| XmlUtils                                                     | XML 解析工具类。                                             |      |
| ZipUtils                                                     | 压缩相关工具类。                                             |      |



### 参考项目

- [https://github.com/Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
- [https://github.com/xuexiangjys/XUtil](https://github.com/xuexiangjys/XUtil)

### 特别感谢

- [https://github.com/Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)

### 联系

1. ID : hgncxzy
2. 邮箱：[hgncxzy@qq.com](mailto:hgncxzy@qq.com)
3. 项目地址：https://github.com/hgncxzy/AndroidUtils


