package com.zeke.cd.actions;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.AsyncResult;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import com.zeke.cd.images.managers.BaseImageManager;
import com.zeke.cd.utils.Utils;

import javax.swing.*;
import java.net.URL;

/**
 * 显示十六进制透明度图片的Action
 */
public class ShowOpacityTableAction extends AnAction {
    private static final Logger LOG = Logger.getInstance(ShowOpacityTableAction.class);
    @Override
    public void actionPerformed(AnActionEvent e) {

        DataManager.getInstance().getDataContextFromFocus()
                .doWhenDone((Consumer<DataContext>) this::openHexOpacityPic)
                .doWhenRejected(LOG::error);
    }

    @SuppressWarnings("Duplicates")
    private void openHexOpacityPic(DataContext dataContext) {
        // 1. 获取 IDEA 正在使用的 Project
        Project currentProject = dataContext.getData(PlatformDataKeys.PROJECT);
        if (currentProject == null) {
            LOG.warn("currentProject cannot be null");
            return;
        }
        URL picUrl = BaseImageManager.getHexComparisonPicUrl();
        VirtualFile image = VfsUtil.findFileByURL(picUrl);
        if (image == null) {
            LOG.error("Cannot find image by URL: " + picUrl.toString());
            return;
        }
        // 3. 获取当前 Project 中，正在使用的 EditorWindow
        FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(currentProject);
        EditorWindow currentWindow = fileEditorManager.getCurrentWindow();
        if (currentWindow == null || currentWindow.getTabCount() == 0) {
            // 3.1 如果没有打开 EditorWindow 或者 EditorWindow 打开的 tab 为零，那就直接打开图片
            fileEditorManager.openFile(image, true);
        } else {
            // 4 获取下一个 EditorWindow
            EditorWindow nextWindow = fileEditorManager.getNextWindow(currentWindow);
            if (nextWindow == currentWindow) {
                // 4.1 如果下一个 EditorWindow 还是它自己，表示 IDEA 只打开了一个 EditorWindow
                // 4.1 那则需要创建一个垂直分屏，再打开图片
                currentWindow.split(SwingConstants.VERTICAL, true, image, true);
            } else {
                // 4.2 在下一个 EditorWindow 打开图片
                fileEditorManager.openFileWithProviders(image, true, nextWindow);
            }
        }
        LOG.info("hex image has been opened");
    }
}
