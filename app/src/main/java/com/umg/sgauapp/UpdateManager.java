package com.umg.sgauapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
public class UpdateManager {
    private static final String TAG = "UpdateManager";
    private final Activity activity;
    private final FirebaseRemoteConfig mFirebaseRemoteConfig;

    public UpdateManager(Activity activity) {
        this.activity = activity;
        this.mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Intervalo de 0 segundos para que descargue los cambios de Firebase de inmediato en pruebas
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
    }

    public void checkForUpdates() {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Remote Config actualizado exitosamente.");
                        checkVersionLogic();
                    } else {
                        Log.e(TAG, "Error al consultar Remote Config.");
                    }
                });
    }

    private void checkVersionLogic() {
        try {
            // 1. Obtener versión actualmente instalada en la app
            String currentVersionStr = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionName;

            // 2. Obtener parámetros configurados en Firebase
            String latestVersionStr = mFirebaseRemoteConfig.getString("latest_version");
            String minimumVersionStr = mFirebaseRemoteConfig.getString("minimum_version");
            String releaseUrl = mFirebaseRemoteConfig.getString("release_url");

            int currentVersion = parseVersion(currentVersionStr);
            int latestVersion = parseVersion(latestVersionStr);
            int minimumVersion = parseVersion(minimumVersionStr);

            // 3. Evaluar escenarios de actualización
            if (currentVersion < minimumVersion) {
                // Actualización OBLIGATORIA
                showUpdateDialog(
                        "Actualización Obligatoria",
                        "Esta versión ya no es compatible. Debe actualizar la aplicación para continuar.",
                        releaseUrl,
                        true
                );
            } else if (currentVersion < latestVersion) {
                // Actualización RECOMENDADA
                showUpdateDialog(
                        "Nueva versión disponible",
                        "Existe una nueva versión disponible. Se recomienda actualizar.",
                        releaseUrl,
                        false
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al verificar la versión", e);
        }
    }

    private void showUpdateDialog(String title, String message, String releaseUrl, boolean isMandatory) {
        activity.runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(title)
                    .setMessage(message)
                    .setCancelable(!isMandatory)
                    .setPositiveButton("Actualizar", (dialog, which) -> {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(releaseUrl));
                        activity.startActivity(browserIntent);
                        if (isMandatory) {
                            activity.finish();
                        }
                    });

            if (!isMandatory) {
                builder.setNegativeButton("Más tarde", (dialog, which) -> dialog.dismiss());
            }

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(!isMandatory);
            dialog.show();
        });
    }
    private int parseVersion(String version) {
        if (version == null || version.isEmpty()) return 0;
        String[] parts = version.split("\\.");
        int num = 0;
        for (String part : parts) {
            num = num * 10 + Integer.parseInt(part.replaceAll("[^0-9]", ""));
        }
        return num;
    }

}
