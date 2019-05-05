package com.zeke.cd.service;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

/**
 * {@link IConfigService} 实现类
 *
 * @author King.Z
 * @version 1.0
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-28
 */
@State(name = "neckProtector", storages = @Storage("neckProtector.xml"))
public class ConfigServiceImpl implements IConfigService {
    private ConfigState configState;

    public ConfigServiceImpl() {
        this.configState = new ConfigState();
    }

    /**
     * {@inheritDoc}
     *
     * <p>若用户是第一次开启插件时，则使用默认配置对象 {@code new ConfigState()}，
     * 否则使用从 {@code neckProtector.xml} 配置文件中解析生成的配置对象</p>
     */
    @NotNull
    @Override
    public ConfigState getState() {
        return this.configState;
    }

    /**
     * {@inheritDoc}
     *
     * <p>调用此方法，会将与默认数据不一致的配置数据
     * 持久化至 {@code neckProtector.xml} 配置文件中</p>
     */
    @Override
    public void updateState(@NotNull ConfigState state) {
        this.configState = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadState(@NotNull ConfigState state) {
        XmlSerializerUtil.copyBean(state, this.configState);
    }

    @NotNull
    public String getServiceName(){
        return ConfigServiceImpl.class.getSimpleName();
    }
}
