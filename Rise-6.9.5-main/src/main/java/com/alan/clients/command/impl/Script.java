package com.alan.clients.command.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;
import com.alan.clients.compat.NetworkToggles;
import com.alan.clients.script.ScriptManager;
import com.alan.clients.util.file.FileType;
import com.alan.clients.util.file.config.ConfigFile;
import com.alan.clients.util.chat.ChatUtil;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Script extends Command {
    public Script() {
        super("command.script.description", "script", "scripts", "js");
    }

    @Override
    public void execute(String[] var1) {
        if (var1.length < 2) {
            this.error("Valid actions are load, reload, unload, folder and enablesecurity");
        } else {
            String s = var1[1].toLowerCase(Locale.getDefault());
            ScriptManager scriptmanager = Client.a.getScriptManager();
            com.alan.clients.script.Script script;
            if (var1.length > 3) {
                script = scriptmanager.getScript(var1[2]);
                if (script == null) {
                    ChatUtil.b("command.script.notfound", var1[2]);
                    return;
                }
            } else {
                script = null;
            }

            try {
                {
                    label122: {
                        {
                            label103: {
                                label102: {
                                    label101: {
                                        {
                                            String s1 = s;
                                            switch (s1) {
                                                case "load":
                                                    break;
                                                case "reload":
                                                    break label102;
                                                case "unload":
                                                    break label101;
                                                case "enablesecurity":
                                                    break label122;
                                                case "download":
                                                case "install":
                                                    ChatUtil.b("Remote script installation is disabled for safety.");
                                                    return;
                                                case "folder":
                                                    try {
                                                        Desktop desktop = Desktop.getDesktop();
                                                        File file1 = new File(String.valueOf(ScriptManager.SCRIPT_DIRECTORY));
                                                        desktop.open(file1);
                                                        ChatUtil.b("command.script.folder");
                                                    } catch (IllegalArgumentException | IOException illegalargumentexception) {
                                                        ChatUtil.b("command.script.notfoundfolder");
                                                    }

                                                    return;
                                                default:
                                                    ChatUtil.b(
                                                        "command.script.unknownaction",
                                                        "Valid actions are load, reload, unload, folder and enablesecurity"
                                                    );
                                                    return;
                                            }
                                        }

                                        if (script == null) {
                                            scriptmanager.loadScripts();
                                        } else {
                                            script.load();
                                        }
                                        break label103;
                                    }

                                    if (script == null) {
                                        scriptmanager.unloadScripts();
                                    } else {
                                        script.unload();
                                    }
                                    break label103;
                                }

                                Client.a.getScriptManager().reloadScripts();
                                Client.a.getStandardClickGUI().moduleList = new ConcurrentLinkedQueue<>();
                            }

                            ChatUtil.b("Successfully " + s + "ed " + (script == null ? "all scripts" : "\"" + script.getName() + "\"") + ".");
                            return;
                        }

                    }

                    scriptmanager.setSecurityMeasures(true);
                    ChatUtil.b("command.script.enablesecurity");
                    return;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                ChatUtil.b("Failed to " + s + " a script. Stacktrace printed.");
            }
        }
    }
}
