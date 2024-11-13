package com.zeke.cd.images;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import com.intellij.util.Consumer;

@SuppressWarnings("ALL")
public class OpenImageConsumerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testAccept() {
        DataManager.getInstance().getDataContextFromFocus()
                .doWhenDone((Consumer<DataContext>) (dataContext -> new OpenImageConsumer().accept(dataContext)))
                .doWhenRejected((Consumer<String>) System.out::println);
    }
}