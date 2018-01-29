/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.conversation;

import com.bot.api.config.UserMapper;
import com.bot.api.model.conversation.ConversationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public abstract class Conversable {

    @Autowired
    private UserMapper userMapper;

    public abstract ConversationResponse response(String userKey) throws Exception;

    public ConversationResponse checkEntities (String userKey, HashMap<String, String> slots) {
        for (String key : slots.keySet()) {
            if (!userMapper.get(userKey).getEntityMap().containsKey(key)) {
                userMapper.get(userKey).setTryCount(userMapper.get(userKey).getTryCount() + 1);
                return ConversationResponse.valueOf(slots.get(key));
            }
        }
        return null;
    }
}