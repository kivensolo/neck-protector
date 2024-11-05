package icons

import com.intellij.openapi.util.IconLoader.getIcon
import com.intellij.util.ReflectionUtil

/**
 * 插件自定义图标
 *
 * @author King.Z
 * @version 1.0
 * @see [IconsAndImages](https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html)
 *
 * @since 2019-05-01
 * @update 2024-11-05
 */
class PluginIcons {
    object Actions {
        private const val hexPath = "/icons/color_table.png"
        val ZEKE_HEX = getIcon(hexPath, ReflectionUtil.getGrandCallerClass() ?: error(hexPath))
    }
}
