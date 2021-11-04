package anjuka.poc.sendwhatsappmsg;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText messageEditText;
    Button submit;
    String message;
    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEditText = findViewById(R.id.message);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 message
                        = messageEditText
                        .getText()
                        .toString();

                 num = "+94715170928";

                // Calling the function
                // to send message


                if (!isAccessibilityOn(getApplicationContext(), WhatsappAccesessbilityService.class)){
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+message));
                intent.setPackage("com.whatsapp");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                boolean isWhatsappInstall = isAppInstall("com.whatsapp");

/*                if (isWhatsappInstall){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+message));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Please install whatsapp first.",
                            Toast.LENGTH_SHORT)
                            .show();
                }*/
            }
        });
    }

    private boolean isAppInstall(String s) {

        PackageManager packageManager = getPackageManager();
        boolean isInstall =true;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            isInstall = true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstall = false;
            e.printStackTrace();
        }

        return isInstall;
    }

    private boolean isAccessibilityOn (Context context, Class<? extends AccessibilityService> clazz) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + clazz.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}