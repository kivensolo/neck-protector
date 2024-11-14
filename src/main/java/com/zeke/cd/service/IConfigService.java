package com.zeke.cd.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;

/**
 * 配置参数持久化组件服务
 *
 * <p>依赖于 Intellij Platform 的 {@link PersistentStateComponent} 组件实现</p>
 *
 * @author King.Z
 * @version 1.0
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_services.html">Plugin Services</a>
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">PersistentStateComponent</a>
 * @since 2019-04-28
 *
 */
public interface IConfigService extends PersistentStateComponent<ConfigState> {

    /** 由 Intellij Platform 保证的单例模式 */
    static IConfigService getInstance() {
        return ApplicationManager.getApplication().getService(IConfigService.class);
    }

    /**
     * 获取插件配置
     * @return {@link ConfigState}
     */
    @NotNull
    ConfigState getState();

    /**
     * 修改插件配置
     * @param state 将被保存的新配置对象
     */
    void updateState(@NotNull ConfigState state);
}