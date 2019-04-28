import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * author: King.Z <br>
 * date:  2019/4/25 21:43 <br>
 * description: 配置Action <br>
 */
public class ConfigAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String txt = Messages.showInputDialog(project,
                "What is your name?",
                "Input Your Name",
                Messages.getQuestionIcon());
        Messages.showMessageDialog(project,
                "Hello, " + txt + "!\n I am glad to see you.",
                "Information",
                Messages.getInformationIcon());
    }
}
