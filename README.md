# NeckProtector
### A  IntelliJ IDEA plugin that reminds you to rest.

定时提醒工作中的你注意休息，活动活动肩颈，远离各种肩颈毛病~~


Features 特性列表
----
* [x] Timed reminders / 定时提醒
* [x] Customizable reminder pictures (support Bing daily photos) / 可自定义提醒图片(支持Bing每日美图)
* [x] Customizable reminder type / 可自定义提醒方式
* [x] Customizable reminder interval / 可自定义提醒时间
* [x] Customizable reminder text content / 可自定义提醒文案
* [x] Automatically get daily Bing search beautiful pictures / 自动获取每日Bing搜索精美图片

Installation / 安装
----
- **Install using the IDE built-in plugin system / 使用 IDE 内置插件系统安装:**
  - <kbd>进入IDE设置页面(Settings)</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>搜索并找到"NeckProtector"</kbd> > <kbd>Install Plugin</kbd>

Restart / 重启**IDE**.

Configuration / 配置
----
![setting](doc/settings.png)



Additional tool / 附加工具
----
- Hexadecimal table corresponding to opacity 不透明度对应的16进制表<br>
![opacity](doc/opacity.png)
![table](doc/table.png)

Notes / 注意事项
----
- When running in sandbox mode, The following configurations are needed to ensure the validity of file paths in sandbox environments.
<br> `PluginDefaultConfig. SANDBOX_MODE = true` <br>
沙箱模式下运行时，需设置 `PluginDefaultConfig.SANDBOX_MODE = true` . 以保证沙箱环境下文件路径的有效性。
