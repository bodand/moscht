/**
 * This is the Windows-native source file that generates native notifications.
 */

#include <jni.h>
#include <windows.h>
#include <comdef.h>
#include <Shobjidl.h>

namespace {
    jint throwSystemError(JNIEnv* env, HRESULT hr) {
        constexpr static const char* class_name = "hu/kszi2/moscht/notify/SystemError";

        jclass ex_class = env->FindClass(class_name);
        if (!ex_class) {
            // idk, cry
            return 1;
        }

        _com_error com_err(hr);
        LPCTSTR err_msg = com_err.ErrorMessage();

        return env->ThrowNew(ex_class, err_msg);
    }
}

extern "C"
JNIEXPORT void JNICALL Java_hu_kszi2_moscht_notify_NativeNotifier_enable(
      JNIEnv* env,
      jobject self) {
    auto hr = CoInitializeEx(nullptr,
                             COINIT_MULTITHREADED
                             | COINIT_DISABLE_OLE1DDE
                             | COINIT_SPEED_OVER_MEMORY);
    if (FAILED(hr)) {
        throwSystemError(env, hr);
        return;
    }
}

extern "C"
JNIEXPORT void JNICALL Java_hu_kszi2_moscht_notify_NativeNotifier_disable(
      JNIEnv* env,
      jobject self) {
    CoUninitialize();
}

extern "C"
JNIEXPORT void JNICALL Java_hu_kszi2_moscht_notify_NativeNotifier_sendNotification(
      JNIEnv* env,
      jobject self,
      jstring message) {
    IUserNotification* notif = nullptr;
    // This CLSCTX_INPROC is the best we can get without writing a whole COM server
    auto hr = CoCreateInstance(CLSID_UserNotification, nullptr, CLSCTX_INPROC, IID_PPV_ARGS(&notif));
    if (FAILED(hr)) {
        throwSystemError(env, hr);
        return;
    }

    // Java strings are UTF-16 just like Windows wants it to be, we just need to
    // cast it to WCHAR so that Windows can eat it
    const auto* javaMessage = env->GetStringChars(message, false);
    const auto* windowsedMessage = reinterpret_cast<const WCHAR*>(javaMessage);

    notif->SetBalloonRetry(1, 1, 0);
    notif->SetBalloonInfo(L"moscht", windowsedMessage, NIIF_INFO);
    notif->Show(nullptr, 0);
    notif->Release();
}
