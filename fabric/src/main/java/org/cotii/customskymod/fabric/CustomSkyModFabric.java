package org.cotii.customskymod.fabric;

import java.lang.reflect.*;

public final class CustomSkyModFabric {
    private static boolean initialized;

    public CustomSkyModFabric() {
        if (initialized) return;
        initialized = true;
        registerClientTick();
        registerClientCommands();
    }

    private static void registerClientTick() {
        try {
            Class<?> events = Class.forName("net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents");
            Object event = events.getField("END_CLIENT_TICK").get(null);
            registerEvent(event, "net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTick", args -> tick());
        } catch (Throwable ignored) {}
    }

    private static void registerClientCommands() {
        try {
            Class<?> cb = Class.forName("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");
            Object event = cb.getField("EVENT").get(null);
            registerEvent(event, cb.getName(), args -> {
                if (args != null && args.length > 0) invokeSkyboxCommandRegister(args[0]);
            });
        } catch (Throwable ignored) {}
    }

    private static void registerEvent(Object event, String callbackName, Handler handler) throws Exception {
        Class<?> callback = Class.forName(callbackName);
        Object proxy = Proxy.newProxyInstance(callback.getClassLoader(), new Class<?>[]{callback}, (p, method, args) -> {
            handler.run(args);
            return null;
        });
        Method register = event.getClass().getMethod("register", Object.class);
        register.invoke(event, proxy);
    }

    private static void tick() {
        try {
            invokeStatic("org.cotii.customskymod.client.config.SkyboxConfig", "load");
            Object textures = invokeStatic("org.cotii.customskymod.client.texture.AnimatedTextureRegistry", "get");
            textures.getClass().getMethod("tick").invoke(textures);
            Object manager = invokeStatic("org.cotii.customskymod.client.sky.SkyboxManager", "get");
            manager.getClass().getMethod("tickBiomeTransition").invoke(manager);
        } catch (Throwable ignored) {}
    }

    private static void invokeSkyboxCommandRegister(Object dispatcher) {
        try {
            Class<?> command = Class.forName("org.cotii.customskymod.client.command.SkyboxCommand");
            for (Method m : command.getMethods()) {
                if (m.getName().equals("register") && m.getParameterCount() == 1) {
                    m.invoke(null, dispatcher);
                    return;
                }
            }
        } catch (Throwable ignored) {}
    }

    private static Object invokeStatic(String className, String method) throws Exception {
        Class<?> c = Class.forName(className);
        return c.getMethod(method).invoke(null);
    }

    @FunctionalInterface
    private interface Handler { void run(Object[] args); }
}
