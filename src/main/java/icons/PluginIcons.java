package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * 插件自定义图标
 *
 * @author King.Z
 * @version 1.0
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html">IconsAndImages</a>
 * @since 2019-05-01
 */
public class PluginIcons{

    public static final class Actions {
        public static final Icon ZEKE_HEX = IconLoader.getIcon("/icons/color_table.png", PluginIcons.class.getClassLoader());
    }
}
