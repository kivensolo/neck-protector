package com.zeke.cd.images;

import com.intellij.ide.DataManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class OpenImageConsumerTest extends BasePlatformTestCase {

    public void testAccept() {
        DataManager.getInstance().getDataContextFromFocusAsync()
                .onSuccess(dataContext -> new OpenImageConsumer().accept(dataContext))
                .onError(LOG::error);
    }
}