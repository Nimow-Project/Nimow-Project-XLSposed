package com.nimowproject.nimowproject;

import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public final static List<String> hookPackages = Arrays.asList(
            "org.telegram.messenger",
            "org.telegram.messenger.web",
            "org.telegram.messenger.beta",
            "org.forkclient.messenger",
            "org.forkclient.messenger.beta",
            "org.telegram.BifToGram",
            "org.telegram.plus",
            "org.ilwt.lagatgram",
            "org.thunderdog.challegram",
            "org.nift4.catox",
            "org.aka.messenger",
            "com.cool2645.nekolite",
            "com.iMe.android",
            "com.exteragram.messenger",
            "com.exteragram.messenger.beta",
            "ellipi.messenger",
            "io.nekohasekai.dsm",
            "me.ninjagram.messenger",
            "nekox.messenger",
            "it.owlgram.android",
            "ir.ilmili.telegraph",
            "top.qwq2333.nullgram",
            "top.qwq2333.nullgram.beta",
            "ua.itaysonlab.messenger"
            );

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (hookPackages.contains(lpparam.packageName)) {
            try {
                Class<?> messagesControllerClass = XposedHelpers.findClassIfExists("org.telegram.messenger.MessagesController", lpparam.classLoader);
                if (messagesControllerClass != null) {
                    XposedBridge.hookAllMethods(messagesControllerClass, "getSponsoredMessages", XC_MethodReplacement.returnConstant(null));
                    XposedBridge.hookAllMethods(messagesControllerClass, "isChatNoForwards", XC_MethodReplacement.returnConstant(false));
                }
                Class<?> chatUIActivityClass = XposedHelpers.findClassIfExists("org.telegram.ui.ChatActivity", lpparam.classLoader);
                if (chatUIActivityClass != null) {
                    XposedBridge.hookAllMethods(chatUIActivityClass, "addSponsoredMessages", XC_MethodReplacement.returnConstant(null));
                }
                Class<?> SharedConfigClass = XposedHelpers.findClassIfExists("org.telegram.messenger.SharedConfig", lpparam.classLoader);
                if (SharedConfigClass != null) {
                    XposedBridge.hookAllMethods(SharedConfigClass, "getDevicePerformanceClass", XC_MethodReplacement.returnConstant(2));
                }
                Class<?> UserConfigClass = XposedHelpers.findClassIfExists("org.telegram.messenger.UserConfig", lpparam.classLoader);
                if (UserConfigClass != null) {
                    XposedBridge.hookAllMethods(UserConfigClass, "getMaxAccountCount", XC_MethodReplacement.returnConstant(999));
                    XposedBridge.hookAllMethods(UserConfigClass, "hasPremiumOnAccounts", XC_MethodReplacement.returnConstant(true));
                }
            } catch (Throwable ignored) {
            }
            try {
                Class<?> userConfigClass = XposedHelpers.findClassIfExists("org.telegram.messenger.UserConfig", lpparam.classLoader);
                if (userConfigClass != null) {
                    XposedBridge.hookAllMethods(userConfigClass, "isPremium", XC_MethodReplacement.returnConstant(true));
                }
            } catch (Throwable ignored) {
            }
        }
    }
}
