/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.config;

import com.bot.api.conversation.Conversable;
import com.bot.api.conversation.dialog.NoneBO;
import com.bot.api.conversation.dialog.ProfileBO;
import com.bot.api.conversation.dialog.WelcomeBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DialogMapper {
    private Map<String, Conversable> dialogMap;

    @Autowired
    private NoneBO noneBO;

    @Autowired
    private WelcomeBO welcomeBO;

    @Autowired
    private ProfileBO profileBO;


    @PostConstruct
    public void init() {
        dialogMap = new HashMap<String, Conversable>();
        dialogMap.put("None", noneBO);
        dialogMap.put("Welcome", welcomeBO);
        dialogMap.put("Profile", profileBO);
    }

    public Conversable getDialog(String key) {
        return dialogMap.get(key);
    }
}