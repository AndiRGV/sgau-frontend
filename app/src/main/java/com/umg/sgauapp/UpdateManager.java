package com.umg.sgauapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class UpdateManager {
    private static final String TAG = "UpdateManager";
    private final Activity activity;
    private final FirebaseRemoteConfig mFirebaseRemoteConfig;

    public UpdateManager(Activity activity) {
        this.activity = activity;
        this.mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    }

    public void checkForUpdates() {
        // 1. Configurar tiempo de fetch a 0 segundos para pruebas inmediatas
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();

        // 2. Aplicar la configuración y luego consultar Firebase
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                .addOnCompleteListener(task -> {
                    // Una vez aplicada la configuración a 0s, realizamos el fetch
                    mFirebaseRemoteConfig.fetchAndActivate()
                            .addOnCompleteListener(activity, fetchTask -> {
                                if (fetchTask.isSuccessful()) {
                                    Log.d(TAG, "Remote Config actualizado exitosamente.");
                                    checkVersionLogic();
                                } else {
                                    Log.e(TAG, "Error al consultar Remote Config.", fetchTask.getException());
                                }
                            });
                });
    }

    private void checkVersionLogic() {
        try {
            // Obtener versión actualmente instalada en la app
            String currentVersionStr = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionName;

            // Obtener parámetros configurados en Firebase
            String latestVersionStr = mFirebaseRemoteConfig.getString("latest_version");
            String minimumVersionStr = mFirebaseRemoteConfig.getString("minimum_version");
            String releaseUrl = mFirebaseRemoteConfig.getString("release_url");

            // Imprimir logs para depurar en Logcat y confirmar valores recibidos
            Log.d(TAG, "Versión Instalada: " + currentVersionStr);
            Log.d(TAG, "Firebase Latest: " + latestVersionStr);
            Log.d(TAG, "Firebase Minimum: " + minimumVersionStr);
            Log.d(TAG, "Firebase Release URL: " + releaseUrl);

            int currentVersion = parseVersion(currentVersionStr);
            int latestVersion = parseVersion(latestVersionStr);
            int minimumVersion = parseVersion(minimumVersionStr);

            // Evaluar escenarios de actualización
            if (currentVersion < minimumVersion) {
                // Actualización OBLIGATORIA
                Log.d(TAG, "Mostrando cuadro de actualización OBLIGATORIA.");
                showUpdateDialog(
                        "Actualización Obligatoria",
                        "Esta versión ya no es compatible. Debe actualizar la aplicación para continuar.",
                        releaseUrl,
                        true
                );
            } else if (currentVersion < latestVersion) {
                // Actualización RECOMENDADA
                Log.d(TAG, "Mostrando cuadro de actualización RECOMENDADA.");
                showUpdateDialog(
                        "Nueva versión disponible",
                        "Existe una nueva versión disponible. Se recomienda actualizar.",
                        releaseUrl,
                        false
                );
            } else {
                Log.d(TAG, "La versión instalada está al día. No se requiere diálogo.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al verificar la versión", e);
        }
    }

    private void showUpdateDialog(String title, String message, String releaseUrl, boolean isMandatory) {
        activity.runOnUiThread(() -> {
            // Se utiliza el AlertDialog nativo del sistema para evitar requerir AppCompat Theme
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
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

            android.app.AlertDialog dialog = builder.create();
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