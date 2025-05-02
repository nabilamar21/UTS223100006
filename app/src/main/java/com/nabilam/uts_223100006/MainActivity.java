package com.nabilam.uts_223100006;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private EditText urlInput;
    private Button showButton;
    private WebView webView;

    private static final String CHANNEL_ID = "uts_channel_id";
    private static final String NIM = "011100862"; // Ganti dengan NIM Anda
    private static final String NAMA = "Nama Anda"; // Ganti dengan nama Anda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.urlInput);
        showButton = findViewById(R.id.showButton);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        showButton.setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            if (!url.startsWith("http")) {
                url = "https://" + url;
            }
            webView.loadUrl(url);
            showNotification(url);
        });

        createNotificationChannel();
    }

    private void showNotification(String url) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Info WebView")
                .setContentText("NIM: " + NIM + ", Nama: " + NAMA + ", URL: " + url)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "UTS Notification";
            String description = "Channel untuk UTS WebView";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}