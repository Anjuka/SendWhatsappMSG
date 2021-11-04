package anjuka.poc.sendwhatsappmsg;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsappAccesessbilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        if (getRootInActiveWindow() == null){
            return;
        }

        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        List<AccessibilityNodeInfoCompat> massageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");

        if (massageNodeList == null || massageNodeList.isEmpty()){
            return;
        }

        AccessibilityNodeInfoCompat massageField = massageNodeList.get(0);

        if (massageField == null || massageField.getText().length() == 0 || massageField.getText().toString().endsWith("   ")){
            return;
        }

        List<AccessibilityNodeInfoCompat> sendMassageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");

        if (sendMassageNodeList == null || sendMassageNodeList.isEmpty()){
            return;
        }

        AccessibilityNodeInfoCompat sendMassege = sendMassageNodeList.get(0);

        if (!sendMassege.isVisibleToUser()){
            return;
        }

        sendMassege.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        try {
            Thread.sleep(2000);
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(2000);
        }
        catch (InterruptedException ignored){

        }

    }

    @Override
    public void onInterrupt() {

    }
}
