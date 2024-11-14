package com.zeke.cd.images;

import com.intellij.ide.DataManager;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

@SuppressWarnings("UnstableApiUsage")
public class OpenImageConsumerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testAccept() {
        DataManager.getInstance().getDataContextFromFocusAsync()
                .onSuccess(dataContext -> new OpenImageConsumer().accept(dataContext))
                .onError(LOG::error);
    }
}