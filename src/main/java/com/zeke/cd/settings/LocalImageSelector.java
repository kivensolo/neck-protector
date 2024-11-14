package com.zeke.cd.settings;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * 本地图片选择器
 *
 * @author King.Z
 * @version v1.1.5
 * @since 2024/11/14
 */
public interface LocalImageSelector {

    /**
     * 支持的图片格式
     */
    List<String> MIME_OF_IMAGE = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");

    /**
     * 文件选择器的描述对象
     * 控制哪些文件被选中，这里限制文件为图片。
     */
    FileChooserDescriptor IMAGE_FILE_CHOOSER = new FileChooserDescriptor(
            true, false, false,
            false, false, false)
            .withFileFilter(virtualFile -> MIME_OF_IMAGE.contains(virtualFile.getExtension()));

    /**
     * Create an ActionListener
     * @param textField UI Component
     * @return Custom ActionListener
     */
    static ActionListener newImageActionListener(TextFieldWithBrowseButton textField){
        return actionEvent -> {
            LocalImageSelector.IMAGE_FILE_CHOOSER.setTitle("Image URL");
            LocalImageSelector.IMAGE_FILE_CHOOSER.setDescription("Choose the picture you like");

            VirtualFile chosenImageFile = FileChooser.chooseFile(
                    LocalImageSelector.IMAGE_FILE_CHOOSER,
                    ProjectManager.getInstance().getDefaultProject(),
                    null);
            if(chosenImageFile != null){
//                TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT.setText(textField, chosenImageFile.getPath());
                textField.setText(chosenImageFile.getPath());
            }
        };
    }
}
