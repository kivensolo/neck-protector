package com.zeke.cd;

import com.intellij.openapi.components.ProjectComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Project组件接口
 *
 * <p>依赖于 Intellij Platform 组件实现</p>
 * 暂未使用
 * @author King.Z
 * @version 1.0
 * @since 2019-05-1 12:54
 */
public interface IProjectComponent extends ProjectComponent {

    /**
     * Component initialization logic
     */
    @Override
    void initComponent();

    @NotNull
    @Override
    String getComponentName();

    /**
     * Called when project is opened
     */
    @Override
    void projectOpened();

    /**
     * Called when project is being closed
     */
    @Override
    void projectClosed();
}
