/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class UserMapper {
    public HashMap<String, Conversation> userMap;

    @PostConstruct
    public void init(){
        userMap = new HashMap<String, Conversation>();
    }

    public void put(String userKey, Conversation conversation) {
        userMap.put(userKey, conversation);
    }

    public Conversation get(String userKey) {
        return userMap.get(userKey);
    }

    public boolean containsKey(String userKey) {
        return userMap.containsKey(userKey);
    }

    public void initUser(String userKey) {

        userMap.put(userKey,
                Conversation.valueOf(
                        "#None",
                        null,
                        null,
                        0
                )
        );

    }
}